package com.finstack.assist.leave.notification.service;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finstack.assist.leave.notification.entity.Notification;
import com.finstack.assist.leave.notification.entity.NotificationEvent;
import com.finstack.assist.leave.notification.model.EmailDTO;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TemplateUtilImpl implements TemplateUtil {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public EmailDTO getProcessedInfo(Notification notification) {
        EmailDTO emailDTO = new EmailDTO();
        try {

            log.info("Notification Event is " + notification.getNotificationEvent());
            Resource resource = resourceLoader.getResource("classpath:");
            resource = resourceLoader.getResource("classpath:EmailTemplates\\" + notification.getNotificationEvent() + ".xml");
            log.info("resource URI is" + resource.getURI());
            String templateFileContent = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
            log.info("template file content is " + templateFileContent);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(resource.getInputStream());
            log.info("to address values is " + document.getElementsByTagName("toAddress").item(0).getTextContent());
            emailDTO.setToAddress(replaceHolders(new StringBuilder(document.getElementsByTagName("toAddress").item(0).getTextContent()), notification.getTransactionInformation(), notification.getNotificationEvent()));
            emailDTO.setCcAddress(replaceHolders(new StringBuilder(document.getElementsByTagName("ccAddress").item(0).getTextContent()), notification.getTransactionInformation(), notification.getNotificationEvent()));
            emailDTO.setBccAddress(replaceHolders(new StringBuilder(document.getElementsByTagName("bccAddress").item(0).getTextContent()), notification.getTransactionInformation(), notification.getNotificationEvent()));
            emailDTO.setSubject(replaceHolders(new StringBuilder(document.getElementsByTagName("mailSubject").item(0).getTextContent()), notification.getTransactionInformation(), notification.getNotificationEvent()));
            emailDTO.setContent(replaceHolders(new StringBuilder(document.getElementsByTagName("mailContent").item(0).getTextContent()), notification.getTransactionInformation(), notification.getNotificationEvent()));

        } catch (IOException e) {
            log.error("IO Error" + e);
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            log.error("ParserConfigurationException" + e);
            e.printStackTrace();

        } catch (SAXException e) {
            log.error("SAXException" + e);
            e.printStackTrace();
        }
        return emailDTO;
    }

    private String replaceHolders(StringBuilder templateText, String transactionInformation, NotificationEvent notificationEvent) {

        log.info("Initial value is ==>" + templateText);

        List<String> placeHolders = getAllMatchesAsList(templateText.toString());

        placeHolders.stream().forEach((item) -> {

            String fieldFromTransaction = null;
            String prefix = null;
            String suffix = null;
            String valueToBeReplaced = null;
            if (item.contains("[1..n]")) {
                log.info("repeatable field" + item);
                //remove repetition convention
                fieldFromTransaction = item.substring(8, item.length() - 2);
                log.info("fieldFromTransaction is " + fieldFromTransaction);
                prefix = getPrefix(fieldFromTransaction);
                suffix = getSuffix(fieldFromTransaction);

                log.info("prefix is" + prefix);
                log.info("suffix is" + suffix);
                if (prefix != null)
                    fieldFromTransaction = fieldFromTransaction.substring(("(" + prefix + ")").length());

                if (suffix != null)
                    fieldFromTransaction = fieldFromTransaction.substring(0, fieldFromTransaction.length() - ("(" + suffix + ")").length());

            } else {
                fieldFromTransaction = item.substring(2, item.length() - 2);
            }

            String valueFromTransaction = "";

            log.info("field to be replaced is " + fieldFromTransaction);

            if (item.contains("[1..n]")) {
                valueFromTransaction = getValuesfromTransaction(transactionInformation, fieldFromTransaction, prefix, suffix).toString();
            } else {
                valueFromTransaction = getValuefromTransaction(transactionInformation, fieldFromTransaction);
            }
            log.info(item + " will be replaced with " + valueFromTransaction);
            if (valueFromTransaction != null)

                replaceAll(templateText, item, valueFromTransaction);
        });

        log.info(" After processing value is ==> " + templateText);

        return templateText.toString();
    }

    private List<String> getValuesfromTransaction(String transactionInformation, String fieldFromTransaction, String prefix, String suffix) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(transactionInformation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String transactionField = fieldFromTransaction;
        //String transactionField="sbrId";
        String jsonPath = "";
        if (transactionField.contains(".")) {
            transactionField = "/" + transactionField;
            transactionField = transactionField.replaceAll("\\.", "/");
            jsonPath = transactionField.substring(0, transactionField.lastIndexOf("/"));
            //jsonPath= "/" + jsonPath + transactionField.substring(0,transactionField.lastIndexOf("."));
        }
        int counter = 0;

        JsonPointer jsonPointer = JsonPointer.compile(jsonPath);
        List<String> value = new ArrayList<>();
        //jsonPointer = JsonPointer.compile("/counterParties/0");
        jsonPointer = JsonPointer.compile(jsonPath + "/" + counter);

        while (jsonNode.at(jsonPointer).get(transactionField.substring(transactionField.lastIndexOf("/") + 1)) != null) {

            value.add(prefix + jsonNode.at(jsonPointer).get(transactionField.substring(transactionField.lastIndexOf("/") + 1)).asText() + suffix);
            counter++;
            jsonPointer = JsonPointer.compile(jsonPath + "/" + counter);

        }
        log.info("value from transaction information is " + value);
        return value;
    }

    private String getPrefix(String str) {
        if (str.charAt(0) == '(')
            return str.substring(1, str.substring(1).indexOf(')') + 1);
        else
            return null;
    }

    private String getSuffix(String str) {
        if (str.charAt(str.length() - 1) == ')')
            return str.substring(str.lastIndexOf('(') + 1, str.length() - 1);
        else
            return null;
    }

    private String getValuefromTransaction(String transactionInformation, String fieldFromTransaction) {

        log.info("field from transaction is " + fieldFromTransaction);
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(transactionInformation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String transactionField = fieldFromTransaction;
        //String transactionField="sbrId";
        String jsonPath = "";
        if (transactionField.contains(".")) {
            transactionField = "/" + transactionField;
            transactionField = transactionField.replaceAll("\\.", "/");
            jsonPath = transactionField.substring(0, transactionField.lastIndexOf("/"));
            //jsonPath= "/" + jsonPath + transactionField.substring(0,transactionField.lastIndexOf("."));
        }
        log.debug("json path is " + jsonPath);
        JsonPointer jsonPointer = JsonPointer.compile(jsonPath);
        String value = null;
        if (jsonNode.at(jsonPointer).get(transactionField.substring(transactionField.lastIndexOf("/") + 1)) != null)
            value = jsonNode.at(jsonPointer).get(transactionField.substring(transactionField.lastIndexOf("/") + 1)).asText();
        else
            log.error("unable to replace value for path " + jsonPointer + " and node " + transactionField.substring(transactionField.lastIndexOf("/") + 1));
        log.debug("value from transaction information is " + value);
        return value;
    }

    public void replaceAll(StringBuilder sb, String find, String replace) {

        //to accommodate for escape sequences

        if (find.contains("[")) {
            String finalValue = (sb.toString().replace(find, replace));
            sb.replace(0, sb.length(), finalValue);
            return;
        }

        //compile pattern from find string
        Pattern p = Pattern.compile(find);

        //create new Matcher from StringBuilder object
        Matcher matcher = p.matcher(sb);

        //index of StringBuilder from where search should begin
        int startIndex = 0;

        while (matcher.find(startIndex)) {

            sb.replace(matcher.start(), matcher.end(), replace);

            //set next start index as start of the last match + length of replacement
            startIndex = matcher.start() + replace.length();
        }
    }

    //all regex matches to a List
    private static List<String> getAllMatchesAsList(String str) {
//        //pattern to match <%anystring%>
//        Pattern pattern = Pattern.compile("<%[a-zA-Z_.]*%>");
        //pattern to match <%anystring%> <%[1..n](<p>)anystring(</p>)%>
        Pattern pattern = Pattern.compile("<%[\\[\\]()a-zA-Z_.,1\\<\\>\\/]*%>");
        Matcher matcher = pattern.matcher(str);
        return matcher.results().map(MatchResult::group).collect(Collectors.toList());
    }
}

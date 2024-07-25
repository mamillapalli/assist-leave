package com.finstack.assist.leave.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.Map;

public class ErrorMessageUtil {
    private static final String MESSAGE_FILE = "ErrorMessages.xml";
    private static Map<String, String> messages = new HashMap<>();

    static {
        loadMessages();
    }

    private static void loadMessages() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(ErrorMessageUtil.class.getClassLoader().getResourceAsStream(MESSAGE_FILE));

            NodeList messageNodes = document.getElementsByTagName("message");
            for (int i = 0; i < messageNodes.getLength(); i++) {
                Element element = (Element) messageNodes.item(i);
                String key = element.getAttribute("key");
                String value = element.getTextContent();
                messages.put(key, value);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load messages from XML file", e);
        }
    }

    public static String getMessage(String key) {
        return messages.get(key);
    }
}

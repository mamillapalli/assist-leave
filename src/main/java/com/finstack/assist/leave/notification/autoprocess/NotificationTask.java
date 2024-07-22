package com.finstack.assist.leave.notification.autoprocess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finstack.assist.leave.notification.entity.Notification;
import com.finstack.assist.leave.notification.entity.NotificationStatusEnum;
import com.finstack.assist.leave.notification.repository.NotificationRepository;
import com.finstack.assist.leave.notification.service.NotificationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
//@ConditionalOnProperty(prefix = "NotificationTask", name = "enabled", havingValue = "true")
public class NotificationTask {

    NotificationRepository notificationRepository;
    NotificationService notificationService;
    ObjectMapper objectMapper;
    String notificationTaskEnabled;
    KafkaTemplate<String, Object> kafkaTemplate;
    String kafkaTopic;

    @Autowired
    NotificationTask(NotificationRepository notificationRepository, NotificationService notificationService,
                     ObjectMapper objectMapper, @Value("${NotificationTask.enabled}") String notificationTaskEnabled,
                     KafkaTemplate<String, Object> kafkaTemplate, @Value("${kafka.topic}") String kafkaTopic) {
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
        this.notificationTaskEnabled = notificationTaskEnabled;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopic = kafkaTopic;
        log.info("setting system property notificationTaskEnabled, value from application properties is " + notificationTaskEnabled);
        if (notificationTaskEnabled.equalsIgnoreCase("true"))
            System.setProperty("notificationTaskEnabled", "true");
        else
            System.setProperty("notificationTaskEnabled", "false");
    }

    //@Scheduled(fixedRate = 20000000)
    @Scheduled(fixedRateString = "${NotificationTask.interval}", initialDelay = 1000)
    public void sendMessage() {
        if (System.getProperty("notificationTaskEnabled").equalsIgnoreCase("true")) {
            log.info("NotificationTask Start...");
            List<NotificationStatusEnum> notificationStatusEnumList = new ArrayList<NotificationStatusEnum>();
            notificationStatusEnumList.add(NotificationStatusEnum.INITIATED);
            notificationStatusEnumList.add(NotificationStatusEnum.READY_TO_BE_SENT);
            Optional<List<Notification>> optionalNotificationList = notificationRepository.findByNotificationStatusIn(notificationStatusEnumList);
            if (optionalNotificationList.isPresent()) {
                optionalNotificationList.get().stream().forEach(notification -> {
                    if(notification.getNotificationStatus().compareTo(NotificationStatusEnum.INITIATED)==0)
                        notificationService.prepareForSend(notification);

                    try {
                        log.debug("Sending Notification object on Kafka ==>" +  objectMapper.writeValueAsString(notification));
                        kafkaTemplate.send(kafkaTopic,notification);
                        notificationService.updateSentStatus(notification);

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                });
            }

        }
    }

}

package com.csme.assist.leave.notification.service;

import com.csme.assist.leave.entity.Base;
import com.csme.assist.leave.jwtauthentication.configuration.service.JWTUtil;
import com.csme.assist.leave.notification.entity.Notification;
import com.csme.assist.leave.notification.entity.NotificationEvent;
import com.csme.assist.leave.notification.entity.NotificationStatusEnum;
import com.csme.assist.leave.notification.model.EmailDTO;
import com.csme.assist.leave.notification.repository.NotificationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    TemplateUtil templateUtil;

    @Override
    public void addLeave(Object object, NotificationEvent notificationEvent) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException {

        Notification notification = Notification.builder()
                .notificationStatus(NotificationStatusEnum.INITIATED)
                .uuid(UUID.randomUUID())
                .transactionInformation(objectMapper.writeValueAsString(object))
                .notificationEvent(notificationEvent)
                .build();
        //Base baseDetails = objectMapper.readValue(objectMapper.writeValueAsString(object), Base.class);
        notification.setCreationDetails(jwtUtil.extractUsernameFromRequest());
        notificationRepository.save(notification);

    }

    @Override
    public void updateLeave(Object object, NotificationEvent notificationEvent) throws JsonProcessingException {

        Notification notification = Notification.builder()
                .notificationStatus(NotificationStatusEnum.INITIATED)
                .uuid(UUID.randomUUID())
                .transactionInformation(objectMapper.writeValueAsString(object))
                .notificationEvent(notificationEvent)
                .build();
        Base baseDetails = objectMapper.readValue(objectMapper.writeValueAsString(object), Base.class);
        notification.setModificationDetails(jwtUtil.extractUsernameFromRequest());

        notificationRepository.save(notification);

    }

    @Override
    public void approveLeave(Object object, NotificationEvent notificationEvent) throws JsonProcessingException {

        Notification notification = Notification.builder()
                .notificationStatus(NotificationStatusEnum.INITIATED)
                .uuid(UUID.randomUUID())
                .transactionInformation(objectMapper.writeValueAsString(object))
                .notificationEvent(notificationEvent)
                .build();
        Base baseDetails = objectMapper.readValue(objectMapper.writeValueAsString(object), Base.class);
        notification.setAuthorizationDetails(jwtUtil.extractUsernameFromRequest());

        notificationRepository.save(notification);

    }

    @Override
    public void rejectLeave(Object object, NotificationEvent notificationEvent) throws JsonProcessingException {

        Notification notification = Notification.builder()
                .notificationStatus(NotificationStatusEnum.INITIATED)
                .uuid(UUID.randomUUID())
                .transactionInformation(objectMapper.writeValueAsString(object))
                .notificationEvent(notificationEvent)
                .build();
        Base baseDetails = objectMapper.readValue(objectMapper.writeValueAsString(object), Base.class);
        notification.setModificationDetails(jwtUtil.extractUsernameFromRequest());

        notificationRepository.save(notification);
    }
    
	@Override
	public void deleteLeave(Object object, NotificationEvent notificationEvent) throws JsonProcessingException {

        Notification notification = Notification.builder()
                .notificationStatus(NotificationStatusEnum.INITIATED)
                .uuid(UUID.randomUUID())
                .transactionInformation(objectMapper.writeValueAsString(object))
                .notificationEvent(notificationEvent)
                .build();
        Base baseDetails = objectMapper.readValue(objectMapper.writeValueAsString(object), Base.class);
        notification.setModificationDetails(jwtUtil.extractUsernameFromRequest());
 
        notificationRepository.save(notification);

	}

    public boolean prepareForSend(Notification notification) {

        EmailDTO emailDTO = templateUtil.getProcessedInfo(notification);
        notification.setTo(emailDTO.getToAddress());
        notification.setCc(emailDTO.getCcAddress());
        notification.setBcc(emailDTO.getBccAddress());
        notification.setContent(emailDTO.getContent());
        notification.setSubject(emailDTO.getSubject());
        notification.setNotificationStatus(NotificationStatusEnum.READY_TO_BE_SENT);
        notificationRepository.save(notification);

        return true;
    }

    @Override
    public boolean updateSentStatus(Notification notification) {

        notification.setNotificationStatus(NotificationStatusEnum.SENT);
        notificationRepository.save(notification);

        return true;
    }


}

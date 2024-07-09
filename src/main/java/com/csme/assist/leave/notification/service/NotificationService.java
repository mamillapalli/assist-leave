package com.csme.assist.leave.notification.service;

import com.csme.assist.leave.notification.entity.Notification;
import com.csme.assist.leave.notification.entity.NotificationEvent;
import com.fasterxml.jackson.core.JsonProcessingException;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface NotificationService {

    void addLeave(Object object, NotificationEvent notificationEvent) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException;

    void updateLeave(Object object, NotificationEvent notificationEvent) throws JsonProcessingException;

    void approveLeave(Object object, NotificationEvent notificationEvent) throws JsonProcessingException;

    void rejectLeave(Object result, NotificationEvent notificationEvent) throws JsonProcessingException;
    
    void deleteLeave(Object result, NotificationEvent notificationEvent) throws JsonProcessingException;

    boolean prepareForSend(Notification notification);

    boolean updateSentStatus(Notification notification);
}

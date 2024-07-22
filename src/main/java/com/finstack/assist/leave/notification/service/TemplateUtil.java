package com.finstack.assist.leave.notification.service;

import com.finstack.assist.leave.notification.entity.Notification;
import com.finstack.assist.leave.notification.model.EmailDTO;

public interface TemplateUtil {

    EmailDTO getProcessedInfo(Notification notification);

}

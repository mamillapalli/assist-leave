package com.csme.assist.leave.notification.service;

import com.csme.assist.leave.notification.entity.Notification;
import com.csme.assist.leave.notification.model.EmailDTO;

public interface TemplateUtil {

    EmailDTO getProcessedInfo(Notification notification);

}

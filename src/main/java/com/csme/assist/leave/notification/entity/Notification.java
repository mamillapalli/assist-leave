package com.csme.assist.leave.notification.entity;

import com.csme.assist.leave.entity.Base;
import lombok.*;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "NOTIFICATION_TABLE", schema = "LEAVE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Notification extends Base {

    @Id
    @Column(name = "MESSAGE_ID")
    private UUID uuid;
    @Column(name = "TO_LIST")
    private String to;
    @Column(name = "CC_LIST")
    private String cc;
    @Column(name = "BCC_LIST")
    private String bcc;
    @Column(name = "FROM_LIST")
    private String from;
    @Column(name = "SUBJECT")
    private String subject;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "ATTACHMENTS")
    private byte[] attachments;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private NotificationStatusEnum notificationStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "NOTIFICATION_EVENT")
    private NotificationEvent notificationEvent;
    @Column(name = "TRANSACTION_INFORMATION")
    private String transactionInformation;

}

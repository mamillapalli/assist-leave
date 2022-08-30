package com.csme.assist.leave.entity;

import jdk.jshell.Snippet;
import lombok.*;

import javax.persistence.*;
import java.util.Date;



@Entity
@Table(name = "LEAVE_TABLE", schema = "PUBLIC")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class  Leave extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="LEAVE_ID")
    private int id;
    @Column(name = "NAME")
    private String name;
    @Column (name ="DESCRIPTION")
    private String description;
    @Column(name = "START_DATE")
    private Date startDate;
    @Column (name = "END_DATE")
    private Date endDate;
    @Column (name = "NUMBER_OF_DAYS")
    private int numberOfDays;
    @Column (name = "PAY_PERCENTAGE")
    private int payPercentage;
    @Column(name = "RESOURCE_ID")
    private String resourceId;
    @Column (name = "APPROVER_ID")
    private String approverId;
    @Column (name = "CONTACT_ADDRESS")
    private String contactAddress;
    @Column (name = "CONTACT_PHONE")
    private String contactPhone;
    @Column (name = "TICKETS_PAID")
    private boolean ticketsPaid;
    @Column (name = "TICKETS_TO")
    private String ticketsTo;
    @Column (name = "STATUS")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Column(name="APPROVER_COMMENTS")
    private String approverComments;
    @Column(name = "DELETE_FLAG")
    private boolean deleteFlag;

}

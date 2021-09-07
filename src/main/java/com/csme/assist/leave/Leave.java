package com.csme.assist.leave;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table (name = "LEAVE_TABLE")
public class Leave {

    @Id
    @GeneratedValue
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
    private int resourceId;
    @Column (name = "APPROVER_ID")
    private int approverId;
    @Column (name = "CONTACT_ADDRESS")
    private String contactAddress;
    @Column (name = "CONTACT_PHONE")
    private String contactPhone;
    @Column (name = "TICKETS_PAID")
    private boolean ticketsPaid;
    @Column (name = "TICKETS_TO")
    private String ticketsTo;
    @Column (name = "STATUS")
    private String status;
    @Column(name="APPROVER_COMMENTS")
    private String approverComments;

}

package com.csme.assist.leave.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "LEAVE_TABLE", schema = "PUBLIC")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class  Leave extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="LEAVE_ID")
    private int id;
    @Column(name = "LEAVE_NAME")
    private String name;
    @Column (name ="DESCRIPTION")
    private String description;
    @Column(name = "START_DATE")
    private LocalDate startDate;
    @Column (name = "END_DATE")
    private LocalDate endDate;
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
    
    //added 4 notification
    @Column(name = "COLLEAGUE_NAME")
    private String colleagueName;
    @Column(name = "COLLEAGUE_EMAIL")
    private String colleagueEmail;
    @Column(name = "COLLEAGUE_CONTACT")
    private long colleagueContact;
    @Column(name = "notes")
    private String notes;

}

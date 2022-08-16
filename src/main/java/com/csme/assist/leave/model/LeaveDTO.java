package com.csme.assist.leave.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDTO {
    private int id;
    private String name;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" ,timezone = "Asia/Dubai")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" ,timezone = "Asia/Dubai")
    private Date endDate;
    private int numberOfDays;
    private int payPercentage;
    private int resourceId;
    private int approverId;
    private String contactAddress;
    private String contactPhone;
    private boolean ticketsPaid;
    private String ticketsTo;
    private String status;
    private String approverComments;
    private boolean deleteFlag;
}

package com.csme.assist.leave.model;

import java.time.LocalDate;

import com.csme.assist.leave.entity.StatusEnum;
import com.csme.assist.leave.entity.TransactionStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDTO {
    private int id;
    private String name;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" ,timezone = "Asia/Dubai")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" ,timezone = "Asia/Dubai")
    private LocalDate endDate;
    private int numberOfDays;
    private int payPercentage;
    private String resourceId;
    private String approverId;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private List<ApproverDTO> approver;
    private String contactAddress;
    private String contactPhone;
    private boolean ticketsPaid;
    private String ticketsTo;
    private StatusEnum status;
    private String approverComments;
    private boolean deleteFlag;
    private String colleagueName;
    private String colleagueEmail;
    private String colleagueContact;
    private TransactionStatusEnum transactionStatus;
    
    private String leaveSeekerName;
    private String approverName;
   // private String assistLogo;
    
	
	  private String previousDescription;
	  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" ,timezone = "Asia/Dubai")
	  private LocalDate previousStartDate;
	  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" ,timezone = "Asia/Dubai")
	  private LocalDate previousEndDate;
	  private int  previousNumberOfDays;
	 
    

}

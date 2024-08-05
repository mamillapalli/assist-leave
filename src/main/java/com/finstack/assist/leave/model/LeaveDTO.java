package com.finstack.assist.leave.model;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.finstack.assist.leave.entity.StatusEnum;
import com.finstack.assist.leave.entity.TransactionStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
 
public class LeaveDTO {
    private int id;
    //private String name;
    @NotBlank(message = "Should not be blank")
    private String leaveType;
   // @NotBlank("leave description should not be blank")
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" ,timezone = "Asia/Dubai")
    @NotNull(message = "should not be null")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" ,timezone = "Asia/Dubai")
    @NotNull(message = "should not be null")
    private LocalDate endDate;
    private int numberOfDays;
    private int payPercentage;
    @NotBlank(message = "should not be blank")
    private String resourceId;
    @NotBlank(message = "should not be blank")
    private String approverId;
    private List<ApproverDTO> approver;
    @NotBlank(message = "should not be blank")
    private String contactAddress;
    @NotBlank(message = "should not be blank")
    private String contactPhone;
    private boolean ticketsPaid;
    private String ticketsTo;
    private StatusEnum status;
    private String approverComments;
    private boolean deleteFlag;
    @NotBlank(message = "should not be blank")
    private String colleagueName;
    @NotBlank(message = "should not be blank")
    private String colleagueEmail;
    @NotBlank(message = "should not be blank")
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
	 
	  public void setApprover(List<ApproverDTO> approverInfo){
		  System.out.println("@@@@@ approverid: "+approverId);
	        approverId = approverInfo == null || approverInfo.isEmpty() ? approverId : approverInfo.get(0).getItemName()==null?approverId: approverInfo.get(0).getItemName();
			  System.out.println("@@@@@ approverid: "+approverId);

	    }
    
}

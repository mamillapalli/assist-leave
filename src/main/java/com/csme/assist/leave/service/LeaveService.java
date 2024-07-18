package com.csme.assist.leave.service;


import com.csme.assist.leave.entity.StatusEnum;
import com.csme.assist.leave.entity.TransactionStatusEnum;
import com.csme.assist.leave.model.LeaveDTO;
import com.csme.assist.leave.model.LeaveSummuryDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;



public interface LeaveService {
    List<LeaveDTO> getAll();

    List<LeaveDTO> getAll(String status,String resourceId,String approverId,LocalDate startDate,LocalDate endDate);
    LeaveDTO getLeaves(int id);

    List<LeaveDTO> getLeavesByTransactionStatus(TransactionStatusEnum transactionStatus);

    List<LeaveDTO> getLeavesByResourceId(String id);

    List<LeaveDTO> getLeavesByResourceIdAndTransactionStatus(String id,TransactionStatusEnum transactionStatus);
    
    List<LeaveDTO> getLeavesByResourceIdAndStatus(String id,StatusEnum status);

    List<LeaveDTO> getLeavesByApproverId(String id);
    LeaveDTO addLeave(LeaveDTO leaveDTO);

    LeaveDTO updateLeave(int id , LeaveDTO leaveDTO);
    
    LeaveDTO deleteLeave(int id);

    LeaveDTO approveLeave(int id,LeaveDTO leaveDTO);

    LeaveDTO rejectLeave(int id,LeaveDTO leaveDTO);

    List<LeaveDTO> getLeavesByApproverIdAndStatus(String id, StatusEnum status);
    

    Long calculateDays(LocalDate startDate, LocalDate endDate);
    
    public LeaveSummuryDTO leaveSummary(String resourceId);
}

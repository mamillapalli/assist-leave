package com.finstack.assist.leave.service;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.finstack.assist.leave.entity.StatusEnum;
import com.finstack.assist.leave.entity.TransactionStatusEnum;
import com.finstack.assist.leave.model.LeaveDTO;
import com.finstack.assist.leave.model.LeaveSummuryDTO;



public interface LeaveService {
   // List<LeaveDTO> getAll();

    List<LeaveDTO> getAll(StatusEnum status,String resourceId,String approverId,LocalDate startDate,LocalDate endDate);
    Page<LeaveDTO> getAllLeavesByPaging(StatusEnum status,String resourceId,String approverId,LocalDate startDate,LocalDate endDate, int page, int pageSize);
    Page<LeaveDTO> getAllLeavesByPaging(StatusEnum status,String resourceId,String approverId,LocalDate startDate,LocalDate endDate, Pageable pageable );


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

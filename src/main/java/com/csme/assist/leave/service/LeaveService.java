package com.csme.assist.leave.service;


import com.csme.assist.leave.entity.StatusEnum;
import com.csme.assist.leave.entity.TransactionStatusEnum;
import com.csme.assist.leave.model.LeaveDTO;
import java.util.List;



public interface LeaveService {
    List<LeaveDTO> getAll();

    LeaveDTO getLeaves(int id);

    List<LeaveDTO> getLeavesByTransactionStatus(TransactionStatusEnum transactionStatus);

    List<LeaveDTO> getLeavesByResourceId(String id);

    List<LeaveDTO> getLeavesByResourceIdAndStatus(String id,TransactionStatusEnum transactionStatus);

    List<LeaveDTO> getLeavesByApproverId(String id);

     LeaveDTO addLeave(LeaveDTO leaveDTO);

    LeaveDTO updateLeave(int id , LeaveDTO leaveDTO);

    LeaveDTO approveLeave(int id,LeaveDTO leaveDTO);

    LeaveDTO rejectLeave(int id,LeaveDTO leaveDTO);

    List<LeaveDTO> getLeavesByApproverIdAndStatus(String id, StatusEnum status);
}

package com.csme.assist.leave.service;


import com.csme.assist.leave.entity.StatusEnum;
import com.csme.assist.leave.entity.TransactionStatusEnum;
import com.csme.assist.leave.model.LeaveDTO;
import java.util.List;



public interface LeaveService {
    List<LeaveDTO> getAll();

    LeaveDTO getLeaves(int id);

    List<LeaveDTO> getLeavesByResourceId(int id);

    List<LeaveDTO> getLeavesByApproverId(int id);

     LeaveDTO addLeave(LeaveDTO leaveDTO);

    LeaveDTO updateLeave(int id , LeaveDTO roleDTO);

    LeaveDTO approveLeave(int id);

    List<LeaveDTO> getLeavesByApproverIdAndStatus(int id, StatusEnum status);
}

package com.csme.assist.leave.service;


import com.csme.assist.leave.model.LeaveDTO;
import java.util.List;



public interface LeaveService {
    List<LeaveDTO> getAll();

    LeaveDTO getLeaves(int id);

    LeaveDTO getLeavesByResourceId(int id);

    LeaveDTO getLeavesByApproverId(int id);

    LeaveDTO addLeave(LeaveDTO leaveDTO);

    LeaveDTO updateLeave(int id , LeaveDTO roleDTO);

    LeaveDTO approveLeave(int id);

}

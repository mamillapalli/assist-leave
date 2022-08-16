package com.csme.assist.leave.mapper;

import com.csme.assist.leave.entity.Leave;
import com.csme.assist.leave.model.LeaveDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface LeaveMapper {
    Leave leaveDTOToLeave(LeaveDTO leaveDTO);
    LeaveDTO leaveToLeaveDTO(Leave leave);
    List<Leave> leaveDTOsToLeave(List<LeaveDTO> leaveDTOs);
    List<LeaveDTO> leaveToLeaveDTOs(List<Leave> leave);
}

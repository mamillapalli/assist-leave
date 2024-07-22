package com.finstack.assist.leave.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.finstack.assist.leave.entity.Leave;
import com.finstack.assist.leave.model.LeaveDTO;

@Mapper
public interface LeaveMapper {
	
    LeaveMapper INSTANCE = Mappers.getMapper(LeaveMapper.class);

    Leave leaveDTOToLeave(LeaveDTO leaveDTO);
    LeaveDTO leaveToLeaveDTO(Leave leave);
    List<Leave> leaveDTOsToLeave(List<LeaveDTO> leaveDTOs);
    List<LeaveDTO> leaveToLeaveDTOs(List<Leave> leave);

    
    default Page<Leave> leaveDTOsToLeavesByPage(Page<LeaveDTO> leaveDTOs) {
        List<Leave> leaves = leaveDTOs.getContent().stream()
            .map(this::leaveDTOToLeave)
            .collect(Collectors.toList());
        return new PageImpl<>(leaves, PageRequest.of(leaveDTOs.getNumber(), leaveDTOs.getSize()), leaveDTOs.getTotalElements());
    }

    default Page<LeaveDTO> leavesToLeaveDTOsByPage(Page<Leave> leaves) {
        List<LeaveDTO> leaveDTOs = leaves.getContent().stream()
            .map(this::leaveToLeaveDTO)
            .collect(Collectors.toList());
        return new PageImpl<>(leaveDTOs, PageRequest.of(leaves.getNumber(), leaves.getSize()), leaves.getTotalElements());
    }	
}

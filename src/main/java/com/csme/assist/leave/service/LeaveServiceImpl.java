package com.csme.assist.leave.service;

import com.csme.assist.leave.entity.Leave;
import com.csme.assist.leave.jwtauthentication.configuration.service.JWTUtil;
import com.csme.assist.leave.model.LeaveDTO;
import com.csme.assist.leave.repository.LeaveRepository;
import com.csme.assist.leave.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;
import com.csme.assist.leave.mapper.LeaveMapper;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    LeaveService leaveService;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    LeaveMapper leaveMapper;

    @Autowired
    JWTUtil jwtUtil;

    @Override
    public List<LeaveDTO> getAll() {
        return leaveMapper.leaveToLeaveDTOs(leaveRepository.findAll());
    }

    @Override
    public LeaveDTO getLeaves(int id) {
        Leave leave = leaveRepository.getById(id);
        if (leave == null) throw new RuntimeException("Leave with id " + id + " does not exist");
        return leaveMapper.leaveToLeaveDTO(leave);
    }

    @Override
    public LeaveDTO getLeavesByResourceId(int id) {
        return leaveMapper.leaveToLeaveDTO((Leave) leaveRepository.findByResourceId(id).orElseThrow(() -> new ResourceNotFoundException("Resource with id " + id + " not found")));
    }

    @Override
    public LeaveDTO getLeavesByApproverId(int id) {
        return leaveMapper.leaveToLeaveDTO((Leave) leaveRepository.findByApproverId(id).orElseThrow(() -> new ResourceNotFoundException("Resource with id " + id + " not found")));
    }

    @Override
    public LeaveDTO addLeave(LeaveDTO leaveDTO) {
        Leave leave = leaveMapper.leaveDTOToLeave(leaveDTO);
        leave.setCreationDetails(jwtUtil.extractUsernameFromRequest());
        return leaveMapper.leaveToLeaveDTO(leaveRepository.save(leave));
    }

    @Override
    public LeaveDTO updateLeave(int userId, LeaveDTO leaveDTO)
    {
        Leave leave = leaveRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Leave wit id -> " + userId + " not found"));
        leave.setApproverId(leaveDTO.getApproverId());
        leave.setContactAddress(leaveDTO.getContactAddress());
        leave.setStatus(leaveDTO.getStatus());
        leave.setContactPhone(leaveDTO.getContactPhone());
        leave.setEndDate(leaveDTO.getEndDate());
        leave.setStartDate(leaveDTO.getStartDate());
        leave.setNumberOfDays(leaveDTO.getNumberOfDays());
        leave.setPayPercentage(leaveDTO.getPayPercentage());
        leave.setTicketsPaid(leaveDTO.isTicketsPaid());
        Leave updatedLeave = leaveRepository.save(leave);
        return leaveMapper.leaveToLeaveDTO(updatedLeave);
    }

    @Override
    public LeaveDTO approveLeave(int userId)
    {
        if(leaveRepository.findById(userId).isEmpty()) throw new RuntimeException("Leave with id " + userId + " does not exist");
        Leave existingUserDetails = leaveRepository.findById(userId).get();
        existingUserDetails.setAuthorizationDetails(jwtUtil.extractUsernameFromRequest());
        Leave savedUser = leaveRepository.save(existingUserDetails);
        if(savedUser.isDeleteFlag()) leaveRepository.delete(savedUser);
        return leaveMapper.leaveToLeaveDTO(savedUser);
    }


}

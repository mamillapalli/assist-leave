package com.csme.assist.leave.service;

import com.csme.assist.leave.entity.Leave;
import com.csme.assist.leave.entity.StatusEnum;
import com.csme.assist.leave.entity.TransactionStatusEnum;
import com.csme.assist.leave.jwtauthentication.configuration.service.JWTUtil;
import com.csme.assist.leave.model.LeaveDTO;
import com.csme.assist.leave.repository.LeaveRepository;
import com.csme.assist.leave.repository.ResourceRepository;
import jdk.jshell.Snippet;
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
    public List<LeaveDTO> getLeavesByTransactionStatus(TransactionStatusEnum transactionStatus) {
        String emailAddress = jwtUtil.extractUsernameFromRequest();
        List<Leave> leave = null;
        if(emailAddress != null){
            leave = leaveRepository.findByTransactionStatusAndResourceIdNot(transactionStatus,emailAddress);
        } else {
            leave = leaveRepository.findByTransactionStatus(transactionStatus);
        }
        if(leave.size()==0) throw new RuntimeException("There are no pending leave request currently in the system");
        return leaveMapper.leaveToLeaveDTOs(leave);
    }


    @Override
    public List<LeaveDTO> getLeavesByResourceId(String id) {
        List<Leave> leave = null;
        System.out.println("roles is :"+jwtUtil.extractRolesFromRequest());
        String rolesValue = jwtUtil.extractRolesFromRequest();
        List<String> list = Arrays.asList(rolesValue.split(","));
//        if (list.contains("LEAVE_ADMIN") || list.contains("LEAVE_APPROVER")) {
//            leave = leaveRepository.findByResourceIdOrTransactionStatus(id,TransactionStatusEnum.PENDING);
//        } else {
//            leave = leaveRepository.findByResourceId(id);
//
//        }
        leave = leaveRepository.findByResourceId(id);
        if (leave.size()==0)
            throw new RuntimeException("Leave with resource " + id + " does not exist");
        return leaveMapper.leaveToLeaveDTOs(leave);

        //return leaveMapper.leaveToLeaveDTOs(leaveRepository.findByResourceId(id).orElseThrow(() -> new ResourceNotFoundException("Resource with id " + id + " not found")));
    }

    @Override
    public List<LeaveDTO> getLeavesByResourceIdAndStatus(String id,TransactionStatusEnum transactionStatus) {
        List<Leave> leave = leaveRepository.findByResourceIdAndTransactionStatus(id,transactionStatus);
        if(leave.size()==0) throw new RuntimeException("There are no pending leave request currently in the system");
        return leaveMapper.leaveToLeaveDTOs(leave);
    }

    @Override
    public List<LeaveDTO> getLeavesByApproverId(String id) {
        return leaveMapper.leaveToLeaveDTOs(leaveRepository.findByApproverId(id).orElseThrow(() -> new ResourceNotFoundException("Approver with id " + id + " not found")));
    }

    @Override
    public List<LeaveDTO> getLeavesByApproverIdAndStatus(String id,StatusEnum status) {
        System.out.println("id is :"+id);
        System.out.println("status is :"+status);
        return leaveMapper.leaveToLeaveDTOs(leaveRepository.findByApproverIdAndStatus(id,status).orElseThrow(() -> new ResourceNotFoundException("Approver with id " + id + " not found")));
    }

    @Override
    public LeaveDTO addLeave(LeaveDTO leaveDTO) {
        Leave leave = leaveMapper.leaveDTOToLeave(leaveDTO);
        leave.setStatus(StatusEnum.WAITING);
        leave.setTransactionStatus(TransactionStatusEnum.PENDING);
        leave.setCreationDetails(jwtUtil.extractUsernameFromRequest());
        return leaveMapper.leaveToLeaveDTO(leaveRepository.save(leave));
    }

    @Override
    public LeaveDTO updateLeave(int userId, LeaveDTO leaveDTO)
    {
        Leave leave = leaveRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Leave wit id -> " + userId + " not found"));
        leave.setApproverId(leaveDTO.getApproverId());
        leave.setContactAddress(leaveDTO.getContactAddress());
        leave.setStatus(StatusEnum.WAITING);
        leave.setTransactionStatus(TransactionStatusEnum.PENDING);
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
    public LeaveDTO approveLeave(int userId, LeaveDTO leaveDTO)
    {
        if(leaveRepository.findById(userId).isEmpty()) throw new RuntimeException("Leave with id " + userId + " does not exist");
        Leave existingUserDetails = leaveRepository.findById(userId).get();
        existingUserDetails.setAuthorizationDetails(jwtUtil.extractUsernameFromRequest());
        existingUserDetails.setStatus(StatusEnum.APPROVED);
        existingUserDetails.setTransactionStatus(TransactionStatusEnum.MASTER);
        existingUserDetails.setApproverId(jwtUtil.extractUsernameFromRequest());
        existingUserDetails.setApproverComments(leaveDTO.getApproverComments());
        Leave savedUser = leaveRepository.save(existingUserDetails);
       if(savedUser.isDeleteFlag()) leaveRepository.delete(savedUser);
        return leaveMapper.leaveToLeaveDTO(savedUser);
    }

    @Override
    public LeaveDTO rejectLeave(int userId, LeaveDTO leaveDTO)
    {
        if(leaveRepository.findById(userId).isEmpty())
            throw new RuntimeException("Reject Leave with id " + userId + " does not exist");
        Leave existingUserDetails = leaveRepository.findById(userId).get();
        existingUserDetails.setAuthorizationDetails(jwtUtil.extractUsernameFromRequest());
        existingUserDetails.setStatus(StatusEnum.REJECTED);
        existingUserDetails.setTransactionStatus(TransactionStatusEnum.PENDING);
        existingUserDetails.setApproverComments(leaveDTO.getApproverComments());
        Leave savedUser = leaveRepository.save(existingUserDetails);
        if(savedUser.isDeleteFlag()) leaveRepository.delete(savedUser);
        return leaveMapper.leaveToLeaveDTO(savedUser);
    }

//    @Override
//    public List<LeaveDTO> getPendingLeavesByApproverId(int id) {
//        List<Leave> leaves = leaveRepository.findByPendingApproverId(id,TransactionStatusEnum.PENDING);
//        if(leaves.size()==0) throw new RuntimeException("No pending customers exists in the system");
//        List<LeaveDTO> leaveDTOS = new ArrayList<>();
//        leaves.forEach((leave) -> leaveDTOS.add(leaveMapper.leaveToLeaveDTO(leave)));
//        return leaveDTOS;
//    }



}

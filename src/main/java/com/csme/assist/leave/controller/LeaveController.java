package com.csme.assist.leave.controller;

import com.csme.assist.leave.auth.AssistUserDetailsService;
import com.csme.assist.leave.entity.Resource;
import com.csme.assist.leave.entity.StatusEnum;
import com.csme.assist.leave.entity.TransactionStatusEnum;
import com.csme.assist.leave.model.LeaveDTO;
import com.csme.assist.leave.repository.ResourceRepository;
import com.csme.assist.leave.jwtauthentication.configuration.service.JWTUtil;
import com.csme.assist.leave.redis.entity.RedisBusinessUnit;
import com.csme.assist.leave.redis.repository.RedisBusinessUnitRepository;
import com.csme.assist.leave.repository.LeaveRepository;
import com.csme.assist.leave.service.LeaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController

public class LeaveController {

    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    LeaveService leaveService;


    Logger logger = LoggerFactory.getLogger(LeaveController.class);

    @Autowired
    RedisBusinessUnitRepository redisBusinessUnitRepository;


    AssistUserDetailsService assistUserDetailsService = new AssistUserDetailsService();


    RedisBusinessUnit redisBusinessUnit = new RedisBusinessUnit();

    @Autowired
    ResourceRepository resourceRepository;


    @Autowired
    JWTUtil jwtUtil;

    @CrossOrigin(origins = "http://localhost:8001/leaves")
    @GetMapping (path = "/leaves")
    public ResponseEntity<List<LeaveDTO>> getAllLeave()
    {
        return new ResponseEntity<>(leaveService.getAll(), HttpStatus.OK);
    }


    @GetMapping (path = "/leaves/{id}")
    public ResponseEntity<LeaveDTO> getLeaves(@PathVariable (name = "id") int id)
    {
        LeaveDTO leaveDTO = leaveService.getLeaves(id);
        return new ResponseEntity<>(leaveDTO, HttpStatus.OK);
    }


    @GetMapping (path = "/leavesstatus/{TRANSACTIONSTATUS}")
    public ResponseEntity<List<LeaveDTO>> getLeavesByStatus(@PathVariable (name = "TRANSACTIONSTATUS") TransactionStatusEnum transactionStatus)
    {
        List<LeaveDTO> leaveDTO = leaveService.getLeavesByTransactionStatus(transactionStatus);
        return new ResponseEntity<>(leaveDTO, HttpStatus.OK);
    }


    @GetMapping (path = "/leavesByResourceId/{id}")
    public ResponseEntity<List<LeaveDTO>> getLeavesByResourceId( @PathVariable (name = "id") String id)
    {
        return new ResponseEntity<>(leaveService.getLeavesByResourceId(id), HttpStatus.OK);

    }


    @GetMapping (path = "/leavesByResourceId/{id}/{TRANSACTIONSTATUS}")
    public ResponseEntity<List<LeaveDTO>> getLeavesByResourceIdAndStatus( @PathVariable (name = "id") String id, @PathVariable (name = "TRANSACTIONSTATUS") TransactionStatusEnum status)
    {
        return new ResponseEntity<>(leaveService.getLeavesByResourceIdAndStatus(id,status), HttpStatus.OK);

    }


    @GetMapping (path = "/leavesByApproverId/{id}")
    public ResponseEntity<List<LeaveDTO>> getLeavesByApproverId( @PathVariable (name = "id") String id)
    {
        return new ResponseEntity<>(leaveService.getLeavesByApproverId(id), HttpStatus.OK);
    }


    @GetMapping (path = "/leavesByApproverId/{id}/{STATUS}")
    public ResponseEntity<List<LeaveDTO>> getLeavesByApproverIdAndStatus( @PathVariable (name = "id") String id, @PathVariable (name = "STATUS") StatusEnum status)
    {
        System.out.println("controller id is :"+id);
        System.out.println("controller status is :"+status);
        return new ResponseEntity<>(leaveService.getLeavesByApproverIdAndStatus(id,status), HttpStatus.OK);
    }


    @PostMapping(path = "/leaves")
    public ResponseEntity<LeaveDTO> addLeave(@Valid @RequestBody LeaveDTO leaveDTO) throws Exception {
        return new ResponseEntity<>(leaveService.addLeave(leaveDTO),HttpStatus.ACCEPTED);
    }


    @PutMapping(path = "/leaves/{id}")
    public ResponseEntity<LeaveDTO> updateLeave(@Valid @RequestBody LeaveDTO leaveDTO, @PathVariable (name = "id") int id)
    {
        return new ResponseEntity<>(leaveService.updateLeave(id,leaveDTO),HttpStatus.ACCEPTED);
    }


    @PutMapping(path = "/approveleaves/{id}")
    public ResponseEntity<LeaveDTO> approveLeave(@Valid @RequestBody LeaveDTO leaveDTO,@PathVariable (name = "id") int id)
    {
        LeaveDTO leaveDTOs = leaveService.approveLeave(id,leaveDTO);
        return new ResponseEntity<>(leaveDTOs, HttpStatus.OK);
    }


    @PutMapping(path = "/rejectleaves/{id}")
    public ResponseEntity<LeaveDTO> rejectLeave(@Valid @RequestBody LeaveDTO leaveDTO,@PathVariable (name = "id") int id)
    {
        LeaveDTO leaveDTOs = leaveService.rejectLeave(id,leaveDTO);
        return new ResponseEntity<>(leaveDTO, HttpStatus.OK);
    }


    @GetMapping (path = "/profile")
    public Optional<Resource> getProfile()
    {
        Optional<Resource> resource = resourceRepository.findByEmailAddress(jwtUtil.extractUsernameFromRequest());
        return resource;
    }

}

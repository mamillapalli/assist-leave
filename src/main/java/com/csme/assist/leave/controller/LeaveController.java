package com.csme.assist.leave.controller;

import com.csme.assist.leave.auth.AssistUserDetailsService;
import com.csme.assist.leave.entity.Resource;
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

    @GetMapping (path = "/leavesByResourceId/{id}")
    public ResponseEntity<LeaveDTO> getLeavesByResourceId( @PathVariable (name = "id") int id)
    {
        LeaveDTO leaveDTO = leaveService.getLeavesByResourceId(id);
        return new ResponseEntity<>(leaveDTO, HttpStatus.OK);
    }

    @GetMapping (path = "/leavesByApproverId/{id}")
    public ResponseEntity<LeaveDTO> getLeavesByApproverId( @PathVariable (name = "id") int id)
    {
        LeaveDTO leaveDTO = leaveService.getLeavesByApproverId(id);
        return new ResponseEntity<>(leaveDTO, HttpStatus.OK);
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
    public ResponseEntity<LeaveDTO> approveLeave(@PathVariable (name = "id") int id)
    {
        LeaveDTO leaveDTO = leaveService.approveLeave(id);
        return new ResponseEntity<>(leaveDTO, HttpStatus.OK);
    }


    @GetMapping (path = "/profile")
    public Optional<Resource> getProfile()
    {
        Optional<Resource> resource = resourceRepository.findByEmailAddress(jwtUtil.extractUsernameFromRequest());
        return resource;
    }

}

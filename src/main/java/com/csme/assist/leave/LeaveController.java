package com.csme.assist.leave;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController

public class LeaveController {

    @Autowired
    LeaveRepository leaveRepository;

    Logger logger = LoggerFactory.getLogger(LeaveController.class);

    @GetMapping (path = "/leaves")
    public List<Leave> getLeaves()
    {

        List<Leave> all = leaveRepository.findAll();
        return all;
    }

    @GetMapping (path = "/leavesByResourceId/{id}")
    public Optional<List<Leave>> getLeavesByResourceId(@PathVariable Integer id)
    {
        logger.info("Retrieving leaves for resource id " + id);
        Optional<List<Leave>> byResourceId = leaveRepository.findByResourceId(id);
        return byResourceId;
    }

    @GetMapping (path = "/leavesByApproverId/{id}")
    public Optional<List<Leave>> getLeavesByApproverId(@PathVariable Integer id)
    {
        logger.info("Retrieving leaves for resource id " + id);
        Optional<List<Leave>> byApproverId = leaveRepository.findByApproverId(id);
        return byApproverId;
    }


}

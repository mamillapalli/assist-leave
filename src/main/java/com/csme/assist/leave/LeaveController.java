package com.csme.assist.leave;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class LeaveController {

    @Autowired
    LeaveRepository leaveRepository;

    @GetMapping (path = "/Leaves")
    public List<Leave> getLeaves(@RequestBody int id)
    {
        log.debug("Retrieving leaves for resource id " + id);
        List<Leave> byResourceId = leaveRepository.findByResourceId(id);
        return byResourceId;
    }



}

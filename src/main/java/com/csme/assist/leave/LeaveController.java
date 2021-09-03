package com.csme.assist.leave;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController

public class LeaveController {

    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    LeaveService leaveService;

    Logger logger = LoggerFactory.getLogger(LeaveController.class);

    @GetMapping (path = "/leaves")
    public List<Leave> getLeaves()
    {

        List<Leave> all = leaveRepository.findAll();
        return all;
    }

    @GetMapping (path = "/getNumberOfWorkingDays/from/{startDate}/to/{endDate}")
    public int getNumberOfWorkingDays(@PathVariable (name="startDate") String start, @PathVariable (name="endDate") String end) throws ParseException {


        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(start);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(end);
        int numberofWorkingDaysBetween = leaveService.getNumberofWorkingDaysBetween(startDate, endDate);
        logger.info("Number of working days between " + startDate + " " + endDate + " is " + numberofWorkingDaysBetween);
        return numberofWorkingDaysBetween;
    }

    @GetMapping (path = "/leavesByResourceId/{id}")
    public Optional<List<Leave>> getLeavesByResourceId( @PathVariable Integer id)
    {
        logger.info("Retrieving leaves for resource id " + id);
        Optional<List<Leave>> byResourceId = leaveRepository.findByResourceId(id);
        return byResourceId;
    }

    @GetMapping (path = "/maximumNumberOfHolidaysAllowed")
    public int getMaximumNumberOfHolidaysAllowed ()
    {
        logger.info("Retrieving maximum number of Holidays Allowed");
        return Integer.valueOf(leaveService.getMaxNoOfHolidays());

    }



    @GetMapping (path = "/leavesByApproverId/{id}")
    public Optional<List<Leave>> getLeavesByApproverId(@PathVariable Integer id)
    {
        logger.info("Retrieving leaves for Approver id " + id);
        Optional<List<Leave>> byApproverId = leaveRepository.findByApproverId(id);
        return byApproverId;
    }

    @PostMapping(path = "/leaves")
    public Leave addLeave( @Valid @RequestBody Leave leave)
    {
        logger.info("in add Leave");
        Leave save = leaveRepository.save(leave);
        return save;
    }

    @PutMapping(path = "/leaves/{id}")
    public ResponseEntity<Leave> updateLeave(@Valid @RequestBody Leave leaveDetails, @PathVariable int id)
    {
        logger.info("in update Leave");
/*        if(leaveRepository.getById(id)!=null) {
            leave.setId(id);
            Leave save = leaveRepository.save(leave);
            return save;
        }
        return new ResourceNotFoundException("Leave with id " + id + " does not exist");*/

        Leave leave = leaveRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Leave wit id -> " + id + " not found"));
        leave.setApproverId(leaveDetails.getApproverId());
        leave.setContactAddress(leaveDetails.getContactAddress());
        leave.setStatus(leaveDetails.getStatus());
        leave.setContactPhone(leaveDetails.getContactPhone());
        leave.setEndDate(leaveDetails.getEndDate());
        leave.setStartDate(leaveDetails.getStartDate());
        leave.setNumberOfDays(leaveDetails.getNumberOfDays());
        leave.setPayPercentage(leaveDetails.getPayPercentage());
        leave.setTicketsPaid(leaveDetails.isTicketsPaid());
        final Leave updatedLeave = leaveRepository.save(leave);
        return ResponseEntity.ok(updatedLeave);
    }


}

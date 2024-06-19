package com.csme.assist.leave.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.csme.assist.leave.model.HolidayDTO;
import com.csme.assist.leave.service.HolidayService;


@RestController
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @CrossOrigin(origins = "http://localhost:8001/holidays")
    @GetMapping (path = "/holidays")
    public ResponseEntity<List<HolidayDTO>> getAll()
    {
        return new ResponseEntity<>(holidayService.getAll(), HttpStatus.OK);
    }
    
    @GetMapping("/holidays/{year}")
    public ResponseEntity<List<HolidayDTO>> getHolidaysByYear(@PathVariable(name = "year") int year){
    	
    	return new ResponseEntity<List<HolidayDTO>>(holidayService.getHolidaysOfYear(year),HttpStatus.OK);
    }
    
    @PostMapping("/holidays")
    public ResponseEntity<HolidayDTO> addHoliday(@RequestBody HolidayDTO holidayDTO ){
    
    	return new ResponseEntity<HolidayDTO>(holidayService.addHoliday(holidayDTO),HttpStatus.OK);
    }
    
    @PutMapping("/updateHoliday/{holidayId}")
    public ResponseEntity<HolidayDTO> updateHoliday(@PathVariable(name = "holidayId") int holidayId, @RequestBody HolidayDTO holidayDTO){
    	
    	HolidayDTO updateHoliday = holidayService.updateHoliday(holidayId, holidayDTO);
    	return new ResponseEntity<HolidayDTO>(updateHoliday,HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteHoliday/{holidayId}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable(name = "holidayId") int holidayId){
    	holidayService.deleteHoliday(holidayId);
    	return new ResponseEntity<Void>(HttpStatus.OK);
    }
    
    
}


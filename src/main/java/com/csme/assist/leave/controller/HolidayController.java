package com.csme.assist.leave.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
}

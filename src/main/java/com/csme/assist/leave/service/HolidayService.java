package com.csme.assist.leave.service;

import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.csme.assist.leave.entity.Holiday;
import com.csme.assist.leave.jwtauthentication.configuration.service.JWTUtil;
import com.csme.assist.leave.mapper.HolidayMapper;
import com.csme.assist.leave.model.HolidayDTO;
import com.csme.assist.leave.repository.HolidayRepository;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    HolidayMapper holidayMapper;
    
    @Autowired
    JWTUtil jwtUtil;

    public List<HolidayDTO> getAll(){
        List<Holiday>  holidays = holidayRepository.findAll();
        return holidayMapper.holidayToHolidayDTOs(holidays);
    }
    
    public List<HolidayDTO> getHolidaysOfYear(int year){
        List<Holiday>  holidays = holidayRepository.findByYear(year);
        return holidayMapper.holidayToHolidayDTOs(holidays);
    }
    
    public HolidayDTO addHoliday(@Valid HolidayDTO holidayDTO) {
        Holiday holiday = holidayMapper.holidayDTOToHoliday(holidayDTO);
        holiday.setCreationDetails(jwtUtil.extractUsernameFromRequest());
        long noOfDays =   ChronoUnit.DAYS.between(holidayDTO.getStartDate(), holidayDTO.getEndDate()) + 1;
        holiday.setNumberOfDays(noOfDays);
        holiday = holidayRepository.save(holiday);
        return holidayMapper.holidayToHolidayDTO(holiday);
    }

    public HolidayDTO updateHoliday(int id, @Valid HolidayDTO holidayDTO) {
        Holiday holiday = holidayRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Holiday with id -> " + id + " not found"));
        
        holiday.setStartDate(holidayDTO.getStartDate());
        holiday.setEndDate(holidayDTO.getEndDate());
        holiday.setDescription(holidayDTO.getDescription());
        holiday.setName(holidayDTO.getName());
        holiday.setYear(holidayDTO.getYear());

        holiday = holidayRepository.save(holiday);
        return holidayMapper.holidayToHolidayDTO(holiday);
    }

    public void deleteHoliday(int id) {
        holidayRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Holiday with id -> " + id + " not found"));
        holidayRepository.deleteById(id);
    }
}

package com.finstack.assist.leave.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.finstack.assist.leave.model.HolidayDTO;
import com.finstack.assist.leave.service.HolidayService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class HolidayServiceTest {
    @Autowired
    HolidayService holidayService;

	/*
	 * @Test public void testAddHoliday() throws ParseException { HolidayDTO
	 * holidayDTO = new HolidayDTO(); // DateFormat dateFormat = new
	 * SimpleDateFormat("dd-MM-yyyy"); DateTimeFormatter dateFormat =
	 * DateTimeFormatter.ofPattern("yyyy-MM-dd"); LocalDate startDate =
	 * LocalDate.parse("2024-06-01", dateFormat); LocalDate endDate =
	 * LocalDate.parse("2024-06-01",dateFormat); holidayDTO.setStartDate(startDate);
	 * holidayDTO.setEndDate(endDate); holidayDTO.setDescription("test1");
	 * holidayDTO.setName("test1");
	 * 
	 * holidayService.addHoliday(holidayDTO); List<HolidayDTO> holidayDTOList =
	 * holidayService.getAll(); int noOfHolidays = holidayDTOList.size();
	 * Assertions.assertEquals(1, noOfHolidays); }
	 */

}

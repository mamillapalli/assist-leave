package com.csme.assist.leave.service;

import com.csme.assist.leave.model.HolidayDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class HolidayServiceTest {
    @Autowired
    HolidayService holidayService;

    @Test
    public void testAddHoliday() throws ParseException {
        HolidayDTO holidayDTO = new HolidayDTO();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = dateFormat.parse("01-06-2024");
        Date endDate = dateFormat.parse("06-06-2024");
        holidayDTO.setStartDate(startDate);
        holidayDTO.setEndDate(endDate);
        holidayDTO.setDescription("test1");
        holidayDTO.setName("test1");

        holidayService.addHoliday(holidayDTO);
        List<HolidayDTO> holidayDTOList = holidayService.getAll();
        int noOfHolidays = holidayDTOList.size();
        Assertions.assertEquals(1, noOfHolidays);
    }
}

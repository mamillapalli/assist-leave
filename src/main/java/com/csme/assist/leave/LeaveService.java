package com.csme.assist.leave;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class LeaveService {


    @Value("${leave.max-no-of-holidays}")
    private String maxNoOfHolidays;
    @Value("${leave.holidays}")
    private String holidays;

    public int getNumberofWorkingDaysBetween(Date startDate, Date endDate)
    {
        log.info("get number of working days method");
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int workDays = 0;

        //Return 0 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && notInHolidayList(startDate)) {
                ++workDays;
                return workDays;
            }

            log.info("day of the week is" + startCal.get(Calendar.DAY_OF_WEEK));
            return 0;
        }

        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }

        do {
           startCal.add(Calendar.DAY_OF_MONTH, 1);
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && notInHolidayList(startCal.getTime())) {
                ++workDays;

            }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date

        return workDays;
    }

    private boolean notInHolidayList(Date date)
    {
       String[] holidaysList =  holidays.split(",");
        for(int count= 0 ; count < holidaysList.length; count++)
        {
            if(new SimpleDateFormat("YYYY-MM-dd").format(date).equalsIgnoreCase(holidaysList[count]))
                return false;
        }
        return true;
    }
}

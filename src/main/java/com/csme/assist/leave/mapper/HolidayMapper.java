package com.csme.assist.leave.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.csme.assist.leave.entity.Holiday;
import com.csme.assist.leave.model.HolidayDTO;


@Mapper
public interface HolidayMapper {
    Holiday holidayDTOToHoliday(HolidayDTO leaveDTO);
    HolidayDTO holidayToHolidayDTO(Holiday leave);
    List<Holiday> holidayDTOsToHoliday(List<HolidayDTO> leaveDTOs);
    List<HolidayDTO> holidayToHolidayDTOs(List<Holiday> leave);
}

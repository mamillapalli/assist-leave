package com.finstack.assist.leave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finstack.assist.leave.entity.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Integer> {
	List<Holiday> findByYear(int year);
}

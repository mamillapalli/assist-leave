package com.csme.assist.leave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LeaveRepository extends JpaRepository<Leave, Integer> {

    public List<Leave> findByResourceId(int id);
}

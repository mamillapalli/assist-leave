package com.csme.assist.leave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LeaveRepository extends JpaRepository<Leave, Integer> {

    Optional<List<Leave>> findByResourceId(int id);

    Optional<List<Leave>> findByApproverId(int id);
}



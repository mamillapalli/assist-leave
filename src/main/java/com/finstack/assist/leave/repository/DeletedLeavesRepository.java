package com.finstack.assist.leave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finstack.assist.leave.entity.DeletedLeave;

@Repository
public interface DeletedLeavesRepository extends JpaRepository<DeletedLeave, Integer> {

}

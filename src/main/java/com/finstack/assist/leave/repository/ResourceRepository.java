package com.finstack.assist.leave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finstack.assist.leave.entity.Resource;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, UUID>{

    Optional<Resource> findByEmailAddress(String email);
}

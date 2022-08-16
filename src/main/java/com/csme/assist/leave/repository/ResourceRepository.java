package com.csme.assist.leave.repository;

import com.csme.assist.leave.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, UUID>{

    Optional<Resource> findByEmailAddress(String email);
}

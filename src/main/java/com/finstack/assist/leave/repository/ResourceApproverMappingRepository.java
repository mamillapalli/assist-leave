package com.finstack.assist.leave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finstack.assist.leave.entity.ResourceApproverMapping;

import java.util.List;
import java.util.Optional;


@Repository
public interface ResourceApproverMappingRepository extends JpaRepository<ResourceApproverMapping, Integer> {

    Optional <ResourceApproverMapping> findByResourceId(int id);

    Optional <List<ResourceApproverMapping>> findByApproverId(int id);

    void deleteByResourceId(int id);

}



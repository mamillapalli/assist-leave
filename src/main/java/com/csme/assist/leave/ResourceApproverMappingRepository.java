package com.csme.assist.leave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ResourceApproverMappingRepository extends JpaRepository<ResourceApproverMapping, Integer> {

    Optional <ResourceApproverMapping> findByResourceId(int id);

    Optional <List<ResourceApproverMapping>> findByApproverId(int id);

    void deleteByResourceId(int id);

    void deleteByApproverId(int id);
}



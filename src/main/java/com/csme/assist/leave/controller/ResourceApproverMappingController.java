package com.csme.assist.leave.controller;

import com.csme.assist.leave.entity.ResourceApproverMapping;
import com.csme.assist.leave.repository.ResourceApproverMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
public class ResourceApproverMappingController {

    @Autowired
    ResourceApproverMappingRepository resourceApproverMappingRepository;
    // Assign Approver for resource controller
    @PostMapping(path = "/assignApproverForResource")
    @Transactional
    public ResourceApproverMapping assignApproverForResource(@RequestBody ResourceApproverMapping resourceApproverMapping)
    {
        return resourceApproverMappingRepository.save(resourceApproverMapping);
    }

    //Modify Approver for resource Id
    @PutMapping(path = "/modifyApproverForResource")
    @Transactional
    public ResourceApproverMapping modifyApproverForResource(@RequestBody ResourceApproverMapping resourceApproverMapping)
    {
        if(resourceApproverMappingRepository.findByResourceId(resourceApproverMapping.getResourceId()).isPresent())
            resourceApproverMappingRepository.deleteByResourceId(resourceApproverMapping.getResourceId());

        return resourceApproverMappingRepository.save(resourceApproverMapping);

    }

    //Assign resources for a given approver Id
    @PostMapping(path = "/assignApproverForResources")
    @Transactional
    public List<ResourceApproverMapping> assignApproverForResources(@RequestBody List<ResourceApproverMapping> resourceApproverMappings)
    {
        return resourceApproverMappingRepository.saveAll(resourceApproverMappings);
    }

    //Modify resources for a given approver Id

    @PutMapping(path = "/modifyApproverForResources")
    @Transactional
    public List<ResourceApproverMapping> modifyApproverForResources(@RequestBody List<ResourceApproverMapping> resourceApproverMappings)
    {
        for (ResourceApproverMapping resourceApproverMapping : resourceApproverMappings) {
            resourceApproverMappingRepository.deleteByResourceId(resourceApproverMapping.getResourceId());
        }
        return resourceApproverMappingRepository.saveAll(resourceApproverMappings);
    }


    //Get Approver for resource controller
    @GetMapping(path = "/getApproverForResource/{resourceId}")
    public Optional<ResourceApproverMapping> getApproverForResource(@PathVariable int resourceId)
    {
        return resourceApproverMappingRepository.findByResourceId(resourceId);
    }


    // Get Resources with a given Approver Id controller
    @GetMapping(path = "/getResourcesForApprover/{approverId}")
    public Optional<List<ResourceApproverMapping>> getResourcesForApprover(@PathVariable int approverId)
    {
        return resourceApproverMappingRepository.findByApproverId(approverId);
    }

}

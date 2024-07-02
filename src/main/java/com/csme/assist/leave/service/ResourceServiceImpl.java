package com.csme.assist.leave.service;

import com.csme.assist.leave.entity.Resource;
import com.csme.assist.leave.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService{

    @Autowired
    ResourceRepository resourceRepository;

    @Override
    public Resource findByEmail(String email) {
        return resourceRepository.findByEmailAddress(email).orElseThrow(() -> new ResourceNotFoundException("Reource with email " + email + " not found"));
    }
}

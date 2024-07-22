package com.finstack.assist.leave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.finstack.assist.leave.entity.Resource;
import com.finstack.assist.leave.repository.ResourceRepository;

@Service
public class ResourceServiceImpl implements ResourceService{

    @Autowired
    ResourceRepository resourceRepository;

    @Override
    public Resource findByEmail(String email) {
        return resourceRepository.findByEmailAddress(email).orElseThrow(() -> new ResourceNotFoundException("Reource with email " + email + " not found"));
    }
}

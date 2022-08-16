package com.csme.assist.leave.auth;

import com.csme.assist.leave.entity.Resource;
import com.csme.assist.leave.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AssistUserDetailsService implements UserDetailsService {

    @Autowired
    ResourceRepository resourceRepository;

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {

        Resource resource= resourceRepository.findByEmailAddress(emailAddress).orElseThrow(() -> new RuntimeException("user with the given email address not found"));
        return new AssistUserDetails(resource);
    }


}

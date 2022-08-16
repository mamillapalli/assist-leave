package com.csme.assist.leave.jwtauthentication.configuration.service;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Profile("NOTUSED")
public class SecurityUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        return new User("foo", "foo", new ArrayList<>());
    }
}

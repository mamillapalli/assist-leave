package com.finstack.assist.leave.service;

import com.finstack.assist.leave.entity.Resource;

public interface ResourceService {
    Resource findByEmail(String email);
}

package com.csme.assist.leave.service;

import com.csme.assist.leave.entity.Resource;

public interface ResourceService {
    Resource findByEmail(String email);
}

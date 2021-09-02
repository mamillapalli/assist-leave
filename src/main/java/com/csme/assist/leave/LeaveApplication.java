package com.csme.assist.leave;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class LeaveApplication {

    public static void main(String[] args)
    {
        log.info("in Leave application");
        SpringApplication.run(LeaveApplication.class, args);
    }

}

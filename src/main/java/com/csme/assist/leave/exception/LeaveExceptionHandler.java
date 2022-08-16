package com.csme.assist.leave.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@Slf4j
public class LeaveExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<Object> handleException(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("Leave Exception Handler");
        //return new LeaveException(new Date(),status,ex.getMessage(),ex.getMessage());
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}

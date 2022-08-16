package com.csme.assist.leave.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class LeaveException {

    private Date timestamp;
    private HttpStatus status;
    private String error;
    private String message;

}

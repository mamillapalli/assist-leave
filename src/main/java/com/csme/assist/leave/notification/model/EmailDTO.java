package com.csme.assist.leave.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmailDTO {

    private String toAddress;
    private String ccAddress;
    private String bccAddress;
    private String fromAddress;
    private String content;
    private String subject;
}

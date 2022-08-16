package com.csme.assist.leave.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "AUDIT", schema = "ADMIN")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Audit {

    @Id
    private UUID uuid;
    private Date eventAt;
    private String accessedBy;
    private String jwtToken;
    private String accessedResource;
    private String inputParameters;
    private String returnedResult;
    private String exception;
    private String eventAction;
    private String remoteHost;
    private String remoteAddress;
    private String remoteUser;
}

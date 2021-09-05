package com.csme.assist.leave;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "RESOURCE_APPROVER_MAPPING")
public class ResourceApproverMapping {

    @Id
    @GeneratedValue
    @Column(name="RESOURCE_APPROVER_MAPPING_ID")
    private int id;
    @Column(name="RESOURCE_ID")
    private int resourceId;
    @Column(name="APPROVER_ID")
    private int approverId;
}

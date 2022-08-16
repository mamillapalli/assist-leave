package com.csme.assist.leave.entity;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "RESOURCE_TABLE", schema="ADMIN")
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Resource extends Base{

    @Id
    private UUID uuid;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "BIRTH_DATE")
    private Date birthDate;
    @Column(name = "JOINING_DATE")
    private Date joiningDate;
    @Column(name = "ACTIVE_STATUS")
    private boolean status;
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "RESOURCE_ROLES_TABLE", schema = "ADMIN",
            joinColumns = { @JoinColumn(name = "RESOURCE_ID") },
            inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") }
    )
    private List<Role> roles;
    @Column(name = "EMAIL_ADDRESS", unique = true)
    private String emailAddress;
    @Column(name = "REPORTING_TO")
    private String reportingTo;


}

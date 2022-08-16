package com.csme.assist.leave.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "ROLE_TABLE", schema = "ADMIN")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role extends Base{

    @Id
    @Column (name="ROLE_ID")
    private UUID id;
    @Column (name="ROLE_NAME")
    @Size(min = 2 , message = "name of the role should be atleast 2 characters")
    private String name;
}

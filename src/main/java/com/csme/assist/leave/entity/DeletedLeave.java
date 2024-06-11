package com.csme.assist.leave.entity;

import javax.persistence.*;

@Entity
@Table(name = "DELETED_LEAVE_TABLE", schema = "PUBLIC")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class  DeletedLeave extends Leave {
    
}

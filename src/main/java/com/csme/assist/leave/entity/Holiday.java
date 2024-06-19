package com.csme.assist.leave.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HOLIDAY_TABLE", schema = "PUBLIC")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Holiday extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="HOLIDAY_ID")
    private int id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "YEAR")
    private int year;
    @Column (name ="DESCRIPTION")
    private String description;
    @Column(name = "START_DATE")
    private LocalDate startDate;
    @Column (name = "END_DATE")
    private LocalDate endDate;
    @Column (name = "NUMBER_OF_DAYS")
    private long numberOfDays;
}

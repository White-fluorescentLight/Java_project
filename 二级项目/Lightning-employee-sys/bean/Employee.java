package com.employee.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private int id;
    private String name;
    private String sex;
    private int age;
    private String phone;
    private String position;
    private String entryDate;
    private double salary;
    private String department;
}


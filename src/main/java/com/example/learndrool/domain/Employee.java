package com.example.learndrool.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String name;
    private int age;
    private BigDecimal income;
    //    private boolean exempt;
    private int rate;
    private String employeeType;
//    private BigDecimal calculatedTaxIncome;
}

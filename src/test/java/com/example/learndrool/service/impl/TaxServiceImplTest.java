package com.example.learndrool.service.impl;

import com.example.learndrool.domain.Employee;
import com.example.learndrool.domain.EmployeeType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
@Slf4j
class TaxServiceImplTest {
    @Autowired
    private TaxService taxService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldSetTaxRateTo0WhenAgeIsBelow60AndIncomeIsBelow250000() {
        Employee employee = new Employee();
        employee.setEmployeeType(EmployeeType.INDIVIDUAL.name());
        employee.setAge(59);
        employee.setIncome(BigDecimal.valueOf(240000));
        Employee employee1 = taxService.applyTax(employee);
        log.info("employee-below-age-60-income-below-250000 - {}", employee1);
        Assertions.assertEquals(employee1.getRate(), 0);
    }

    @Test
    void shouldSetTaxRateTo5WhenAgeIsBelow60AndIncomeIsBetween250000And500000() {
        Employee employee = new Employee();
        employee.setEmployeeType(EmployeeType.INDIVIDUAL.name());
        employee.setAge(59);
        employee.setIncome(BigDecimal.valueOf(250000));
        Employee employee1 = taxService.applyTax(employee);
        log.info("employee-below-age-60-income-between-250000-5000000- {}", employee1);
        Assertions.assertEquals(employee1.getRate(), 5);
    }

    @Test
    void shouldSetTaxRateTo20WhenAgeIsBelow60AndIncomeIsBetween500000And1000000() {
        Employee employee = new Employee();
        employee.setEmployeeType(EmployeeType.INDIVIDUAL.name());
        employee.setAge(59);
        employee.setIncome(BigDecimal.valueOf(600000));
        Employee employee1 = taxService.applyTax(employee);
        log.info("employee-below-age-60-income-between-500000-1000000 - {}", employee1);
        Assertions.assertEquals(employee1.getRate(), 20);
    }

    @Test
    void shouldSetTaxRateTo30WhenAgeIsBelow60AndIncomeIsBetween1000000And100000000() {
        Employee employee = new Employee();
        employee.setEmployeeType(EmployeeType.INDIVIDUAL.name());
        employee.setAge(59);
        employee.setIncome(BigDecimal.valueOf(11000000));
        Employee employee1 = taxService.applyTax(employee);
        log.info("employee-below-age-60-income-between-1000000-1000000000 - {}", employee1);
        Assertions.assertEquals(employee1.getRate(), 30);
    }

    @Test
    void shouldSetTaxRateTo0WhenAge60AndLessThan80AndIncomeIsBelow300000() {
        Employee employee = new Employee();
        employee.setEmployeeType(EmployeeType.INDIVIDUAL.name());
        employee.setAge(70);
        employee.setIncome(BigDecimal.valueOf(200000));
        Employee employee1 = taxService.applyTax(employee);
        log.info("employee-abpve-age-60-less-than-80-income-below-300000 - {}", employee1);
        Assertions.assertEquals(employee1.getRate(), 0);
    }

    @Test
    void shouldSetTaxRateTo5WhenAge60AndLessThan80AndIncomeIsBetween300000And500000() {
        Employee employee = new Employee();
        employee.setEmployeeType(EmployeeType.INDIVIDUAL.name());
        employee.setAge(70);
        employee.setIncome(BigDecimal.valueOf(400000));
        Employee employee1 = taxService.applyTax(employee);
        log.info("employee-above-age-60-less-than-80-income-300000-500000 - {}", employee1);
        Assertions.assertEquals(employee1.getRate(), 5);
    }

    @Test
    void shouldSetTaxRateT2o0WhenAge60AndLessThan80AndIncomeIsBetween500000And1000000() {
        Employee employee = new Employee();
        employee.setEmployeeType(EmployeeType.INDIVIDUAL.name());
        employee.setAge(70);
        employee.setIncome(BigDecimal.valueOf(700000));
        Employee employee1 = taxService.applyTax(employee);
        log.info("employee-above-age-60-less-than-80-income-500000-1000000 - {}", employee1);
        Assertions.assertEquals(employee1.getRate(), 20);
    }

    @Test
    void shouldSetTaxRateTo30WhenAge60AndLessThan80AndIncomeIsAbove1000000() {
        Employee employee = new Employee();
        employee.setEmployeeType(EmployeeType.INDIVIDUAL.name());
        employee.setAge(70);
        employee.setIncome(BigDecimal.valueOf(2000000));
        Employee employee1 = taxService.applyTax(employee);
        log.info("employee-above-age-60-less-than-80-income-above-1000000 - {}", employee1);
        Assertions.assertEquals(employee1.getRate(), 30);
    }


    @Test
    void shouldSetTaxRateTo10WhenIncomeIsLessThan10000() {
        Employee employee = new Employee();
        employee.setEmployeeType(EmployeeType.BUSINESS.name());
        employee.setIncome(BigDecimal.valueOf(9000));
        Employee employee1 = taxService.applyTax(employee);
        log.info("business-income-less-than-10000 - {}", employee1);
        Assertions.assertEquals(employee1.getRate(), 10);
    }

    @Test
    void shouldSetTaxRateTo20WhenIncomeIsGreaterThan10000ButLessThan20000() {
        Employee employee = new Employee();
        employee.setEmployeeType(EmployeeType.BUSINESS.name());
        employee.setIncome(BigDecimal.valueOf(15000));
        Employee employee1 = taxService.applyTax(employee);
        log.info("business-income-greater-than-10000-but-less-than-20000 - {}", employee1);
        Assertions.assertEquals(employee1.getRate(), 20);
    }

    @Test
    void shouldSetTaxRateTo30WhenIncomeIsGreaterThan20000() {
        Employee employee = new Employee();
        employee.setEmployeeType(EmployeeType.BUSINESS.name());
        employee.setIncome(BigDecimal.valueOf(5000000));
        Employee employee1 = taxService.applyTax(employee);
        log.info("business-income-greater-than-20000 - {}", employee1);
        Assertions.assertEquals(employee1.getRate(), 30);
    }
}

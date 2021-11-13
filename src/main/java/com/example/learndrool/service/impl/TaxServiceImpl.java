package com.example.learndrool.service.impl;

import com.example.learndrool.domain.Employee;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

@Service
public class TaxServiceImpl implements TaxService {
    private final KieSession kieSession;

    public TaxServiceImpl(KieSession kieSession) {
        this.kieSession = kieSession;
    }

    @Override
    public Employee applyTax(Employee employee) {
        kieSession.insert(employee);
        kieSession.fireAllRules();
        return employee;
    }
}

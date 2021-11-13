package com.example.learndrool.service.impl;

import com.example.learndrool.domain.Order;
import com.example.learndrool.service.OrderService;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final KieSession kieSession;

    public OrderServiceImpl(KieSession kieSession) {
        this.kieSession = kieSession;
    }

    @Override
    public Order applyDiscount(Order order) {
        kieSession.insert(order);
        kieSession.fireAllRules();
        return order;
    }
}

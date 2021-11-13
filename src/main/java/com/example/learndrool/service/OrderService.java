package com.example.learndrool.service;

import com.example.learndrool.domain.Order;

public interface OrderService {
    Order applyDiscount(Order order);
}

package com.example.learndrool.service.impl;

import com.example.learndrool.domain.Order;
import com.example.learndrool.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldApplyDBSDiscount() {
        Order order = new Order();
        order.setName("Item 1");
        order.setCardType("DBS");
        order.setPrice(1501);
        Order order1 = orderService.applyDiscount(order);
        log.info("order: {}", order1);
        Assertions.assertEquals(order1.getDiscount(), 15);
    }

    @Test
    void shouldApplyHDFCDiscount() {
        Order order = new Order();
        order.setName("Item 1");
        order.setCardType("HDFC");
        order.setPrice(10001);
        Order order1 = orderService.applyDiscount(order);
        log.info("order: {}", order1);
        Assertions.assertEquals(order1.getDiscount(), 10);
    }
}

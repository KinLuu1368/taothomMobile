package com.lab08.main.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.lab08.main.Entity.Order;

public interface OrderService {
    Order create(JsonNode orderData);

    Order findById(Long id);

    List<Order> findAll();

    List<Order> findByUsername(String username);
}

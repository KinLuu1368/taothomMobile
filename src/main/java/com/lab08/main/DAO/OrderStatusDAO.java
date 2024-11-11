package com.lab08.main.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lab08.main.Entity.Order;
import com.lab08.main.Entity.OrderStatus;

public interface OrderStatusDAO extends JpaRepository<OrderStatus, Long> {

}

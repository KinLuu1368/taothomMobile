package com.lab08.main.DAO;

import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lab08.main.Entity.Order;
import com.lab08.main.Entity.OrderDetail;

import jakarta.transaction.Transactional;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM OrderDetail od WHERE od.order.id = :orderId")
    void deleteByOrderId(Integer orderId);
}

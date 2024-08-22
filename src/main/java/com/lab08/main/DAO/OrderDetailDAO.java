package com.lab08.main.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab08.main.Entity.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Integer> {

}

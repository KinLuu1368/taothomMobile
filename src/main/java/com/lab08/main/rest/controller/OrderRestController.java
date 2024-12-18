package com.lab08.main.rest.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.lab08.main.DAO.OrderDAO;
import com.lab08.main.DAO.OrderDetailDAO;
import com.lab08.main.DAO.OrderStatusDAO;
import com.lab08.main.Entity.Order;
import com.lab08.main.Entity.OrderDetail;
import com.lab08.main.Entity.OrderStatus;
import com.lab08.main.Entity.Product;
import com.lab08.main.service.OrderService;


@CrossOrigin("*")
@RestController
@RequestMapping("/rest/order")
public class OrderRestController {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailDAO oddao;

    @Autowired
    OrderDAO odao;

    @Autowired
    OrderStatusDAO orderStatusDAO;

    @PostMapping()
    public Order create(@RequestBody JsonNode orderData) {
        return orderService.create(orderData);
    }

    @GetMapping()
    public List<Order> getAll() {
        return orderService.findAll();
    }

    @GetMapping("/orderdetail")
    public List<OrderDetail> getAllDetails() {
        return oddao.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        // Xóa chi tiết đơn hàng liên quan
        orderService.deleteOrderDetailsByOrderId(id);
        // Xóa đơn hàng
        Long idLong = Long.valueOf(id);
        orderService.deleteOrderById(idLong);
    }
    
    @PutMapping("/{id}")
    public Order update(@PathVariable("id") Integer id,
            @RequestBody Order order) {
        return orderService.update(order);
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable("id") Integer id) {
        Long idL = Long.valueOf(id);
        return orderService.findById(idL);
    }

}
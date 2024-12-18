package com.lab08.main.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab08.main.DAO.OrderDAO;
import com.lab08.main.DAO.OrderDetailDAO;
import com.lab08.main.DAO.OrderStatusDAO;
import com.lab08.main.Entity.Order;
import com.lab08.main.Entity.OrderDetail;
import com.lab08.main.Entity.OrderStatus;
import com.lab08.main.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDAO odao;
    @Autowired
    OrderDetailDAO ddao;
    @Autowired
    OrderStatusDAO orderStatusDAO;

    @Override
    public Order create(JsonNode orderData) {
        ObjectMapper mapper = new ObjectMapper();

        Order order = mapper.convertValue(orderData, Order.class);
        odao.save(order);

        TypeReference<List<OrderDetail>> type = new TypeReference<List<OrderDetail>>() {
        };
        List<OrderDetail> details = mapper.convertValue(orderData.get("orderDetails"), type)
                .stream().peek(d -> d.setOrder(order)).collect(Collectors.toList());
        ddao.saveAll(details);
        return order;
    }

    @Override
    public Order findById(Long id) {
        return odao.findById(id).get();
    }

    @Override
    public List<Order> findByUsername(String username) {
        return odao.findByuUsername(username);
    }

    @Override
    public List<Order> findAll() {
        return odao.findAll();
    }

    @Override
    public void deleteOrderById(Long id) {
        odao.deleteById(id);
    }

    @Override
    public void deleteOrderDetailsByOrderId(Integer orderId) {
        ddao.deleteByOrderId(orderId);
    }

    @Override 
    public Order update(Order order) {
        return odao.save(order);
    }

    @Override 
    public Order save(Order order) {
        return odao.save(order);
    }

    @Override
    public void updateOrderStatus(Long orderId, int idLong) {
        // Lấy đơn hàng từ repository
        Order order = odao.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        // Lấy trạng thái "Đã xử lý" từ OrderStatus repository
        OrderStatus processedStatus = orderStatusDAO.getById(Long.valueOf(idLong));

        // Cập nhật trạng thái đơn hàng
        order.setOrderStatus(processedStatus);
        
        // Lưu thay đổi vào cơ sở dữ liệu
        odao.save(order);
    }

}

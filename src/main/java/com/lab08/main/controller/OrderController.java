package com.lab08.main.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lab08.main.DAO.OrderDAO;
import com.lab08.main.DAO.OrderStatusDAO;
import com.lab08.main.Entity.Order;
import com.lab08.main.Entity.OrderStatus;
import com.lab08.main.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderDAO odao;

    @Autowired
    private OrderStatusDAO orderStatusDAO;

    @RequestMapping("/order/checkout")
    public String checkout() {
        return "order/checkout";
    }

    @RequestMapping("/order/list")
    public String list(Model model, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        System.out.println(username);
        model.addAttribute("orders", orderService.findByUsername(username));
        return "order/list";
    }

    @RequestMapping("/order/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        System.out.println(orderService.findById(id).getOrderDetails().get(0).getId());
        return "order/detail";
    }

    @RequestMapping("/order/detail1/{id}")
    public String detail1(@PathVariable("id") Long id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        System.out.println(orderService.findById(id).getOrderDetails().get(0).getId());

        Order order = orderService.findById(id);
        double sumOrder = order.getOrderDetails().stream()
                .mapToDouble(detail -> detail.getPrice() * detail.getQuantity())
                .sum();

        model.addAttribute("sumOrder", sumOrder);

        return "order/detail1";
    }

    @PostMapping("/order/process/{id}")
    public String processOrder(@PathVariable Long id, Model model) {
        // Gọi service để xử lý đơn hàng
        orderService.updateOrderStatus(id, 2); // 2 là ID của trạng thái "Đã xử lý"
        
        // Sau khi cập nhật trạng thái, trả về trang chi tiết đơn hàng
        return "redirect:/order/detail1/" + id; // Redirect lại chi tiết đơn hàng
    }

    @PostMapping("/order/cancel/{id}")
    public String cancelOrder(@PathVariable Long id, Model model) {
        // Gọi service để xử lý đơn hàng
        orderService.updateOrderStatus(id, 3); // 2 là ID của trạng thái "Đã xử lý"
        
        // Sau khi cập nhật trạng thái, trả về trang chi tiết đơn hàng
        return "redirect:/order/detail1/" + id; // Redirect lại chi tiết đơn hàng
    }

}

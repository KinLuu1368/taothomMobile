package com.lab08.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lab08.main.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

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
        return "order/detail";
    }
}

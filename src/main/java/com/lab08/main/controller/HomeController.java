package com.lab08.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lab08.main.Entity.Account;
import com.lab08.main.service.AccountService;

@Controller
public class HomeController {
     @Autowired
    private AccountService accountService;
    
    @RequestMapping({ "/", "/home/index" })
    public String home(Model model) {
        return "redirect:/product/list";
    }

    @RequestMapping({ "/1", "/home/index" })
    public String home1() {
        return "home";
    }

    @RequestMapping({ "/admin", "/admin/home/index" })
    public String admin() {
        return "redirect:/assets/admin/index.html";
    }
}

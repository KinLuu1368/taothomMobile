package com.lab08.main.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lab08.main.Entity.Category;
import com.lab08.main.Entity.Product;
import com.lab08.main.service.CategoryService;
import com.lab08.main.service.ProductModelService;
import com.lab08.main.service.ProductService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class HomeController {    
    @Autowired
    ProductService productService;

    @Autowired
    ProductModelService productModelService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping({ "/", "/home/index" })
    public String home(Model model) {
         // Lấy tất cả sản phẩm
        List<Product> products = productService.findAll();

        // Lấy tất cả danh mục và sản phẩm trong từng danh mục
        List<Category> categories = categoryService.findAll();

        // Đưa dữ liệu sản phẩm vào model
        model.addAttribute("products", products);
        
        // Đưa danh mục và sản phẩm trong từng danh mục vào model
        model.addAttribute("categories", categories);
        return "home";
    }

    @RequestMapping({ "/admin", "/admin/home/index" })
    public String admin() {
        return "redirect:/assets/admin/index.html";
    }

    @RequestMapping("/security/unauthorized")
    public String FailToAdmin(Model model) {
        return "redirect:/";
    }
    
}

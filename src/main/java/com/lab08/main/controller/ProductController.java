package com.lab08.main.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lab08.main.Entity.Product;
import com.lab08.main.Entity.ProductModel;
import com.lab08.main.Entity.Series;
import com.lab08.main.service.SeriesService;
import com.lab08.main.service.ProductModelService;
import com.lab08.main.service.ProductService;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductModelService productModelService;

    @Autowired
    SeriesService seriesService;

    @RequestMapping("/product/list")
    public String list(Model model, @RequestParam("cid") Optional<String> cid, @RequestParam("mid") Optional<String> mid,  @RequestParam("sid") Optional<String> sid) {
        List<Series> sList = seriesService.findAll();
        model.addAttribute("sList", sList);
        if (cid.isPresent()) {
            List<Product> list = productService.findByCategoryId(cid.get());
            model.addAttribute("items", list);
        } else if (mid.isPresent()) {
            // Tìm sản phẩm theo model id
            Integer midInteger = Integer.valueOf(mid.get());
            List<Product> list = productService.findByProductModelId(midInteger);
            model.addAttribute("items", list);
        } else {
            List<ProductModel> list = productModelService.findAll();
            model.addAttribute("items", list);
        }
        return "product/list";
    }

    @RequestMapping("/product/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Product item = productService.findById(id);
        model.addAttribute("item", item);
        return "product/detail";
    }
}

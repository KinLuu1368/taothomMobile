package com.lab08.main.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lab08.main.Entity.Product;
import com.lab08.main.service.ProductModelService;
import com.lab08.main.service.ProductService;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductModelService productModelService;
    
    public List<Product> getLowList(List<Product> products) {
        // Lọc sản phẩm để chỉ lấy sản phẩm có giá thấp nhất cho mỗi ProductModel và ProductCapacity
        Map<Integer, Map<Integer, Product>> lowestPriceProductsMap = products.stream()
            .collect(Collectors.groupingBy(
                product -> product.getProductModel().getId(),  // Nhóm theo ProductModel ID
                Collectors.toMap(
                    product -> product.getProductCapacity().getId(),  // Nhóm tiếp theo theo ProductCapacity ID
                    product -> product, // Giá trị là sản phẩm
                    (existing, replacement) -> existing.getPrice() < replacement.getPrice() ? existing : replacement // Giữ sản phẩm có giá thấp hơn
                )
            ));
    
        // Chuyển đổi Map thành List bằng cách trích xuất các sản phẩm từ map lồng nhau
        List<Product> lowestPriceProducts = lowestPriceProductsMap.values().stream()
            .flatMap(capacityMap -> capacityMap.values().stream())  // Lấy tất cả sản phẩm từ từng Capacity group
            .collect(Collectors.toList());
        
        return lowestPriceProducts;
    }
    
    

    @RequestMapping("/product/list")
    public String list(Model model, @RequestParam("cid") Optional<String> cid, @RequestParam("name") Optional<String> name) {
        if (cid.isPresent()) {
            List<Product> products = productService.findByCategoryId(cid.get());
            List<Product> lowestPriceProducts = getLowList(products);
            model.addAttribute("items", lowestPriceProducts);
            try {
                model.addAttribute("nameSeries", products.get(0).getCategory().getName());
            } catch (Exception e) {
                model.addAttribute("nameSeries", "Sản phẩm đang hết hàng hoặc đang cập nhật");
            }
            return "product/list";
        } else if (name.isPresent()) {
            List<Product> products = productService.findByName(name.get());
            List<Product> lowestPriceProducts = getLowList(products);
            model.addAttribute("items", lowestPriceProducts);

            return "product/list";
        } else {
            List<Product> products = productService.findAll();
            List<Product> lowestPriceProducts = getLowList(products);

            model.addAttribute("items", lowestPriceProducts);
            return "product/list";
        }
    }

    @RequestMapping("/product/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Product item = productService.findById(id);
        model.addAttribute("item", item);
        model.addAttribute("image", item.getImage());
        int productModelId = item.getProductModel().getId();
        List<Product> list = productService.findByProductModelId(productModelId);
        model.addAttribute("same", list);
        return "product/detail";
    }
}

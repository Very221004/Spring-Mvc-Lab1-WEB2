package com.example.spring_mvc_lab1.controller;

import com.example.spring_mvc_lab1.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsController {

    private final ProductService productService;

    public StatisticsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/statistics")
    public String showStatistics(Model model) {

        model.addAttribute("title", "Statistik Produk");
        model.addAttribute("totalProducts", productService.getTotalProducts());
        model.addAttribute("categoryStats", productService.getTotalPerCategory());
        model.addAttribute("mostExpensive", productService.getMostExpensiveProduct().orElse(null));
        model.addAttribute("cheapest", productService.getCheapestProduct().orElse(null));
        model.addAttribute("averagePrice", productService.getAveragePrice());
        model.addAttribute("lowStockCount", productService.countLowStockProducts());

        return "statistics";
    }
}
package com.example.spring_mvc_lab1.controller;

import com.example.spring_mvc_lab1.model.Product;
import com.example.spring_mvc_lab1.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // Constructor Injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ===============================
    // LIST SEMUA PRODUK
    // ===============================
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.findAll();

        model.addAttribute("products", products);
        model.addAttribute("title", "Daftar Produk");
        model.addAttribute("totalProducts", products.size());

        return "product/list";
    }

    // ===============================
    // DETAIL PRODUK
    // ===============================
    @GetMapping("/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.findById(id);

        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            model.addAttribute("title", "Detail: " + product.get().getName());
        } else {
            model.addAttribute("error", "Produk dengan ID " + id + " tidak ditemukan");
            model.addAttribute("title", "Produk Tidak Ditemukan");
        }

        return "product/detail";
    }

    // ===============================
    // FILTER BERDASARKAN KATEGORI
    // ===============================
    @GetMapping("/category/{category}")
    public String productsByCategory(@PathVariable String category, Model model) {
        List<Product> products = productService.findByCategory(category);

        model.addAttribute("products", products);
        model.addAttribute("title", "Kategori: " + category);
        model.addAttribute("totalProducts", products.size());
        model.addAttribute("selectedCategory", category);

        return "product/list";
    }

    // ===============================
    // SEARCH PRODUK
    // ===============================
    @GetMapping("/search")
    public String searchProducts(@RequestParam(defaultValue = "") String keyword,
                                 Model model) {
        List<Product> products = keyword.isBlank()
                ? productService.findAll()
                : productService.search(keyword);

        model.addAttribute("products", products);
        model.addAttribute("title", "Hasil Pencarian: " + keyword);
        model.addAttribute("totalProducts", products.size());
        model.addAttribute("keyword", keyword);

        return "product/list";
    }

    // ===============================
    // ✅ EKSPERIMEN 4B
    // CATEGORY SUMMARY
    // ===============================
    @GetMapping("/categories")
    public String showCategories(Model model) {

        List<String> categories = productService.getAllCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("title", "Daftar Kategori");

        return "product/categories";
        // → nanti buat file: templates/product/categories.html
    }
}
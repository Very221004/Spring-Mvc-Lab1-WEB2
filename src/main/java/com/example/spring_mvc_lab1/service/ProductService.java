package com.example.spring_mvc_lab1.service;

import com.example.spring_mvc_lab1.model.Product;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();

    public ProductService() {
        products.add(new Product(1L, "Laptop ASUS", "Elektronik", 12_500_000, 15));
        products.add(new Product(2L, "Mouse Logitech", "Elektronik", 350_000, 50));
        products.add(new Product(3L, "Buku Java Programming", "Buku", 150_000, 30));
        products.add(new Product(4L, "Kopi Arabica 250g", "Makanan", 85_000, 100));
        products.add(new Product(5L, "Headphone Sony", "Elektronik", 1_200_000, 20));
        products.add(new Product(6L, "Novel Laskar Pelangi", "Buku", 75_000, 45));
    }

    // =========================
    // BASIC METHODS
    // =========================

    public List<Product> findAll() {
        return products;
    }

    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public List<Product> findByCategory(String category) {
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    public List<Product> search(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    // =========================
    // STATISTICS METHODS
    // =========================

    // Total semua produk
    public long getTotalProducts() {
        return products.size();
    }

    // Total produk per kategori
    public Map<String, Long> getTotalPerCategory() {
        return products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.counting()
                ));
    }

    // Produk termahal
    public Optional<Product> getMostExpensiveProduct() {
        return products.stream()
                .max(Comparator.comparingDouble(Product::getPrice));
    }

    // Produk termurah
    public Optional<Product> getCheapestProduct() {
        return products.stream()
                .min(Comparator.comparingDouble(Product::getPrice));
    }

    // Rata-rata harga
    public double getAveragePrice() {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0.0);
    }

    // Jumlah stok < 20
    public long countLowStockProducts() {
        return products.stream()
                .filter(p -> p.getStock() < 20)
                .count();
    }
    // Ambil semua kategori unik
    public List<String> getAllCategories() {
        return products.stream()
                .map(Product::getCategory)
                .distinct()
                .toList();
    }
}
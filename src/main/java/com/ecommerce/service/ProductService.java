package com.ecommerce.service;

import com.ecommerce.model.Product;
import com.ecommerce.model.Category;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> findAllActive(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable);
    }

    public Page<Product> findByCategory(Category category, Pageable pageable) {
        return productRepository.findByActiveTrueAndCategory(category, pageable);
    }

    public Page<Product> searchByName(String name, Pageable pageable) {
        return productRepository.findByActiveTrueAndNameContainingIgnoreCase(name, pageable);
    }

    public Page<Product> findByFilters(Category category, BigDecimal minPrice, BigDecimal maxPrice, String name, Pageable pageable) {
        return productRepository.findByFilters(category, minPrice, maxPrice, name, pageable);
    }

    public List<Product> findLatestProducts() {
        return productRepository.findTop8ByActiveTrueOrderByCreatedAtDesc();
    }

    public List<Product> findInStockProducts() {
        return productRepository.findByActiveTrueAndStockQuantityGreaterThan(0);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public boolean isInStock(Product product, int quantity) {
        return product.getStockQuantity() >= quantity;
    }

    public void updateStock(Product product, int quantity) {
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
    }

    public void restoreStock(Product product, int quantity) {
        product.setStockQuantity(product.getStockQuantity() + quantity);
        productRepository.save(product);
    }
}
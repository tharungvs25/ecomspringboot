package com.ecommerce.config;

import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Initialize all data in proper order
            initializeRoles();
            initializeAdminUser();
            initializeCategories();
            initializeProducts();
            
            System.out.println("Data initialization completed successfully!");
        } catch (Exception e) {
            System.out.println("Data initialization skipped due to error: " + e.getMessage());
            // Continue without failing the application startup
        }
    }

    private void initializeRoles() {
        if (roleRepository.count() == 0) {
            Role userRole = new Role(Role.RoleName.ROLE_USER, "Standard user role");
            Role adminRole = new Role(Role.RoleName.ROLE_ADMIN, "Administrator role");
            Role sellerRole = new Role(Role.RoleName.ROLE_SELLER, "Seller role");

            roleRepository.save(userRole);
            roleRepository.save(adminRole);
            roleRepository.save(sellerRole);
        }
    }

    private void initializeAdminUser() {
        if (!userRepository.existsByUsername("admin")) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@ecommerce.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEnabled(true);

            Role adminRole = roleRepository.findByName(Role.RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));
            
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            admin.setRoles(roles);

            userRepository.save(admin);
        }
    }

    private void initializeCategories() {
        if (categoryRepository.count() == 0) {
            Category electronics = new Category("Electronics", "Electronic devices and gadgets");
            Category clothing = new Category("Clothing", "Fashion and apparel");
            Category books = new Category("Books", "Books and educational materials");
            Category home = new Category("Home & Garden", "Home and garden products");
            Category sports = new Category("Sports", "Sports and fitness equipment");

            categoryRepository.save(electronics);
            categoryRepository.save(clothing);
            categoryRepository.save(books);
            categoryRepository.save(home);
            categoryRepository.save(sports);
        }
    }

    private void initializeProducts() {
        if (productRepository.count() == 0) {
            Category electronics = categoryRepository.findByNameIgnoreCase("Electronics").orElse(null);
            Category clothing = categoryRepository.findByNameIgnoreCase("Clothing").orElse(null);
            Category books = categoryRepository.findByNameIgnoreCase("Books").orElse(null);

            // Electronics
            if (electronics != null) {
                Product laptop = new Product("Gaming Laptop", "High-performance gaming laptop with RTX graphics", new BigDecimal("107999.00"), 10);
                laptop.setCategory(electronics);
                laptop.setImageUrl("/images/laptop.jpg");
                productRepository.save(laptop);

                Product smartphone = new Product("Smartphone Pro", "Latest smartphone with advanced camera", new BigDecimal("74999.00"), 25);
                smartphone.setCategory(electronics);
                smartphone.setImageUrl("/images/smartphone.jpg");
                productRepository.save(smartphone);

                Product headphones = new Product("Wireless Headphones", "Premium wireless headphones with noise cancellation", new BigDecimal("16599.00"), 50);
                headphones.setCategory(electronics);
                headphones.setImageUrl("/images/headphones.jpg");
                productRepository.save(headphones);
            }

            // Clothing
            if (clothing != null) {
                Product tshirt = new Product("Cotton T-Shirt", "Comfortable cotton t-shirt available in multiple colors", new BigDecimal("2499.00"), 100);
                tshirt.setCategory(clothing);
                tshirt.setImageUrl("/images/tshirt.jpg");
                productRepository.save(tshirt);

                Product jeans = new Product("Classic Jeans", "Premium denim jeans with perfect fit", new BigDecimal("6599.00"), 75);
                jeans.setCategory(clothing);
                jeans.setImageUrl("/images/jeans.jpg");
                productRepository.save(jeans);
            }

            // Books
            if (books != null) {
                Product book1 = new Product("Spring Boot Guide", "Complete guide to Spring Boot development", new BigDecimal("4149.00"), 30);
                book1.setCategory(books);
                book1.setImageUrl("/images/springbook.jpg");
                productRepository.save(book1);

                Product book2 = new Product("Java Programming", "Comprehensive Java programming handbook", new BigDecimal("3319.00"), 40);
                book2.setCategory(books);
                book2.setImageUrl("/images/javabook.jpg");
                productRepository.save(book2);
            }
        }
    }
}
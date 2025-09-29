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
            Category beauty = new Category("Beauty & Personal Care", "Beauty products and personal care items");
            Category automotive = new Category("Automotive", "Car accessories and automotive products");
            Category toys = new Category("Toys & Games", "Toys, games and entertainment products");

            categoryRepository.save(electronics);
            categoryRepository.save(clothing);
            categoryRepository.save(books);
            categoryRepository.save(home);
            categoryRepository.save(sports);
            categoryRepository.save(beauty);
            categoryRepository.save(automotive);
            categoryRepository.save(toys);
        }
    }

    private void initializeProducts() {
        if (productRepository.count() == 0) {
            Category electronics = categoryRepository.findByNameIgnoreCase("Electronics").orElse(null);
            Category clothing = categoryRepository.findByNameIgnoreCase("Clothing").orElse(null);
            Category books = categoryRepository.findByNameIgnoreCase("Books").orElse(null);
            Category home = categoryRepository.findByNameIgnoreCase("Home & Garden").orElse(null);
            Category sports = categoryRepository.findByNameIgnoreCase("Sports").orElse(null);
            Category beauty = categoryRepository.findByNameIgnoreCase("Beauty & Personal Care").orElse(null);
            Category automotive = categoryRepository.findByNameIgnoreCase("Automotive").orElse(null);
            Category toys = categoryRepository.findByNameIgnoreCase("Toys & Games").orElse(null);

            // Electronics
            if (electronics != null) {
                Product laptop = new Product("Gaming Laptop", "High-performance gaming laptop with RTX graphics", new BigDecimal("107999.00"), 10);
                laptop.setCategory(electronics);
                laptop.setImageUrl("https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=400&h=300&fit=crop&crop=center");
                productRepository.save(laptop);

                Product smartphone = new Product("Smartphone Pro", "Latest smartphone with advanced camera", new BigDecimal("74999.00"), 25);
                smartphone.setCategory(electronics);
                smartphone.setImageUrl("https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=400&h=300&fit=crop&crop=center");
                productRepository.save(smartphone);

                Product headphones = new Product("Wireless Headphones", "Premium wireless headphones with noise cancellation", new BigDecimal("16599.00"), 50);
                headphones.setCategory(electronics);
                headphones.setImageUrl("https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400&h=300&fit=crop&crop=center");
                productRepository.save(headphones);

                Product tablet = new Product("10-inch Tablet", "Lightweight tablet perfect for work and entertainment", new BigDecimal("32999.00"), 30);
                tablet.setCategory(electronics);
                tablet.setImageUrl("https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=400&h=300&fit=crop&crop=center");
                productRepository.save(tablet);

                Product smartwatch = new Product("Fitness Smartwatch", "Advanced smartwatch with health tracking features", new BigDecimal("19999.00"), 40);
                smartwatch.setCategory(electronics);
                smartwatch.setImageUrl("https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=400&h=300&fit=crop&crop=center");
                productRepository.save(smartwatch);

                Product webcam = new Product("4K Webcam", "Professional 4K webcam for streaming and video calls", new BigDecimal("8999.00"), 35);
                webcam.setCategory(electronics);
                webcam.setImageUrl("https://images.unsplash.com/photo-1587355760421-b9de3226a046?w=400&h=300&fit=crop&crop=center");
                productRepository.save(webcam);

                Product keyboard = new Product("Mechanical Keyboard", "RGB mechanical keyboard for gaming and typing", new BigDecimal("7499.00"), 55);
                keyboard.setCategory(electronics);
                keyboard.setImageUrl("https://images.unsplash.com/photo-1541140532154-b024d705b90a?w=400&h=300&fit=crop&crop=center");
                productRepository.save(keyboard);
            }

            // Clothing
            if (clothing != null) {
                Product tshirt = new Product("Cotton T-Shirt", "Comfortable cotton t-shirt available in multiple colors", new BigDecimal("2499.00"), 100);
                tshirt.setCategory(clothing);
                tshirt.setImageUrl("https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=400&h=300&fit=crop&crop=center");
                productRepository.save(tshirt);

                Product jeans = new Product("Classic Jeans", "Premium denim jeans with perfect fit", new BigDecimal("6599.00"), 75);
                jeans.setCategory(clothing);
                jeans.setImageUrl("https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=400&h=300&fit=crop&crop=center");
                productRepository.save(jeans);

                Product hoodie = new Product("Premium Hoodie", "Soft fleece hoodie perfect for casual wear", new BigDecimal("4999.00"), 60);
                hoodie.setCategory(clothing);
                hoodie.setImageUrl("https://images.unsplash.com/photo-1556821840-3a63f95609a7?w=400&h=300&fit=crop&crop=center");
                productRepository.save(hoodie);

                Product sneakers = new Product("Running Sneakers", "Lightweight running shoes with excellent cushioning", new BigDecimal("8999.00"), 45);
                sneakers.setCategory(clothing);
                sneakers.setImageUrl("https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400&h=300&fit=crop&crop=center");
                productRepository.save(sneakers);

                Product jacket = new Product("Winter Jacket", "Waterproof winter jacket with thermal insulation", new BigDecimal("12999.00"), 25);
                jacket.setCategory(clothing);
                jacket.setImageUrl("https://images.unsplash.com/photo-1544966503-7cc5ac882d5d?w=400&h=300&fit=crop&crop=center");
                productRepository.save(jacket);

                Product dress = new Product("Summer Dress", "Elegant summer dress in floral pattern", new BigDecimal("5999.00"), 35);
                dress.setCategory(clothing);
                dress.setImageUrl("https://images.unsplash.com/photo-1595777457583-95e059d581b8?w=400&h=300&fit=crop&crop=center");
                productRepository.save(dress);

                Product cap = new Product("Baseball Cap", "Classic baseball cap with adjustable strap", new BigDecimal("1999.00"), 80);
                cap.setCategory(clothing);
                cap.setImageUrl("https://images.unsplash.com/photo-1588850561407-ed78c282e89b?w=400&h=300&fit=crop&crop=center");
                productRepository.save(cap);
            }

            // Books
            if (books != null) {
                Product book1 = new Product("Spring Boot Guide", "Complete guide to Spring Boot development", new BigDecimal("4149.00"), 30);
                book1.setCategory(books);
                book1.setImageUrl("https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=400&h=300&fit=crop&crop=center");
                productRepository.save(book1);

                Product book2 = new Product("Java Programming", "Comprehensive Java programming handbook", new BigDecimal("3319.00"), 40);
                book2.setCategory(books);
                book2.setImageUrl("https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=400&h=300&fit=crop&crop=center");
                productRepository.save(book2);

                Product cookbook = new Product("Healthy Cooking", "Collection of nutritious and delicious recipes", new BigDecimal("2799.00"), 50);
                cookbook.setCategory(books);
                cookbook.setImageUrl("https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&h=300&fit=crop&crop=center");
                productRepository.save(cookbook);

                Product novel = new Product("Mystery Novel", "Bestselling thriller novel with unexpected twists", new BigDecimal("1999.00"), 65);
                novel.setCategory(books);
                novel.setImageUrl("https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400&h=300&fit=crop&crop=center");
                productRepository.save(novel);

                Product textbook = new Product("Data Science Handbook", "Essential guide to data science and machine learning", new BigDecimal("5999.00"), 25);
                textbook.setCategory(books);
                textbook.setImageUrl("https://images.unsplash.com/photo-1532012197267-da84d127e765?w=400&h=300&fit=crop&crop=center");
                productRepository.save(textbook);

                Product artBook = new Product("Photography Art Book", "Stunning collection of landscape photography", new BigDecimal("3599.00"), 20);
                artBook.setCategory(books);
                artBook.setImageUrl("https://images.unsplash.com/photo-1513475382585-d06e58bcb0e0?w=400&h=300&fit=crop&crop=center");
                productRepository.save(artBook);
            }

            // Home & Garden
            if (home != null) {
                Product coffeeMaker = new Product("Premium Coffee Maker", "Automatic coffee maker with programmable features", new BigDecimal("8299.00"), 20);
                coffeeMaker.setCategory(home);
                coffeeMaker.setImageUrl("https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400&h=300&fit=crop&crop=center");
                productRepository.save(coffeeMaker);

                Product plant = new Product("Indoor Plant Collection", "Beautiful indoor plants for home decoration", new BigDecimal("1999.00"), 45);
                plant.setCategory(home);
                plant.setImageUrl("https://images.unsplash.com/photo-1416879595882-3373a0480b5b?w=400&h=300&fit=crop&crop=center");
                productRepository.save(plant);

                Product candles = new Product("Scented Candle Set", "Relaxing aromatherapy candles in various scents", new BigDecimal("2999.00"), 70);
                candles.setCategory(home);
                candles.setImageUrl("https://images.unsplash.com/photo-1602874801006-2bd9c93bb1ae?w=400&h=300&fit=crop&crop=center");
                productRepository.save(candles);

                Product blender = new Product("High-Speed Blender", "Professional blender for smoothies and soups", new BigDecimal("12999.00"), 15);
                blender.setCategory(home);
                blender.setImageUrl("https://images.unsplash.com/photo-1570197788417-0e82375c9371?w=400&h=300&fit=crop&crop=center");
                productRepository.save(blender);

                Product cushions = new Product("Decorative Cushions", "Set of colorful throw cushions for living room", new BigDecimal("3999.00"), 55);
                cushions.setCategory(home);
                cushions.setImageUrl("https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=400&h=300&fit=crop&crop=center");
                productRepository.save(cushions);

                Product vase = new Product("Ceramic Vase", "Elegant ceramic vase for flower arrangements", new BigDecimal("4999.00"), 30);
                vase.setCategory(home);
                vase.setImageUrl("https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400&h=300&fit=crop&crop=center");
                productRepository.save(vase);

                Product lampShade = new Product("Modern Table Lamp", "Contemporary table lamp with adjustable brightness", new BigDecimal("6999.00"), 25);
                lampShade.setCategory(home);
                lampShade.setImageUrl("https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&h=300&fit=crop&crop=center");
                productRepository.save(lampShade);
            }

            // Sports
            if (sports != null) {
                Product yogaMat = new Product("Premium Yoga Mat", "High-quality yoga mat with excellent grip", new BigDecimal("2999.00"), 60);
                yogaMat.setCategory(sports);
                yogaMat.setImageUrl("https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=400&h=300&fit=crop&crop=center");
                productRepository.save(yogaMat);

                Product dumbbell = new Product("Adjustable Dumbbells", "Professional adjustable dumbbells for home gym", new BigDecimal("12999.00"), 15);
                dumbbell.setCategory(sports);
                dumbbell.setImageUrl("https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=400&h=300&fit=crop&crop=center");
                productRepository.save(dumbbell);

                Product basketball = new Product("Professional Basketball", "Official size basketball for indoor and outdoor play", new BigDecimal("2499.00"), 40);
                basketball.setCategory(sports);
                basketball.setImageUrl("https://images.unsplash.com/photo-1546519638-68e109498ffc?w=400&h=300&fit=crop&crop=center");
                productRepository.save(basketball);

                Product tennisRacket = new Product("Tennis Racket", "Lightweight tennis racket for intermediate players", new BigDecimal("8999.00"), 20);
                tennisRacket.setCategory(sports);
                tennisRacket.setImageUrl("https://images.unsplash.com/photo-1622279457486-62dcc4a431d6?w=400&h=300&fit=crop&crop=center");
                productRepository.save(tennisRacket);

                Product swimwear = new Product("Swimming Goggles", "Professional swimming goggles with UV protection", new BigDecimal("1999.00"), 50);
                swimwear.setCategory(sports);
                swimwear.setImageUrl("https://images.unsplash.com/photo-1530549387789-4c1017266635?w=400&h=300&fit=crop&crop=center");
                productRepository.save(swimwear);

                Product bicycle = new Product("Mountain Bike", "Durable mountain bike for off-road adventures", new BigDecimal("45999.00"), 8);
                bicycle.setCategory(sports);
                bicycle.setImageUrl("https://images.unsplash.com/photo-1558618047-3c8c76ca7d13?w=400&h=300&fit=crop&crop=center");
                productRepository.save(bicycle);

                Product skiingGear = new Product("Ski Helmet", "Safety helmet for skiing and snowboarding", new BigDecimal("7999.00"), 18);
                skiingGear.setCategory(sports);
                skiingGear.setImageUrl("https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400&h=300&fit=crop&crop=center");
                productRepository.save(skiingGear);

                Product fitnessTracker = new Product("Fitness Tracker Band", "Waterproof fitness tracker with heart rate monitor", new BigDecimal("4999.00"), 35);
                fitnessTracker.setCategory(sports);
                fitnessTracker.setImageUrl("https://images.unsplash.com/photo-1575311373937-040b8e1fd5b6?w=400&h=300&fit=crop&crop=center");
                productRepository.save(fitnessTracker);
            }

            // Beauty & Personal Care
            if (beauty != null) {
                Product skincare = new Product("Anti-Aging Serum", "Premium anti-aging serum with vitamin C", new BigDecimal("5999.00"), 40);
                skincare.setCategory(beauty);
                skincare.setImageUrl("https://images.unsplash.com/photo-1556228720-195a672e8a03?w=400&h=300&fit=crop&crop=center");
                productRepository.save(skincare);

                Product perfume = new Product("Luxury Perfume", "Long-lasting floral fragrance for women", new BigDecimal("8999.00"), 25);
                perfume.setCategory(beauty);
                perfume.setImageUrl("https://images.unsplash.com/photo-1541643600914-78b084683601?w=400&h=300&fit=crop&crop=center");
                productRepository.save(perfume);

                Product shampoo = new Product("Organic Shampoo", "Natural shampoo for all hair types", new BigDecimal("2999.00"), 60);
                shampoo.setCategory(beauty);
                shampoo.setImageUrl("https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=400&h=300&fit=crop&crop=center");
                productRepository.save(shampoo);

                Product makeup = new Product("Makeup Kit", "Complete makeup kit with brushes and cosmetics", new BigDecimal("12999.00"), 30);
                makeup.setCategory(beauty);
                makeup.setImageUrl("https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?w=400&h=300&fit=crop&crop=center");
                productRepository.save(makeup);
            }

            // Automotive
            if (automotive != null) {
                Product carCharger = new Product("Car Phone Charger", "Fast charging car adapter with dual USB ports", new BigDecimal("1999.00"), 80);
                carCharger.setCategory(automotive);
                carCharger.setImageUrl("https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=400&h=300&fit=crop&crop=center");
                productRepository.save(carCharger);

                Product carCover = new Product("Waterproof Car Cover", "All-weather protection car cover", new BigDecimal("5999.00"), 20);
                carCover.setCategory(automotive);
                carCover.setImageUrl("https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?w=400&h=300&fit=crop&crop=center");
                productRepository.save(carCover);

                Product dashCam = new Product("Dashboard Camera", "HD dashboard camera with night vision", new BigDecimal("9999.00"), 15);
                dashCam.setCategory(automotive);
                dashCam.setImageUrl("https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=400&h=300&fit=crop&crop=center");
                productRepository.save(dashCam);

                Product airFreshener = new Product("Car Air Freshener", "Premium vanilla scented car air freshener", new BigDecimal("899.00"), 100);
                airFreshener.setCategory(automotive);
                airFreshener.setImageUrl("https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=400&h=300&fit=crop&crop=center");
                productRepository.save(airFreshener);
            }

            // Toys & Games
            if (toys != null) {
                Product boardGame = new Product("Strategy Board Game", "Engaging strategy game for 2-4 players", new BigDecimal("3999.00"), 35);
                boardGame.setCategory(toys);
                boardGame.setImageUrl("https://images.unsplash.com/photo-1606092195730-5d7b9af1efc5?w=400&h=300&fit=crop&crop=center");
                productRepository.save(boardGame);

                Product puzzle = new Product("1000-Piece Puzzle", "Beautiful landscape jigsaw puzzle", new BigDecimal("2499.00"), 45);
                puzzle.setCategory(toys);
                puzzle.setImageUrl("https://images.unsplash.com/photo-1606092195730-5d7b9af1efc5?w=400&h=300&fit=crop&crop=center");
                productRepository.save(puzzle);

                Product toyRobot = new Product("Remote Control Robot", "Interactive robot toy with voice commands", new BigDecimal("7999.00"), 25);
                toyRobot.setCategory(toys);
                toyRobot.setImageUrl("https://images.unsplash.com/photo-1485827404703-89b55fcc595e?w=400&h=300&fit=crop&crop=center");
                productRepository.save(toyRobot);

                Product artSet = new Product("Art & Craft Set", "Complete art set with paints, brushes and paper", new BigDecimal("4999.00"), 40);
                artSet.setCategory(toys);
                artSet.setImageUrl("https://images.unsplash.com/photo-1513475382585-d06e58bcb0e0?w=400&h=300&fit=crop&crop=center");
                productRepository.save(artSet);

                Product drone = new Product("Mini Drone", "Beginner-friendly mini drone with camera", new BigDecimal("14999.00"), 12);
                drone.setCategory(toys);
                drone.setImageUrl("https://images.unsplash.com/photo-1473968512647-3e447244af8f?w=400&h=300&fit=crop&crop=center");
                productRepository.save(drone);
            }
        }
    }
}
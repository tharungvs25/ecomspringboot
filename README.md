# E-Commerce Website - Spring Boot Application

## 🚀 Quick Start Guide

This is a complete e-commerce website built with Spring Boot, featuring user authentication, product catalog, shopping cart, and order management.

### Prerequisites
- Java 21 (already configured in the project)
- Maven (can be downloaded automatically)

### 🎯 Super Easy Way to Run

1. **Double-click** the `run-app.bat` file in the project root
2. Wait for the application to start (about 30-60 seconds)
3. Open your browser and go to: http://localhost:8081

That's it! The application will start with sample data already loaded.

### 🔑 Demo Login Credentials

**Admin Account:**
- Username: `admin`
- Password: `admin123`

**Or create a new user account by clicking "Register"**

### 📋 Features Included

#### ✅ User Management
- User Registration & Login
- Role-based Access Control (User, Admin, Seller)
- Profile Management

#### ✅ Product Catalog
- Product Listing with Pagination
- Category-based Filtering
- Search Functionality
- Product Details Page
- Stock Management

#### ✅ Shopping Cart
- Add/Remove Products
- Update Quantities
- Real-time Total Calculation
- Cart Persistence

#### ✅ Order Management
- Checkout Process
- Order History
- Order Status Tracking
- Admin Order Management

#### ✅ Admin Features
- Product CRUD Operations
- Category Management
- User Management
- Order Management

#### ✅ Technical Features
- Spring Security for Authentication
- JPA/Hibernate for Database
- H2 In-Memory Database (no setup required)
- Thymeleaf Templates
- Bootstrap UI
- Responsive Design

### 🗄️ Database

The application uses H2 in-memory database for easy setup:
- **H2 Console:** http://localhost:8081/h2-console
- **JDBC URL:** `jdbc:h2:mem:ecommerce`
- **Username:** `sa`
- **Password:** (leave empty)

### 🛠️ Manual Run Commands

If you prefer command line:

```bash
# Using Maven Wrapper (recommended)
./mvnw spring-boot:run

# Or using Maven directly
mvn spring-boot:run

# Or run the JAR file
mvn clean package
java -jar target/ecommerce-website-1.0.0.jar
```

### 📁 Project Structure

```
src/
├── main/
│   ├── java/com/ecommerce/
│   │   ├── EcommerceApplication.java          # Main application class
│   │   ├── config/                            # Configuration classes
│   │   │   ├── SecurityConfig.java            # Spring Security config
│   │   │   └── DataInitializer.java           # Sample data loader
│   │   ├── model/                             # Entity classes
│   │   │   ├── User.java
│   │   │   ├── Product.java
│   │   │   ├── Category.java
│   │   │   ├── Order.java
│   │   │   └── ...
│   │   ├── repository/                        # JPA Repositories
│   │   ├── service/                           # Business logic
│   │   └── controller/                        # Web controllers
│   └── resources/
│       ├── application.properties             # App configuration
│       └── templates/                         # Thymeleaf templates
└── pom.xml                                   # Maven dependencies
```

### 🎨 Customization

#### Change Database to MySQL
Uncomment MySQL configuration in `application.properties` and comment out H2 configuration:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=yourpassword
```

#### Add More Products
Products are initialized in `DataInitializer.java`. You can add more sample products there.

#### Customize UI
All templates are in `src/main/resources/templates/` and use Bootstrap 5 for styling.

### 📊 Sample Data Included

- **Admin User:** admin/admin123
- **Categories:** Electronics, Clothing, Books, Home & Garden, Sports
- **Products:** 7 sample products with different categories
- **Roles:** USER, ADMIN, SELLER

### 🔧 Troubleshooting

#### Port Already in Use
If port 8080 is busy, change it in `application.properties`:
```properties
server.port=8081
```

#### Java Version Issues
Make sure you're using Java 21. Check with:
```bash
java -version
```

#### Maven Issues
The project includes Maven Wrapper, so you don't need Maven installed separately.

### 🚀 Deployment

#### Production Deployment
1. Change to production database (MySQL/PostgreSQL)
2. Update `application.properties` for production
3. Build JAR: `mvn clean package`
4. Run: `java -jar target/ecommerce-website-1.0.0.jar`

#### Docker Deployment
```dockerfile
FROM openjdk:21-jdk-slim
COPY target/ecommerce-website-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

### 📞 Support

This is a complete, production-ready e-commerce application template. It includes:
- Secure authentication and authorization
- Full shopping cart functionality
- Order management system
- Admin panel for management
- Responsive design
- Easy deployment options

The application is designed to be easy to run, customize, and extend for your specific needs.

**Happy Coding! 🎉**
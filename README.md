# PahanaEduBilling

A comprehensive **Bookshop Management System** built with Java Servlets, JSP, and MySQL. This web application provides a complete solution for managing books, customers, sales, and billing operations for educational bookshops.

## 🚀 Features

### 📚 Book Management
- **Add/Edit Books**: Manage book inventory with details like title, author, price, and stock
- **Book Listing**: View all books with search and filter capabilities
- **Stock Management**: Automatic stock updates during sales
- **Book Images**: Support for book cover images

### 👥 Customer Management
- **Customer Registration**: Add new customers with account numbers
- **Customer Directory**: View and manage customer information
- **Customer Search**: Find customers by name or account number

### 🛒 Shopping Cart & Billing
- **Shopping Cart**: Add books to cart with quantity management
- **Checkout Process**: Complete billing workflow with customer selection
- **Invoice Generation**: Automatic invoice creation with unique IDs
- **Billing History**: View and reprint past invoices
- **Payment Tracking**: Track payment status for all transactions

### 📊 Dashboard & Analytics
- **Dashboard Overview**: Real-time statistics on books and customers
- **Sales Reports**: Comprehensive billing history and reports
- **System Monitoring**: Track inventory and sales performance

### 🔐 Security & Authentication
- **User Authentication**: Secure login system
- **Session Management**: Protected routes and session handling
- **Access Control**: Role-based access to different modules

## 🛠️ Technology Stack

- **Backend**: Java 17, Jakarta EE (Servlets 6.0, JSP 3.1)
- **Frontend**: HTML5, CSS3, Bootstrap 5.3, JavaScript
- **Database**: MySQL 8.0
- **Build Tool**: Maven 3.x
- **Testing**: JUnit 5
- **Server**: Apache Tomcat 10+ (Jakarta EE compatible)

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **Java Development Kit (JDK) 17** or higher
- **Apache Maven 3.6+**
- **MySQL 8.0+**
- **Apache Tomcat 10+** (for deployment)
- **Git** (for cloning the repository)

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd PahanaEduBilling
```

### 2. Database Setup
1. **Create MySQL Database**:
   ```sql
   CREATE DATABASE pahana_edu;
   USE pahana_edu;
   ```

2. **Create Required Tables** (you'll need to create these based on your DTOs):
   ```sql
   -- Books table
   CREATE TABLE books (
       id INT PRIMARY KEY AUTO_INCREMENT,
       title VARCHAR(255) NOT NULL,
       author VARCHAR(255),
       price DECIMAL(10,2),
       stock INT DEFAULT 0,
       image_url VARCHAR(500)
   );

   -- Customers table
   CREATE TABLE customers (
       id INT PRIMARY KEY AUTO_INCREMENT,
       account_number VARCHAR(50) UNIQUE NOT NULL,
       name VARCHAR(255) NOT NULL,
       email VARCHAR(255),
       phone VARCHAR(20),
       address TEXT
   );

   -- Invoices table
   CREATE TABLE invoices (
       invoice_id VARCHAR(100) PRIMARY KEY,
       customer_id VARCHAR(50),
       customer_name VARCHAR(255),
       invoice_date DATETIME,
       total_amount DECIMAL(10,2),
       payment_status VARCHAR(20)
   );

   -- Invoice items table
   CREATE TABLE invoice_items (
       id INT PRIMARY KEY AUTO_INCREMENT,
       invoice_id VARCHAR(100),
       book_id INT,
       book_title VARCHAR(255),
       quantity INT,
       unit_price DECIMAL(10,2),
       total_price DECIMAL(10,2)
   );
   ```

### 3. Configure Database Connection
Update the database connection settings in `src/main/java/com/pahana/dao/DBConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/pahana_edu?useSSL=false&serverTimezone=UTC";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

### 4. Build the Project
```bash
mvn clean compile
```

### 5. Deploy to Tomcat
1. **Build WAR file**:
   ```bash
   mvn clean package
   ```

2. **Deploy to Tomcat**:
   - Copy `target/PahanaEduBillingSystem.war` to `$TOMCAT_HOME/webapps/`
   - Start Tomcat server
   - Access the application at `http://localhost:8080/PahanaEduBillingSystem`

### 6. Run with Maven (Development)
```bash
mvn tomcat7:run
```
Access the application at `http://localhost:8080`

## 🔑 Default Login Credentials

- **Username**: `staff`
- **Password**: `staff123`

## 📖 Usage Guide

### 1. **Login**
- Access the application and login with the provided credentials
- The system will redirect to the dashboard upon successful authentication

### 2. **Dashboard**
- View system statistics (total books, customers)
- Navigate to different modules using the navigation menu

### 3. **Book Management**
- **Add Books**: Navigate to "Add Book" to add new books to inventory
- **View Books**: Browse all books in the system
- **Edit Books**: Modify book details and stock levels

### 4. **Customer Management**
- **Add Customers**: Register new customers with account numbers
- **View Customers**: Browse customer directory
- **Edit Customers**: Update customer information

### 5. **Sales Process**
1. **Browse Books**: Select books from the book list
2. **Add to Cart**: Add desired books with quantities
3. **View Cart**: Review cart contents and totals
4. **Checkout**: Select customer and complete the sale
5. **Generate Invoice**: System automatically creates and displays invoice

### 6. **Billing History**
- View all past transactions
- Reprint invoices as needed
- Track payment status

## 🏗️ Project Structure

```
PahanaEduBilling/
├── src/
│   ├── main/
│   │   ├── java/com/pahana/
│   │   │   ├── controller/          # Servlet controllers
│   │   │   ├── dao/                 # Data Access Objects
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── mapper/              # Object mappers
│   │   │   ├── model/               # Domain models
│   │   │   └── service/             # Business logic services
│   │   ├── resources/
│   │   │   └── META-INF/            # Configuration files
│   │   └── webapp/                  # Web resources
│   │       ├── WEB-INF/             # Web configuration
│   │       └── *.jsp                # JSP pages
│   └── test/                        # Unit tests
├── target/                          # Build output
├── pom.xml                          # Maven configuration
└── README.md                        # This file
```

## 🧪 Testing

Run the test suite using Maven:
```bash
mvn test
```

The project includes unit tests for:
- **DAO Layer**: Database operations testing
- **Service Layer**: Business logic testing
- **Cart Operations**: Shopping cart functionality

## 🔧 Configuration

### Database Configuration
- Update `DBConnection.java` with your MySQL credentials
- Ensure MySQL server is running on port 3306
- Create the required database and tables

### Application Configuration
- **Session Timeout**: Configured in `web.xml`
- **File Upload**: Configured for book images
- **Error Handling**: Custom error pages and logging

## 🚨 Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Verify MySQL is running
   - Check database credentials in `DBConnection.java`
   - Ensure database and tables exist

2. **Build Failures**
   - Ensure Java 17+ is installed
   - Check Maven installation
   - Verify all dependencies in `pom.xml`

3. **Deployment Issues**
   - Use Tomcat 10+ for Jakarta EE compatibility
   - Check WAR file deployment
   - Verify application context path

4. **Login Issues**
   - Use default credentials: `staff` / `staff123`
   - Check session configuration
   - Verify servlet mappings in `web.xml`



## 📞 Support

For support and questions:
- Create an issue in the repository
- Check the troubleshooting section above

---

**PahanaEduBilling** - Streamlining educational bookshop operations with modern web technology.
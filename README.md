# Jewelry Management System API

A RESTful API for managing jewelry orders, workers, cities, and payments. This system is designed for jewelry businesses that operate across multiple cities with different workers handling orders.

## 📋 Table of Contents

- [Project Status](#-project-status)
- [Architecture](#️-architecture)
- [Technologies Used](#️-technologies-used)
- [Project Structure](#-project-structure)
- [Implemented Features](#-implemented-features)
- [Database Design](#️-database-design)
- [API Endpoints](#-api-endpoints)
- [Setup and Installation](#-setup-and-installation)
- [Development Roadmap](#-development-roadmap)

## 🚧 Project Status

**Current Status**: Core Functionality Implemented

- ✅ Admin Module: Completed
- ✅ City Module: Completed
- ✅ Worker Module: Completed
- ✅ Order Module: Completed
- ✅ Payment Module: Completed
- ✅ Order Status History: Completed

The project has reached a significant milestone with all core modules implemented. The system now supports end-to-end management of jewelry orders from creation to completion, including payment processing and status tracking.

## 🏗️ Architecture

The Jewelry Management System follows a layered architecture with clean separation of concerns:

### Architectural Patterns

1. **Layered Architecture**
   - **Controller Layer**: Handles HTTP requests and responses
   - **Service Layer**: Contains business logic
   - **Repository Layer**: Manages data access
   - **Entity Layer**: Represents database tables

2. **Repository Pattern**
   - Abstracts data access logic
   - Provides a collection-like interface for domain objects

3. **DTO Pattern (Data Transfer Objects)**
   - Separates API contracts from domain models
   - Prevents over-exposure of domain entities

4. **Centralized Exception Handling**
   - Global exception handler using `@ControllerAdvice`
   - Consistent error responses across the API

5. **AOP (Aspect-Oriented Programming)**
   - Cross-cutting concerns like logging and validation
   - Separation of business logic from technical concerns

## 🛠️ Technologies Used

- **Java 17**: Core programming language
- **Spring Boot 3.x**: Application framework
- **Spring Data JPA**: Data access abstraction
- **Hibernate**: ORM implementation
- **MySQL**: Relational database
- **Lombok**: Reduces boilerplate code
- **Jakarta Validation**: Input validation
- **Maven**: Dependency management and build tool
- **Multipart File Upload**: For worker photo uploads

## 📁 Project Structure

Current project structure (all components implemented):

```
src/main/java/com/jewelry/management/
├── JewelryManagementApplication.java
├── config/
│   └── WebConfig.java
├── controller/
│   ├── AdminController.java
│   ├── CityController.java
│   ├── WorkerController.java
│   ├── OrderController.java
│   ├── PaymentController.java
│   └── OrderStatusHistoryController.java
├── dto/
│   ├── request/
│   │   ├── AdminRequest.java
│   │   ├── CityRequest.java
│   │   ├── WorkerRequest.java
│   │   ├── OrderRequest.java
│   │   ├── OrderStatusUpdateRequest.java
│   │   └── PaymentRequest.java
│   └── response/
│       ├── AdminResponse.java
│       ├── CityResponse.java
│       ├── WorkerResponse.java
│       ├── OrderResponse.java
│       ├── PaymentResponse.java
│       └── OrderStatusHistoryResponse.java
├── entity/
│   ├── Admin.java
│   ├── City.java
│   ├── Worker.java
│   ├── Order.java
│   ├── OrderStatusHistory.java
│   └── Payment.java
├── exception/
│   ├── ApiError.java
│   ├── BadRequestException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── repository/
│   ├── AdminRepository.java
│   ├── CityRepository.java
│   ├── WorkerRepository.java
│   ├── OrderRepository.java
│   ├── OrderStatusHistoryRepository.java
│   └── PaymentRepository.java
├── service/
│   ├── impl/
│   │   ├── AdminServiceImpl.java
│   │   ├── CityServiceImpl.java
│   │   ├── WorkerServiceImpl.java
│   │   ├── OrderServiceImpl.java
│   │   ├── OrderStatusHistoryServiceImpl.java
│   │   ├── PaymentServiceImpl.java
│   │   └── FileStorageServiceImpl.java
│   └── interfaces/
│       ├── AdminService.java
│       ├── CityService.java
│       ├── WorkerService.java
│       ├── OrderService.java
│       ├── OrderStatusHistoryService.java
│       ├── PaymentService.java
│       └── FileStorageService.java
└── util/
├── OrderStatus.java
└── PaymentStatus.java

```



## ✨ Implemented Features

### Admin Management
- Create, update, and delete admin users
- View admin details by ID or email
- List all admin users
- Input validation for admin data
- Error handling for duplicate emails and invalid requests

### City Management
- Create, update, and delete cities
- View city details by ID
- List all cities
- Input validation for city data

### Worker Management
- Create workers with or without photo uploads
- Update and delete worker information
- View worker details by ID
- List all workers
- Filter workers by city
- Photo storage on file system with configurable path

### Order Management
- Create new orders with origin and destination cities
- Assign workers to orders
- Track order status through its lifecycle
- View order details by ID or order number
- List all orders
- Filter orders by status, city, or worker
- Update order status with comments and tracking

### Payment Management
- Create payments for orders
- Track payment status (pending, collected, received)
- View payment details by ID
- List all payments
- Filter payments by order or status
- Update payment status

### Order Status History
- Automatic tracking of order status changes
- Record who updated the status and when
- Add comments to status changes
- View complete status history for an order

## 🗄️ Database Design

### Current Database Schema

The complete system uses a relational database with the following tables:

**admins**
- `id` (Long, PK): Unique identifier
- `name` (String): Admin's name
- `email` (String, unique): Admin's email address
- `password` (String): Admin's password
- `mobile_number` (String): Admin's mobile number

**cities**
- `id` (Long, PK): Unique identifier
- `name` (String): City name
- `state` (String): State/province
- `country` (String): Country

**workers**
- `id` (Long, PK): Unique identifier
- `name` (String): Worker's name
- `email` (String): Worker's email address
- `mobile_number` (String): Worker's mobile number
- `address` (String): Worker's address
- `photo_path` (String): Path to worker's photo on file system
- `city_id` (Long, FK): Reference to cities table

**orders**
- `id` (Long, PK): Unique identifier
- `order_number` (String, unique): Unique order reference number
- `description` (String): Order description
- `amount` (Double): Order amount
- `created_date` (Date): Order creation date
- `status` (String): Current order status
- `from_city_id` (Long, FK): Origin city reference
- `to_city_id` (Long, FK): Destination city reference
- `assigned_worker_id` (Long, FK): Assigned worker reference

**payments**
- `id` (Long, PK): Unique identifier
- `order_id` (Long, FK): Reference to orders table
- `amount` (Double): Payment amount
- `status` (String): Payment status
- `payment_date` (Date): Date of payment
- `payment_method` (String): Method of payment

**order_status_history**
- `id` (Long, PK): Unique identifier
- `order_id` (Long, FK): Reference to orders table
- `status` (String): Status value
- `status_date` (Date): Date of status change
- `comments` (String): Comments about the status change
- `updated_by_id` (Long, FK): Reference to admins table

## 🔌 API Endpoints

### Admin API
- `POST /api/admins` - Create a new admin
- `GET /api/admins` - Get all admins
- `GET /api/admins/{id}` - Get admin by ID
- `GET /api/admins/email/{email}` - Get admin by email
- `PUT /api/admins/{id}` - Update admin
- `DELETE /api/admins/{id}` - Delete admin

### City API
- `POST /api/cities` - Create a new city
- `GET /api/cities` - Get all cities
- `GET /api/cities/{id}` - Get city by ID
- `PUT /api/cities/{id}` - Update city
- `DELETE /api/cities/{id}` - Delete city

### Worker API
- `POST /api/workers/create` - Create a worker with photo
- `POST /api/workers/create-without-photo` - Create a worker without photo
- `GET /api/workers/{id}` - Get worker by ID
- `GET /api/workers/get-all-work` - Get all workers
- `GET /api/workers/city/{cityId}` - Get workers by city
- `PUT /api/workers/{id}` - Update worker
- `DELETE /api/workers/{id}` - Delete worker

### Order API
- `POST /api/orders/create` - Create a new order
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/number/{orderNumber}` - Get order by order number
- `GET /api/orders/all` - Get all orders
- `GET /api/orders/status/{status}` - Get orders by status
- `GET /api/orders/from-city/{cityId}` - Get orders by origin city
- `GET /api/orders/to-city/{cityId}` - Get orders by destination city
- `GET /api/orders/worker/{workerId}` - Get orders by assigned worker
- `PUT /api/orders/update-status` - Update order status
- `DELETE /api/orders/{id}` - Delete order

### Payment API
- `POST /api/payments/create` - Create a new payment
- `GET /api/payments/{id}` - Get payment by ID
- `GET /api/payments/all` - Get all payments
- `GET /api/payments/order/{orderId}` - Get payments by order
- `GET /api/payments/status/{status}` - Get payments by status
- `PUT /api/payments/{id}/status/{status}` - Update payment status
- `DELETE /api/payments/{id}` - Delete payment

### Order Status History API
- `GET /api/order-status-history/order/{orderId}` - Get status history by order
- `GET /api/order-status-history/{id}` - Get status history entry by ID

## 🚀 Setup and Installation

### Prerequisites
- Java 17 or higher
- Maven
- MySQL

### Database Setup
1. Install MySQL if not already installed
2. Create a database named `jewelry_management`
3. Update the database configuration in `application.properties` with your MySQL username and password

### Configuration
Update `application.properties` with the following settings:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/jewelry_management?createDatabaseIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# File Upload Configuration
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
file.upload-dir=./uploads/worker-photos

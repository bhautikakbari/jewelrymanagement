# Jewelry Management System API

A comprehensive RESTful API for managing jewelry orders, workers, cities, and payments. This system is designed for jewelry businesses that operate across multiple cities with different workers handling orders.

## 📋 Table of Contents

- [Architecture](#architecture)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Key Features](#key-features)
- [Database Design](#database-design)
- [API Endpoints](#api-endpoints)
- [Setup and Installation](#setup-and-installation)
- [Future Enhancements](#future-enhancements)

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
   - Allows for versioning and evolution of APIs independently from domain models

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

## 📁 Project Structure

```
src/main/java/com/jewelry/management/
├── JewelryManagementApplication.java
├── config/
├── controller/
│   ├── AdminController.java
│   ├── CityController.java
│   ├── WorkerController.java
│   ├── OrderController.java
│   └── PaymentController.java
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
│       ├── OrderStatusHistoryResponse.java
│       └── PaymentResponse.java
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
│   │   └── PaymentServiceImpl.java
│   └── interfaces/
│       ├── AdminService.java
│       ├── CityService.java
│       ├── WorkerService.java
│       ├── OrderService.java
│       └── PaymentService.java
└── util/
    ├── OrderStatus.java
    └── PaymentStatus.java
```

## ✨ Key Features

1. **Admin Management**
   - Create, update, and delete admin users
   - View admin details

2. **City Management**
   - Add, update, and remove cities
   - List all cities

3. **Worker Management**
   - Register workers with their details (name, contact, photo)
   - Assign workers to specific cities
   - Update worker information

4. **Order Management**
   - Create orders with source and destination cities
   - Assign orders to workers
   - Track order status (created, dispatched, in transit, received, sold, etc.)
   - Maintain order history

5. **Payment Tracking**
   - Record payments for orders
   - Track payment status (pending, collected by worker, received by admin)

6. **Reporting**
   - View orders by status
   - View orders by city
   - View orders assigned to specific workers
   - Track pending payments

## 🗄️ Database Design

The system uses a relational database with the following key tables:

1. **admins**: Stores admin user information
2. **cities**: Stores city information
3. **workers**: Stores worker details with references to cities
4. **orders**: Stores order information with references to cities and workers
5. **payments**: Stores payment information with references to orders
6. **order_status_history**: Tracks the history of order status changes

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
- `GET /api/cities/name/{name}` - Get city by name
- `PUT /api/cities/{id}` - Update city
- `DELETE /api/cities/{id}` - Delete city

### Worker API
- `POST /api/workers` - Create a new worker
- `GET /api/workers` - Get all workers
- `GET /api/workers/{id}` - Get worker by ID
- `GET /api/workers/city/{cityId}` - Get workers by city
- `PUT /api/workers/{id}` - Update worker
- `DELETE /api/workers/{id}` - Delete worker

### Order API
- `POST /api/orders` - Create a new order
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/number/{orderNumber}` - Get order by order number
- `GET /api/orders/status/{status}` - Get orders by status
- `GET /api/orders/from-city/{cityId}` - Get orders by source city
- `GET /api/orders/to-city/{cityId}` - Get orders by destination city
- `GET /api/orders/worker/{workerId}` - Get orders by assigned worker
- `PUT /api/orders/status` - Update order status
- `DELETE /api/orders/{id}` - Delete order

### Payment API
- `POST /api/payments` - Create a new payment
- `GET /api/payments` - Get all payments
- `GET /api/payments/{id}` - Get payment by ID
- `GET /api/payments/order/{orderId}` - Get payments by order
- `GET /api/payments/status/{status}` - Get payments by status
- `PUT /api/payments/{id}/status/{status}` - Update payment status
- `DELETE /api/payments/{id}` - Delete payment

## 🚀 Setup and Installation

### Prerequisites
- Java 17 or higher
- Maven
- MySQL

### Database Setup
1. Install MySQL if not already installed
2. Create a database named `jewelry_management`
3. Update the database configuration in `application.properties` with your MySQL username and password

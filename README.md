# Jewelry Management System API

A RESTful API for managing jewelry orders, workers, cities, and payments. This system is designed for jewelry businesses that operate across multiple cities with different workers handling orders.

## 📋 Table of Contents

- [Project Status](#project-status)
- [Architecture](#architecture)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Implemented Features](#implemented-features)
- [Database Design](#database-design)
- [API Endpoints](#api-endpoints)
- [Setup and Installation](#setup-and-installation)
- [Development Roadmap](#development-roadmap)

## 🚧 Project Status

**Current Status**: Initial Development

- ✅ Admin Module: Completed
- 🔄 City Module: Planned
- 🔄 Worker Module: Planned
- 🔄 Order Module: Planned
- 🔄 Payment Module: Planned

This project is in active development. Currently, only the Admin module has been fully implemented. The README outlines both the implemented features and the planned architecture for the complete system.

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

## 📁 Project Structure

Current project structure (implemented components):

```
src/main/java/com/jewelry/management/
├── JewelryManagementApplication.java
├── controller/
│   └── AdminController.java
├── dto/
│   ├── request/
│   │   └── AdminRequest.java
│   └── response/
│       └── AdminResponse.java
├── entity/
│   └── Admin.java
├── exception/
│   ├── ApiError.java
│   ├── BadRequestException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── repository/
│   └── AdminRepository.java
└── service/
    ├── impl/
    │   └── AdminServiceImpl.java
    └── interfaces/
        └── AdminService.java
```

Planned full project structure:

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
│   └── response/
├── entity/
│   ├── Admin.java
│   ├── City.java
│   ├── Worker.java
│   ├── Order.java
│   ├── OrderStatusHistory.java
│   └── Payment.java
├── exception/
├── repository/
├── service/
│   ├── impl/
│   └── interfaces/
└── util/
```

## ✨ Implemented Features

### Admin Management
- Create, update, and delete admin users
- View admin details by ID or email
- List all admin users
- Input validation for admin data
- Error handling for duplicate emails and invalid requests

## 🗄️ Database Design

### Current Database Schema

Currently, only the `admins` table has been implemented:

**admins**
- `id` (Long, PK): Unique identifier
- `name` (String): Admin's name
- `email` (String, unique): Admin's email address
- `password` (String): Admin's password
- `mobile_number` (String): Admin's mobile number

### Planned Database Schema

The complete system will use a relational database with the following key tables:

1. **admins**: Stores admin user information
2. **cities**: Stores city information
3. **workers**: Stores worker details with references to cities
4. **orders**: Stores order information with references to cities and workers
5. **payments**: Stores payment information with references to orders
6. **order_status_history**: Tracks the history of order status changes

## 🔌 API Endpoints

### Implemented Endpoints

#### Admin API
- `POST /api/admins` - Create a new admin
- `GET /api/admins` - Get all admins
- `GET /api/admins/{id}` - Get admin by ID
- `GET /api/admins/email/{email}` - Get admin by email
- `PUT /api/admins/{id}` - Update admin
- `DELETE /api/admins/{id}` - Delete admin

### Planned Endpoints

The system will eventually include endpoints for managing cities, workers, orders, and payments.

## 🚀 Setup and Installation

### Prerequisites
- Java 17 or higher
- Maven
- MySQL

### Database Setup
1. Install MySQL if not already installed
2. Create a database named `jewelry_management`
3. Update the database configuration in `application.properties` with your MySQL username and password


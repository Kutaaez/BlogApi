<p align="center">
      <img width=""1000" height="400" alt="Thread banner" src="https://github.com/user-attachments/assets/72dad953-39b2-4aa9-b41f-265413afb443" />
      
</p>

---

# BlogApi (postmanBlog)

**A robust Spring Boot REST API for a modern blogging platform with Role-Based Access Control (RBAC).**

---

## Description

BlogApi is a comprehensive backend solution for a blogging ecosystem. It allows users to engage in discussions through "Threads," categorize content, and interact via reviews and likes. The project is built with a focus on security, data integrity, and clean architecture, strictly following Software Engineering best practices.

## Features

* Advanced Thread Management: Create, read, update, and delete (CRUD) blog posts with multi-category support.
* Social Engagement: Interactive system for leaving reviews (comments) and likes on threads.
* Role-Based Security (RBAC): Three distinct roles: USER, MODERATOR, and ADMIN with specific permissions.
* Admin Dashboard Logic: Administrative tools for user moderation, including banning/unbanning and role management.
* Automated Data Mapping: Uses MapStruct to ensure a strict separation between Database Entities and API DTOs.
* Database Versioning: Full schema control using Liquibase migrations.

## Tech Stack

* Framework: Spring Boot 4.0.1
* Security: Spring Security (Session-based RBAC)
* Database: PostgreSQL
* ORM: Spring Data JPA / Hibernate
* Migration Tool: Liquibase
* Mapping: MapStruct
* Testing: JUnit 5 & Mockito
* Build Tool: Gradle

## Architecture

The project follows a Layered Architecture to ensure maintainability:

* Controllers: Handle REST endpoints and DTO validation.
* Services: Encapsulate core business logic and security checks.
* Repositories: Manage database communication via JPA.
* DTOs: Mandatory Data Transfer Objects to prevent direct Entity exposure.

---

## Getting Started

### Prerequisites

* Java 17 or higher
* PostgreSQL instance running

### Configuration

1. Clone the repository.
2. Update src/main/resources/application.properties with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/threads
spring.datasource.username=your_username
spring.datasource.password=your_password

```

### Running the Application

Build and run the project using Gradle:

```bash
./gradlew bootRun

```

Liquibase will automatically create the schema and seed the database with roles and a default admin.

**Default Admin Credentials:**

* Email: admin@gmail.com
* Password: password123

---

## Testing and API

### Unit Tests

Run the test suite to verify service logic:

```bash
./gradlew test

```

### Structure

```text
src/main/java/kz/seppaku/postmanBlog/
├── config/
│   └── SecurityConfig.java
├── controllers/
│   ├── AdminController.java
│   ├── AuthController.java
│   ├── CategoryController.java
│   ├── LikeController.java
│   ├── ReviewController.java
│   ├── ThreadController.java
│   └── UserController.java
├── dto/
│   ├── request/
│   │   ├── CategoryCreateDto.java
│   │   ├── LikeCreateDto.java
│   │   ├── ReviewCreateDto.java
│   │   ├── ThreadCreateDto.java
│   │   └── UserCreateDto.java
│   └── response/
│       ├── CategoryDto.java
│       ├── LikeDto.java
│       ├── ReviewDto.java
│       ├── ThreadDto.java
│       └── UserDto.java
├── entities/
│   ├── Category.java
│   ├── Like.java
│   ├── Review.java
│   ├── Role.java
│   ├── Thread.java
│   └── User.java
├── mapper/
│   ├── CategoryMapper.java
│   ├── LikeMapper.java
│   ├── ReviewMapper.java
│   ├── ThreadMapper.java
│   └── UserMapper.java
├── repositories/
│   ├── CategoryRepository.java
│   ├── LikeRepository.java
│   ├── ReviewRepository.java
│   ├── RoleRepository.java
│   ├── ThreadRepository.java
│   └── UserRepository.java
├── services/
│   ├── CategoryService.java
│   ├── LikeService.java
│   ├── ReviewService.java
│   ├── ThreadService.java
│   ├── UserService.java
│   └── impl/
│       ├── CategoryServiceImpl.java
│       ├── LikeServiceImpl.java
│       ├── ReviewServiceImpl.java
│       ├── ThreadServiceImpl.java
│       └── UserServiceImpl.java
└── PostmanBlogApplication.java

src/main/resources/
├── db/changelog/
│   ├── changes/
│   │   ├── 2025-12-28-16-27-00-create-tables.xml
│   │   ├── 2025-12-28-16-29-00-insert-roles.xml
│   │   └── 2025-12-28-16-35-00-insert-admin.xml
│   └── db.changelog-master.xml
└── application.properties

```

### Postman Collections

API documentation and test scenarios are located in the root directory:

* thr_postman_collection.json: Main workflow (Happy Path).
* ban_change_password_logic.json: Security and Admin logic tests.
* get-requests-only-1234.json: Quick data retrieval examples.

## License

This project was developed for educational purposes as part of the Software Engineering final project requirements.

---

*Developed by Zharaskhan Mirmauly s23067305@Narxoz.kz*

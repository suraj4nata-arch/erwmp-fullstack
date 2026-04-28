Overview
----------------------------------------------------------------------------------------------------------------------------
A full-stack web application built using Spring Boot and React to manage core business workflows through secure and scalable REST APIs.The application demonstrates backend design principles, authentication mechanisms, and end-to-end system integration.

Tech Stack
----------------------------------------------------------------------------------------------------------------------------
Backend: Java, Spring Boot, Spring Security, JPA
Frontend: React.js
Database: MySQL
DevOps & Tools: Docker, GitHub Actions

Features
----------------------------------------------------------------------------------------------------------------------------
* JWT-based Authentication & Authorization
* Role-Based Access Control (RBAC)
* RESTful APIs with CRUD operations
* Layered Architecture (Controller → Service → Repository)
* Structured Exception Handling
* End-to-End integration between frontend and backend

Architecture
----------------------------------------------------------------------------------------------------------------------------
Frontend (React)
       ↓
Backend (Spring Boot)
       ↓
Database (MySQL)
Backend Design
Controller Layer → Handles API requests
Service Layer → Business logic
Repository Layer → Database interaction


 How to Run
----------------------------------------------------------------------------------------------------------------------------
* Clone the repository:
git clone https://github.com/suraj4nata-arch/erwmp-fullstack.git

 * Backend
      cd backend
      mvn clean install
      mvn spring-boot:run

* Frontend
     cd frontend
     npm install
     npm start

* Docker Setup
    The application is containerized using Docker.

* Architecture
    frontend  |  backend  |  mysql
    Run using Docker Compose
    docker-compose up --build

* CI/CD Pipeline
Automated using GitHub Actions:
      Code Push
        ↓
    Build Triggered
        ↓
      Run Tests
        ↓
Build Docker Image
        ↓
Ready for Deployment

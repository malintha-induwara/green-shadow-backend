# Green Shadow Farm Management System Backend

A comprehensive farm management system backend for Green Shadow Farm, providing robust APIs for managing farm operations, resources, and tracking agricultural activities.

## About The Project

Green Shadow Farm Management System is a specialized backend service designed to streamline and automate farm management processes. The system provides comprehensive APIs for managing various aspects of farm operations, from crop management to equipment tracking.

### Key Features

- **Authentication System**
  - User registration and login
  - JWT-based authentication
  - Refresh token mechanism for sustained sessions

- **Resource Management**
  - Crop management with image storage
  - Vehicle tracking and maintenance
  - Staff records and management
  - Equipment inventory
  - Field management with dual image support
  
- **Activity Logging**
  - Comprehensive logging system
  - Tracks field operations
  - Records crop activities
  - Monitors staff assignments
  - Maintains historical data

## Technology Stack

[![SPRING](https://img.shields.io/badge/Spring_Framework-black?style=for-the-badge&logo=spring&logoColor=green)](https://spring.io/projects/spring-framework)

[![SPRING DATA JPA](https://img.shields.io/badge/Spring_Data_JPA-black?style=for-the-badge&logo=spring&logoColor=green)](https://spring.io/projects/spring-data-jpa)

[![HIBERNATE](https://img.shields.io/badge/Hibernate-black?style=for-the-badge&logo=Hibernate&logoColor=BBAE79)](https://hibernate.org/orm/)

[![GRADLE](https://img.shields.io/badge/Gradle-black?style=for-the-badge&logo=gradle&logoColor=white)](https://gradle.org/)

[![MySQL](https://img.shields.io/badge/Mysql-black?style=for-the-badge&logo=mysql&logoColor=08668E")](https://www.mysql.com/downloads/)

[![POSTMAN](https://img.shields.io/badge/Postman-black?style=for-the-badge&logo=Postman&logoColor=FF713D")](https://www.postman.com/downloads/)

[![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=jsonwebtokens&logoColor=white)](https://jwt.io/)

## Getting Started

### Prerequisites

Ensure you have the following installed:

- Java 17 or higher
- MySQL 8.0 or higher
- Gradle
- Your preferred IDE (IntelliJ IDEA recommended)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/malintha-induwara/green-shadow-backend.git
   ```

2. Configure MySQL database:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/green_shadow_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. Build the project:
   ```bash
   gradle build
   ```

4. Run the application:
   ```bash
   gradle bootRun
   ```

## Project Documentation

### Database Design
The ER diagram for the Green Shadow Farm Management System can be found here:
[View ER Diagram](https://drive.google.com/file/d/1u9ecV1H7YKYDgXaqrsSF-DyOpzETwyA8/view?usp=s)

### Class Structure
The class diagram showing the system's architecture and relationships can be found here:
[View Class Diagram](https://drive.google.com/file/d/1u9ecV1H7YKYDgXaqrsSF-DyOpzETwyA8/view?usp=s)

## API Documentation

Detailed API documentation is available through Postman:
[View API Documentation](https://documenter.getpostman.com/view/33030562/2sAYBa8UpS)

## Frontend Repository

The frontend repository for this project can be found here:
[Green Shadow Frontend](https://github.com/malintha-induwara/green-shadow-frontend)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

# KEEPINTOUCH 📨
You can also visit app website -> (http://khaydin.chickenkiller.com)  
A simple messaging application built with **Java** and **Spring Boot** for self-study. This project demonstrates the use of **Spring Security**, **Thymeleaf**, **Spring Data JPA**, and **MySQL**.

## Features 🚀
- **User Authentication & Authorization** (Spring Security)
- **User Registration & Login**
- **Real-time Messaging**
- **Database Persistence** (MySQL with Spring Data JPA)
- **Thymeleaf-Based UI**

## Technologies Used 🛠️
- **Java 17**
- **Spring Boot 3**
- **Spring Security**
- **Spring Data JPA**
- **Thymeleaf**
- **MySQL**

## Installation & Setup 🛠🔧

1. **Clone the Repository**  
   ```bash
   git clone [https://github.com/your-username/messaging-app.git
   cd messaging-app](https://github.com/kursathurkanaydin/keepintouch.git)
   ```

2. **Configure Database**  
   Update `application.properties` with your MySQL credentials:  
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/messaging_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```

3. **Run the Application**  
   ```bash
   mvn spring-boot:run
   ```

4. **Access the Application**  
   Open your browser and visit:  
   ```
   http://localhost:8080
   ```

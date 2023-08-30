
# CardManager Application

CardManager is a Spring Boot based RESTful API that allows users to manage tasks in the form of cards. It provides user authentication, card management features, and role-based access.

## Features
- **User Authentication**: Users can log in using their email and password.
- **Role-Based Access**: Users can be `Members` or `Admins`.
    - `Members`: Can only access and manage cards they've created.
    - `Admins`: Can access and manage all cards.
- **Card Management**: Users can create, read, update, and delete cards.
    - Card information includes: Name (mandatory), Description, Color, and Status.
    - Cards can have one of the three statuses: `To Do`, `In Progress`, and `Done`.

## API Documentation
Detailed API documentation is available through Swagger UI once the application is running. Access it at:
```
http://localhost:8080/swagger-ui/
```

## Setup and Installation

### Prerequisites:
- Java 11+
- Maven
- MySQL

### Steps:

1. **Clone the Repository**
   ```bash
   git clone https://github.com/ortupik/cards
   ```

2. **Configure Database**: Update `application.properties` with your MySQL credentials.

3. **Build the Project**:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

## Running Tests
Execute the following command:
```bash
mvn test
```

## Technology Stack
- **Spring Boot**: Framework for building the RESTful API.
- **Spring Data JPA**: For data persistence.
- **MySQL**: Database for storing user and card data.
- **Spring Security & JWT**: For authentication and authorization.
- **Swagger**: API documentation.
- **JUnit & Mockito**: For unit testing.

## Contribution
Feel free to fork this repository and submit pull requests. All contributions are welcome.

## License
MIT License. See `LICENSE` for more information.

---


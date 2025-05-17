# Banking System

A Spring Boot application that provides banking functionality with user management, account operations, and automatic balance increases.

## Technologies Used

### Core Stack
- Java 11
- Spring Boot 2.7.0
- PostgreSQL
- Maven

### Additional Technologies
- **JWT (JSON Web Tokens)** - For stateless authentication
- **Flyway** - Database migration tool
- **Spring Cache** - For performance optimization
- **Swagger/OpenAPI** - API documentation
- **Testcontainers** - For integration testing with real PostgreSQL database
- **Lombok** - To reduce boilerplate code

## Project Setup

### Prerequisites
1. Java 11 or higher
2. Maven 3.6 or higher
3. PostgreSQL 12 or higher
4. Docker (for running tests with Testcontainers)

### Database Setup
1. Create a PostgreSQL database:
```sql
CREATE DATABASE banking;
```

2. Configure database connection in `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/banking
    username: your_username
    password: your_password
```

### Building and Running
1. Clone the repository
2. Navigate to the project directory
3. Build the project:
```bash
mvn clean install
```
4. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI docs: `http://localhost:8080/api-docs`

## Key Features

### Authentication
- JWT-based authentication
- Login with email/phone + password
- Token expiration: 24 hours

### User Management
- User search with multiple criteria
- Email and phone management
- Data validation
- Caching for better performance

### Account Operations
- Secure money transfers with pessimistic locking
- Automatic balance increase every 30 seconds
- Maximum balance increase limit (207% of initial balance)

## Design Decisions

### Security
1. **Pessimistic Locking**: Used for money transfers to prevent race conditions and ensure data consistency
2. **JWT with USER_ID claim**: Enables quick user identification without database queries
3. **BCrypt password encoding**: Secure password storage

### Performance Optimizations
1. **Caching Layer**:
   - User data caching with automatic invalidation on updates
   - Reduces database load for frequently accessed data

2. **Database Indexes**:
   - Optimized queries with indexes on frequently searched fields
   - Covering indexes for common search patterns

3. **Lazy Loading**:
   - Entity relationships use lazy loading to prevent unnecessary data fetching
   - Fetch joins in specific queries where full data is needed

### Data Integrity
1. **Transaction Management**:
   - ACID compliance for critical operations
   - Proper transaction boundaries with @Transactional annotations

2. **Version Control**:
   - Optimistic locking with @Version for account updates
   - Prevents concurrent modifications

3. **Database Constraints**:
   - CHECK constraints for non-negative balance
   - UNIQUE constraints for emails and phones
   - Foreign key constraints for referential integrity

## Testing
The project uses Testcontainers for integration tests, providing:
- Real PostgreSQL database for tests
- Isolated test environment
- Production-like conditions

## Error Handling
- Global exception handling
- Meaningful error messages
- Proper HTTP status codes
- Validation error details

## Monitoring and Logging
- Detailed logging for critical operations
- Transaction logging
- Balance update tracking

## Future Improvements
1. Add Redis for distributed caching
2. Implement Elasticsearch for advanced search capabilities
3. Add metrics collection and monitoring
4. Implement rate limiting for API endpoints
5. Add support for multiple currencies 
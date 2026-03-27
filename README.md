# Library Management System

A simple Library Management System built with Spring Boot and JPA for demo purposes.

## Features

- **Book Management**: Create, read, update, delete, and search books
- **Member Management**: Manage library members with different membership types
- **Loan Management**: Handle book borrowing and returns
- **Overdue Tracking**: Track and manage overdue loans
- **H2 In-Memory Database**: Easy setup with pre-loaded sample data

## Technology Stack

- Java 17
- Spring Boot 4.0.5
- Spring Data JPA
- H2 Database
- Lombok
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:

```bash
./mvnw spring-boot:run
```

Or build and run the JAR:

```bash
./mvnw clean install
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

### Accessing H2 Console

Visit: `http://localhost:8080/h2-console`

- **JDBC URL**: `jdbc:h2:mem:librarydb`
- **Username**: `sa`
- **Password**: (leave empty)

## API Documentation

The API is documented using OpenAPI 3.0 specification. See `openapi.yaml` for complete API documentation.

### Quick API Examples

#### Get all books
```bash
curl http://localhost:8080/api/books
```

#### Create a book
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Clean Code",
    "author": "Robert C. Martin",
    "isbn": "978-0132350884",
    "category": "Programming",
    "available": true,
    "totalCopies": 3,
    "availableCopies": 3
  }'
```

#### Search books by title
```bash
curl "http://localhost:8080/api/books/search/title?title=Clean"
```

#### Create a loan
```bash
curl -X POST http://localhost:8080/api/loans \
  -H "Content-Type: application/json" \
  -d '{"bookId": 1, "memberId": 1}'
```

#### Return a book
```bash
curl -X PATCH http://localhost:8080/api/loans/1/return
```

## Sample Data

The application comes with pre-loaded sample data:

### Books
- Clean Code - Robert C. Martin
- Design Patterns - Gang of Four
- The Pragmatic Programmer - Andrew Hunt
- Effective Java - Joshua Bloch
- Introduction to Algorithms - Thomas H. Cormen

### Members
- John Doe (PREMIUM)
- Jane Smith (REGULAR)
- Bob Johnson (REGULAR)
- Alice Williams (PREMIUM)

## Project Structure

```
src/main/java/com/example/demo/
├── config/
│   └── DataInitializer.java       # Sample data initialization
├── controller/
│   ├── BookController.java        # Book REST endpoints
│   ├── MemberController.java      # Member REST endpoints
│   └── LoanController.java        # Loan REST endpoints
├── entity/
│   ├── Book.java                  # Book entity
│   ├── Member.java                # Member entity
│   └── Loan.java                  # Loan entity
├── repository/
│   ├── BookRepository.java        # Book JPA repository
│   ├── MemberRepository.java      # Member JPA repository
│   └── LoanRepository.java        # Loan JPA repository
├── service/
│   ├── BookService.java           # Book business logic
│   ├── MemberService.java         # Member business logic
│   └── LoanService.java           # Loan business logic
└── DemoApplication.java           # Main application class
```

## API Endpoints

### Books
- `GET /api/books` - Get all books
- `POST /api/books` - Create a book
- `GET /api/books/{id}` - Get book by ID
- `PUT /api/books/{id}` - Update a book
- `DELETE /api/books/{id}` - Delete a book
- `GET /api/books/category/{category}` - Get books by category
- `GET /api/books/available` - Get available books
- `GET /api/books/search/title?title={term}` - Search by title
- `GET /api/books/search/author?author={term}` - Search by author

### Members
- `GET /api/members` - Get all members
- `POST /api/members` - Create a member
- `GET /api/members/{id}` - Get member by ID
- `PUT /api/members/{id}` - Update a member
- `DELETE /api/members/{id}` - Delete a member
- `GET /api/members/email/{email}` - Get member by email
- `GET /api/members/active` - Get active members
- `GET /api/members/type/{type}` - Get members by type
- `GET /api/members/search?name={term}` - Search by name
- `PATCH /api/members/{id}/activate` - Activate a member
- `PATCH /api/members/{id}/deactivate` - Deactivate a member

### Loans
- `GET /api/loans` - Get all loans
- `POST /api/loans` - Create a loan (borrow book)
- `GET /api/loans/{id}` - Get loan by ID
- `DELETE /api/loans/{id}` - Delete a loan
- `GET /api/loans/member/{memberId}` - Get loans by member
- `GET /api/loans/book/{bookId}` - Get loans by book
- `GET /api/loans/member/{memberId}/active` - Get active loans by member
- `GET /api/loans/book/{bookId}/active` - Get active loans by book
- `GET /api/loans/overdue` - Get overdue loans
- `PATCH /api/loans/{id}/return` - Return a book
- `POST /api/loans/update-overdue` - Update overdue loan status

## Business Rules

- Default loan period: 14 days
- When a book is borrowed, `availableCopies` decreases by 1
- When a book is returned, `availableCopies` increases by 1
- Membership types: REGULAR or PREMIUM
- Loan status: ACTIVE, RETURNED, or OVERDUE

## License

This project is for demonstration purposes.
# StackShop -  FullStack E-Commerce Application


## Technologies Used

- **Java** - Programming language
- **Spring Boot** - Application framework
- **Spring Data JPA** - Data persistence
- **Hibernate Validator** - Bean validation
- **Maven** - Build automation
- **Lombok** - Code generation
- **MySQL & MongoDB** - Databases
- **Docker** - Containerization
- **JUnit** - Testing frameworks
- **React** - Frontend

## Features

- Category CRUD operations
- Input validation with custom error messages
- Global exception handling
- RESTful API design
- JPA/Hibernate integration
- HTTP status code wrapping

## API Endpoints

### Category Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/public/categories` | Get all categories |
| `POST` | `/api/public/categories` | Create a new category |
| `PUT` | `/api/admin/categories/{id}` | Update existing category |
| `DELETE` | `/api/admin/categories/{id}` | Delete category |

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/Mohrip/sb-ecom-monorepo.git
cd sb-ecom-monorepo

## Branching
- **main**: Stable production-ready code
- **development**: Active development and new features
- **feature/**: New features and enhancements

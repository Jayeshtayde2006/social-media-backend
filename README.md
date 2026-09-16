# Social Media Backend

A secure Social Media Backend API developed using Java Spring Boot, Spring Security, JWT Authentication, JPA/Hibernate, and MySQL.

## Features

- User Registration
- User Login
- JWT Authentication
- Password Encryption using BCrypt
- Role-Based Access Control (USER / ADMIN)
- User CRUD Operations
- Profile Management
- Post CRUD Operations
- Like and Unlike Posts
- Comment Management
- Feed Management
- User-specific Posts
- Admin Dashboard
- Ownership-based security for Posts and Comments
- Global Exception Handling

## Technologies Used

- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Postman
- VS Code

## Project Structure

```text
social-media-backend/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/socialmedia/social_media_backend/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── exception/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       ├── service/
│   │   │       └── SocialMediaBackendApplication.java
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   │
│   └── test/
│
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md

## API Modules

# Authentication
-POST /api/users/login
-POST /api/users/logout

# Users
-POST /api/users
-GET /api/users
-GET /api/users/{id}
-PUT /api/users/{id}
-DELETE /api/users/{id}

# Profiles
-POST /api/profiles
-GET /api/profiles
-GET /api/profiles/{id}
-PUT /api/profiles/{id}
-DELETE /api/profiles/{id}

# Posts
-POST /api/posts
-GET /api/posts
-GET /api/posts/{id}
-PUT /api/posts/{id}
-DELETE /api/posts/{id}

# Likes
-POST /api/likes
-GET /api/likes
-GET /api/likes/{id}
-DELETE /api/likes/{id}

# Comments
-POST /api/comments
-GET /api/comments
-GET /api/comments/{id}
-PUT /api/comments/{id}
-DELETE /api/comments/{id}

# Feed
-GET /api/feed
-GET /api/feed/count
-GET /api/feed/user?email={email}

# Admin
-GET /api/admin/dashboard
-Security

The application uses JWT-based authentication.

Passwords are encrypted using BCrypt before storing them in the database.

Role-based access control is implemented using:
-USER
-ADMIN

Admin APIs are protected and require an ADMIN role.

Users can only update or delete their own posts and comments.

Database

Database used:

social_media_db

Make sure MySQL Server is running before starting the application.

How to Run

Clone the project and open it in VS Code.

Update the database configuration in:

src/main/resources/application.properties

Then run:

.\mvnw.cmd spring-boot:run

The application runs on:

http://localhost:8080
API Testing

All APIs were tested using Postman.

The Postman collection contains requests for:

Authentication
Users
Profiles
Posts
Likes
Comments
Feed
Admin
Project Status

The Social Media Backend API has been implemented and tested successfully.

Author
Jayesh Tayade
# Sporty Shoes Shopping Writeup

This is the writeup for the Sporty Shoes Shopping project. The project is a web application that
allows users to browse and purchase shoes. The application is built using the Spring Boot framework
and the Thymeleaf templating engine, with a Bootstrap front-end.

## How to Use

This application runs on Java 17 and Maven.

The MySQL database is required to run this application. The database configuration can be found in
the [`application.properties`](./src/main/resources/application.properties) file.

The default database configuration is as follows:

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/SportyShoes
spring.datasource.username=root
spring.datasource.password=root
```

The database should be created with the command `CREATE DATABASE IF NOT EXISTS SportyShoes;`. The
database tables will be created when the application is run.

## Project Structure

The project is structured as follows:

- The main application class
  is [`SportyShoesApplication.java`](./src/main/java/com/sportyshoes/SportyShoes/SportyShoesApplication.java).
  This is the entry point for the application.
- The entities are in the [`entity`](./src/main/java/com/sportyshoes/SportyShoes/entity) package.
  These are the objects that are stored in the database.
- The repositories are in the [`repository`](./src/main/java/com/sportyshoes/SportyShoes/repository)
  package. These are the classes that interact with the database.
- The services are in the [`service`](./src/main/java/com/sportyshoes/SportyShoes/service) package.
  These are the classes that handle the business logic of the application.
- The controllers are in the [`controller`](./src/main/java/com/sportyshoes/SportyShoes/controller)
  package. The `ShoppingController` handles the shopping pages, and the `UserController` handles the
  user pages.
- In the [`resources`](./src/main/resources) directory, the `application.properties` file contains
  the database configuration, and the `templates` directory contains the HTML templates for the
  application.

```
.
├── main
│   ├── java
│   │   └── com
│   │       └── sportyshoes
│   │           ├── SportyShoes
│   │           │   └── SportyShoesApplication.java
│   │           ├── controller
│   │           │   ├── ShoppingController.java
│   │           │   └── UserController.java
│   │           ├── entity
│   │           │   ├── Order.java
│   │           │   ├── Product.java
│   │           │   ├── ProductCategory.java
│   │           │   └── User.java
│   │           ├── repository
│   │           │   ├── OrderRepository.java
│   │           │   ├── ProductCategoryRepository.java
│   │           │   ├── ProductRepository.java
│   │           │   └── UserRepository.java
│   │           └── service
│   │               ├── OrderService.java
│   │               ├── ProductCategoryService.java
│   │               ├── ProductService.java
│   │               └── UserService.java
│   └── resources
│       ├── application.properties
│       ├── commands.sql
│       └── templates
│           ├── fragments
│           │   └── base.html
│           ├── login.html
│           ├── orders.html
│           └── shop.html
└── test
    └── java
        └── com
            └── sportyshoes
                └── SportyShoes
                    └── SportyShoesApplicationTests.java

```

### Object Entities

The application has four entities: `User`, `Product`, `ProductCategory`, and `Order`.

![./screenshots/entity_diagram.png](./screenshots/entity_diagram.png)

## Development 

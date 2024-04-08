# Sporty Shoes Shopping Writeup

This is the writeup for the Sporty Shoes Shopping project. The project is a web application that
allows users to browse and purchase shoes. The application is built using the Spring Boot framework
and the Thymeleaf templating engine, with a Bootstrap front-end.

## How to Use

All the source code can be found on GitHub
at [https://github.com/cmenon12/sporty-shoes-shopping](https://github.com/cmenon12/sporty-shoes-shopping).

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

The database `SportyShoes` is created when the application is first run.

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
  package. The `ShoppingController` handles the shopping web requests, and the `UserController`
  handles the
  user web requests. There are also three admin controllers.
- The [`config`](./src/main/java/com/sportyshoes/config) package contains the security and web
  configuration.
- In the [`resources`](./src/main/resources) folder, the `application.properties` file contains
  the database configuration, and the `templates` folder contains the HTML templates for the
  application. The `static` folder contains some JavaScript and the logo.

A tree diagram of the project structure is below.

```
.
├── main
│   ├── java
│   │   └── com
│   │       └── sportyshoes
│   │           ├── SportyShoes
│   │           │   └── SportyShoesApplication.java
│   │           ├── config
│   │           │   ├── MySecurityConfig.java
│   │           │   ├── MySuccessHandler.java
│   │           │   ├── MyWebConfig.java
│   │           │   └── TrailingSlashRedirectFilter.java
│   │           ├── controller
│   │           │   ├── AdminController.java
│   │           │   ├── AdminProductCategoryController.java
│   │           │   ├── AdminProductController.java
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
│   │               ├── PasswordEncoderService.java
│   │               ├── ProductCategoryService.java
│   │               ├── ProductService.java
│   │               └── UserService.java
│   └── resources
│       ├── application.properties
│       ├── static
│       │   ├── favicon.ico
│       │   ├── filters.js
│       │   ├── logo.png
│       │   └── logo_navbar.png
│       └── templates
│           ├── admin_product_categories.html
│           ├── admin_product_categories_edit.html
│           ├── admin_products.html
│           ├── admin_products_edit.html
│           ├── admin_users.html
│           ├── change_password.html
│           ├── fragments
│           │   └── base.html
│           ├── login.html
│           ├── orders.html
│           ├── register.html
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

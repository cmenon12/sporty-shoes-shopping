# Sporty Shoes Shopping Screenshots

This is the screenshots for the Sporty Shoes Shopping project. The project is a web application that
allows users to browse and purchase shoes. The application is built using the Spring Boot framework
and the Thymeleaf templating engine, with a Bootstrap front-end.

Writeup of the application is in a separate file.

# User Registration and Login

When the application is first run and opened, the user is greeted with the login page. If the user
does not have an account, they can click on the "Register" link to create an account. If no user has
been created, a banner is shown asking the user to create the admin user for the site.

The user cannot access any other pages until they have logged in.

The login page is shown below.

![Login Page](./screenshots/login.png)

The registration page is shown below.

![Registration Page](./screenshots/register.png)

Once a user has been registered, the user can log in with their email address and password. The web
browser detects the login form, and fills in the email address and password automatically.

![Login Page after registering](./screenshots/login_registered.png)

Once the user has logged in, they are taken to the home page. There are no products because the
admin user has just been created.


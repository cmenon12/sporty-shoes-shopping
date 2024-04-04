package com.sportyshoes.service;

import com.sportyshoes.entity.User;
import com.sportyshoes.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  private static final String ADMIN_EMAIL = "admin@sportyshoes.com";
  private static final String ADMIN_PASSWORD = "admin";

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public Optional<User> getByEmail(String email) {
    return userRepository.findById(email);
  }

  public Optional<User> getAdmin() {
    return getByEmail(ADMIN_EMAIL);
  }

  public String authenticate(User user) {
    Optional<User> userFound = getByEmail(user.getEmail());
    if (userFound.isEmpty()) {
      return "User not found";
    } else if (!userFound.get().getPassword().equals(user.getPassword())) {
      return "Incorrect password for user " + user.getEmail();
    } else {
      return "Welcome " + user.getEmail() + "!";
    }
  }

  public String create(User user) {
    if (validate(user) != null) {
      return validate(user);
    }
    if (user.getEmail() != null && getByEmail(user.getEmail()).isPresent()) {
      return "User already exists";
    }
    user.setIsAdmin(false);
    userRepository.save(user);
    return "User created successfully";
  }

  public String createAdmin() {
    if (getByEmail(ADMIN_EMAIL).isPresent()) {
      return "Admin user already exists";
    }
    User user = new User();
    user.setEmail(ADMIN_EMAIL);
    user.setPassword(ADMIN_PASSWORD);
    user.setIsAdmin(true);
    userRepository.save(user);
    System.out.println("Admin user created successfully");
    System.out.println("Email: " + ADMIN_EMAIL);
    System.out.println("Password: " + ADMIN_PASSWORD);
    return "Admin user created successfully! Login with email: `" + ADMIN_EMAIL
        + "` and password: `" + ADMIN_PASSWORD + "`";
  }

  public String update(User user) {
    if (validate(user) != null) {
      return validate(user);
    }
    if (user.getEmail() == null) {
      return "User email is null";
    }
    if (getByEmail(user.getEmail()).isEmpty()) {
      return "User " + user.getEmail() + " not found";
    }
    userRepository.save(user);
    return "User " + user.getEmail() + " updated successfully";
  }

  private String validate(User user) {
    if (user == null) {
      return "User is null";
    }
    if (user.getEmail() == null || user.getEmail().isEmpty()) {
      return "User email is required";
    }
    if (user.getPassword() == null || user.getPassword().isEmpty()) {
      return "User password is required";
    }
    if (user.getIsAdmin() == null) {
      return "User role is required";
    }
    if (user.getIsAdmin() && !user.getEmail()
        .equals(ADMIN_EMAIL)) {
      return "User cannot be an admin";
    }
    return null;
  }

}

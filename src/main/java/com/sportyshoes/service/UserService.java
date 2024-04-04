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


  public List<User> getAll() {
    return userRepository.findAll();
  }

  public Optional<User> getByEmail(String email) {
    return userRepository.findById(email);
  }

  public Optional<User> getAdmin() {
    List<User> admins = userRepository.getAdmin();
    if (admins.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(admins.get(0));
    }
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
    user.setIsAdmin(getAll().isEmpty());
    userRepository.save(user);
    return "User created successfully" + (user.getIsAdmin() ? " as admin" : "");
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
    return null;
  }
}

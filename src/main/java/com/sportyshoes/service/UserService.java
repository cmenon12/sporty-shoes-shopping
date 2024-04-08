package com.sportyshoes.service;

import com.sportyshoes.entity.User;
import com.sportyshoes.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  ApplicationContext context;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findById(email);
    if (user.isPresent()) {
      return org.springframework.security.core.userdetails.User.builder()
          .username(user.get()
              .getEmail())
          .password(user.get()
              .getPassword())
          .roles(parseRoles(user.get()))
          .build();
    } else {
      throw new UsernameNotFoundException("User " + email + " not found.");
    }
  }

  private String[] parseRoles(User user) {
    if (user.getIsAdmin()) {
      return new String[]{"ADMIN", "USER"};
    } else {
      return new String[]{"USER"};
    }
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public List<String> getAllEmails() {
    return userRepository.findAllEmails();
  }

  public Optional<User> getByEmail(String email) {
    return userRepository.findById(email);
  }

  public Optional<User> get(UserDetails userDetails) {
    return getByEmail(userDetails.getUsername());
  }

  public String create(User user) {
    PasswordEncoderService passwordEncoderService = context.getBean(PasswordEncoderService.class);
    if (validate(user) != null) {
      return validate(user);
    }
    if (getByEmail(user.getEmail()).isPresent()) {
      return "User already exists";
    }
    user.setIsAdmin(getAll().isEmpty());
    user.setPassword(passwordEncoderService.encode(user.getPassword()));
    userRepository.save(user);
    return "User created successfully" + (user.getIsAdmin() ? " as admin" : "");
  }

  public String changePassword(User user, String existing, String new1, String new2) {
    PasswordEncoderService passwordEncoderService = context.getBean(PasswordEncoderService.class);
    if (existing == null || existing.isEmpty()) {
      return "Existing password is required";
    }
    if (new1 == null || new1.isEmpty() || new2 == null || new2.isEmpty()) {
      return "Both new passwords are required";
    }
    if (!new1.equals(new2)) {
      return "New passwords do not match";
    }
    String dbPassword = user.getPassword();
    if (!passwordEncoderService.matches(existing, dbPassword)) {
      return "Existing password is incorrect";
    }
    user.setPassword(passwordEncoderService.encode(new1));
    userRepository.save(user);
    return "Password changed successfully";
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

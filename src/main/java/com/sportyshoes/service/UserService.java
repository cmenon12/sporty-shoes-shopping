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

  public Optional<User> getByEmail(String email) {
    return userRepository.findById(email);
  }

  public Optional<User> get(UserDetails userDetails) {
    return getByEmail(userDetails.getUsername());
  }

  public String create(User user) {
    PasswordEncoderService passwordEncoderService = context.getBean(PasswordEncoderService.class);
    user.setPassword(passwordEncoderService.encode(user.getPassword()));
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

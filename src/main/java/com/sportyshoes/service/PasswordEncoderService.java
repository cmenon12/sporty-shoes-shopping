package com.sportyshoes.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Getter
@Service
public class PasswordEncoderService {

  @Autowired
  PasswordEncoder passwordEncoder;

  public String encode(String password) {
    return passwordEncoder.encode(password);
  }

  public boolean matches(String password, String encodedPassword) {
    return passwordEncoder.matches(password, encodedPassword);
  }

}

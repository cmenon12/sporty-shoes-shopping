package com.sportyshoes.controller;

import com.sportyshoes.entity.User;
import com.sportyshoes.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @GetMapping(value = "/login")
  public String login(Model model, HttpSession session) {
    if (session.getAttribute("user") != null) {
      return "redirect:/";
    }
    if (userService.getAll()
        .isEmpty()) {
      model.addAttribute(
          "resultInfo", "No users found. Please register a user who will be the admin.");
    }
    model.addAttribute("navbar", false);
    model.addAttribute("user", new User());
    return "login";
  }

  @GetMapping(value = "/register")
  public String register(Model model, HttpSession session) {
    if (session.getAttribute("user") != null) {
      return "redirect:/";
    }
    if (userService.getAll()
        .isEmpty()) {
      model.addAttribute(
          "resultInfo", "No users found. Please register a user who will be the admin.");
    }
    model.addAttribute("navbar", false);
    model.addAttribute("user", new User());
    return "register";
  }

  @PostMapping(value = "/register")
  public String register(User user, RedirectAttributes redirectAttrs, HttpSession session) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    String result = userService.create(user);
    if (result.contains("created")) {
      session.setAttribute(
          "user", userService.getByEmail(user.getEmail())
              .get());
      redirectAttrs.addFlashAttribute("resultSuccess", result);
      return "redirect:/";
    } else {
      redirectAttrs.addFlashAttribute("user", user);
      redirectAttrs.addFlashAttribute("resultDanger", result);
      return "redirect:/register";
    }
  }

}

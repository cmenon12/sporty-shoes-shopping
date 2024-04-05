package com.sportyshoes.controller;

import com.sportyshoes.entity.User;
import com.sportyshoes.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

  @Autowired
  UserService userService;

  final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

  @GetMapping(value = "/login")
  public String login(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    if (userDetails != null) {
      return "redirect:/";
    }
    if (userService.getAll()
        .isEmpty()) {
      model.addAttribute(
          "resultInfo", "No users found. Please register a user who will be the admin.");
    }
    model.addAttribute("user", new User());
    return "login";
  }

  @GetMapping(value = "/register")
  public String register(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    if (userDetails != null) {
      return "redirect:/";
    }
    if (userService.getAll()
        .isEmpty()) {
      model.addAttribute(
          "resultInfo", "No users found. Please register a user who will be the admin.");
    }
    model.addAttribute("user", new User());
    return "register";
  }

  @PostMapping(value = "/register")
  public String register(User user, RedirectAttributes redirectAttrs) {
    String result = userService.create(user);
    if (result.contains("created")) {
      redirectAttrs.addFlashAttribute("resultSuccess", result);
      return "redirect:/login";
    } else {
      redirectAttrs.addFlashAttribute("user", user);
      redirectAttrs.addFlashAttribute("resultDanger", result);
      return "redirect:/register";
    }
  }

  @GetMapping(value = "/logout")
  public String logout(Authentication authentication, HttpServletRequest request,
      HttpServletResponse response) {
    this.logoutHandler.logout(request, response, authentication);
    return "redirect:/login?logout";
  }

  @GetMapping(value = "/change-password")
  public String changePassword() {
    return "change_password";
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @PostMapping(value = "/change-password")
  public String changePassword(@AuthenticationPrincipal UserDetails userDetails,
      @RequestBody MultiValueMap<String, String> userFormData, Model model,
      RedirectAttributes redirectAttrs) {
    User user = userService.getByEmail(userDetails.getUsername())
        .get();
    String existing = userFormData.getFirst("existingPassword");
    String new1 = userFormData.getFirst("newPassword1");
    String new2 = userFormData.getFirst("newPassword2");
    String result = userService.changePassword(user, existing, new1, new2);
    if (result.contains("successfully")) {
      redirectAttrs.addFlashAttribute("resultSuccess", result);
      return "redirect:/";
    } else {
      model.addAttribute("resultDanger", result);
      return "change_password";
    }
  }

}

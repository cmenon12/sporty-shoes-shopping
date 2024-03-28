package com.sportyshoes.controller;

import com.sportyshoes.entity.User;
import com.sportyshoes.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

  @Autowired
  UserService userService;

  @GetMapping(value = "/")
  public String index(HttpSession session) {
    if (session.getAttribute("user") == null) {
      return "redirect:/login";
    }
    return "index";
  }

  @GetMapping(value = "/login")
  public String login(Model model, HttpSession session) {
    if (session.getAttribute("user") != null) {
      return "redirect:/";
    }
    int adminCreated = userService.createAdmin();
    model.addAttribute("adminCreated", adminCreated);
    if (adminCreated == 1 && userService.getAdmin()
        .isPresent()) {
      model.addAttribute("user", userService.getAdmin()
          .get());
    } else if (model.getAttribute("user") == null) {
      model.addAttribute("user", new User());
    }
    return "login";
  }

  @PostMapping(value = "/login")
  public String login(User user, RedirectAttributes redirectAttrs, HttpSession session) {

    String result = userService.authenticate(user);
    if (result.contains("Welcome")) {
      session.setAttribute("user", user);
      redirectAttrs.addFlashAttribute("resultSuccess", result);
      return "redirect:/";
    } else if (result.contains("Incorrect")) {
      redirectAttrs.addFlashAttribute("user", user);
      redirectAttrs.addFlashAttribute("resultDanger", result);
      return "redirect:/login";
    } else {
      result = userService.create(user);
      if (result.contains("created")) {
        session.setAttribute("user", user);
        redirectAttrs.addFlashAttribute("resultSuccess", result);
        return "redirect:/";
      } else {
        redirectAttrs.addFlashAttribute("user", user);
        redirectAttrs.addFlashAttribute("resultDanger", result);
        return "redirect:/login";
      }
    }
  }

  @GetMapping(value = "/logout")
  public String logout(HttpSession session) {
    session.removeAttribute("user");
    return "redirect:/login";
  }

}

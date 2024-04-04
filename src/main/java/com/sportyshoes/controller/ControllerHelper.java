package com.sportyshoes.controller;

import com.sportyshoes.entity.User;
import jakarta.servlet.http.HttpSession;

public class ControllerHelper {

  static String parseAdminUser(HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return "redirect:/login";
    }
    if (!user.getIsAdmin()) {
      return "redirect:/";
    }
    return null;
  }

}

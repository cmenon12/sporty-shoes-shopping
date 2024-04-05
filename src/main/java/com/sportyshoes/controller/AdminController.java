package com.sportyshoes.controller;

import com.sportyshoes.entity.Order;
import com.sportyshoes.service.OrderService;
import com.sportyshoes.service.ProductCategoryService;
import com.sportyshoes.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  ProductCategoryService productCategoryService;

  @Autowired
  OrderService orderService;

  @Autowired
  UserService userService;

  @GetMapping(value = "/orders")
  public String orders(Model model) {
    List<Order> allOrders = orderService.getAll(-1);
    if (allOrders.isEmpty()) {
      model.addAttribute("resultInfo", "There are no orders.");
    }
    model.addAttribute("allOrders", allOrders);
    model.addAttribute("allCategories", productCategoryService.getAll());
    model.addAttribute("showUser", true);
    return "orders";
  }

  @GetMapping(value = "/users")
  public String users(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    model.addAttribute("allUsers", userService.getAll());
    model.addAttribute("user", true);
    return "admin_users";
  }

}

package com.sportyshoes.controller;

import static com.sportyshoes.controller.ControllerHelper.parseAdminUser;

import com.sportyshoes.entity.Order;
import com.sportyshoes.service.OrderService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  OrderService orderService;

  @GetMapping(value = "/orders")
  public String orders(Model model, HttpSession session) {
    if (parseAdminUser(session) != null) {
      return parseAdminUser(session);
    }
    model.addAttribute("user", session.getAttribute("user"));
    List<Order> allOrders = orderService.getAll(-1);
    model.addAttribute("allOrders", allOrders);
    model.addAttribute("showUser", true);
    return "orders";
  }

}

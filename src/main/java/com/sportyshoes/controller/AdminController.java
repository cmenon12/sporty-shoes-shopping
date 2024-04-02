package com.sportyshoes.controller;

import com.sportyshoes.entity.Product;
import com.sportyshoes.entity.User;
import com.sportyshoes.service.ProductService;
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
  ProductService productService;

  @GetMapping(value = "/")
  public String productsRedirect() {
    return "redirect:/admin/products";
  }

  @GetMapping(value = "/products")
  public String products(Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return "redirect:/login";
    }
    if (!user.getIsAdmin()) {
      return "redirect:/";
    }
    model.addAttribute("user", user);
    List<Product> allProducts = productService.getAll();
    if (allProducts.isEmpty()) {
      model.addAttribute("resultInfo", "There are no products.");
    }
    model.addAttribute("allProducts", allProducts);
    return "admin_products";
  }

}

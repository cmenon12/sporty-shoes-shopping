package com.sportyshoes.controller;

import com.sportyshoes.entity.Product;
import com.sportyshoes.service.ProductService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingController {

  @Autowired
  ProductService productService;

  @GetMapping(value = "/")
  public String home(Model model, HttpSession session) {
    if (session.getAttribute("user") == null) {
      return "redirect:/login";
    }
    List<Product> allProducts = productService.getAll();
    if (allProducts.isEmpty()) {
      model.addAttribute("resultInfo", "There are no products.");
    }
    model.addAttribute("allProducts", allProducts);
    return "shop";
  }

}

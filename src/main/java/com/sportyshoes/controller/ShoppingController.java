package com.sportyshoes.controller;

import com.sportyshoes.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingController {

  @Autowired
  ProductService productService;

  @GetMapping(value = "/")
  public String home(Model model) {
    model.addAttribute("allProducts", productService.getAll());
    return "shop";
  }

}

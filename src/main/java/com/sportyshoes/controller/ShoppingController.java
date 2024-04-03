package com.sportyshoes.controller;

import com.sportyshoes.entity.Order;
import com.sportyshoes.entity.Product;
import com.sportyshoes.entity.User;
import com.sportyshoes.service.OrderService;
import com.sportyshoes.service.ProductService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ShoppingController {

  @Autowired
  ProductService productService;

  @Autowired
  OrderService orderService;

  @GetMapping(value = "/")
  public String home(Model model, HttpSession session) {
    if (session.getAttribute("user") == null) {
      return "redirect:/login";
    }
    model.addAttribute("user", session.getAttribute("user"));
    List<Product> allProducts = productService.getAll();
    if (allProducts.isEmpty()) {
      model.addAttribute("resultInfo", "There are no products.");
    }
    model.addAttribute("allProducts", allProducts);
    return "shop";
  }

  @PostMapping(value = "/order")
  public String order(HttpSession session, RedirectAttributes redirectAttrs,
      @RequestBody MultiValueMap<String, String> userFormData) {
    if (session.getAttribute("user") == null) {
      return "redirect:/login";
    }

    // Check if address is provided
    String address = userFormData.getFirst("address");
    if (Objects.isNull(address) || address.isBlank()) {
      redirectAttrs.addFlashAttribute("resultDanger", "Address is required.");
      return "redirect:/";
    }

    // prepare to loop over products
    List<String> errors = new ArrayList<>();
    List<Product> basket = new ArrayList<>();

    for (String key : userFormData.keySet()) {
      if (!key.startsWith("product-input-")) {
        continue;
      }
      try {
        long productId = Long.parseLong(key.replace("product-input-", ""));
        Optional<Product> product = productService.getById(productId);
        if (product.isEmpty()) {
          errors.add("Product with ID=" + productId + " not found");
          continue;
        }
        int quantity = Integer.parseInt(
            Objects.requireNonNull(userFormData.getFirst(key)));
        if (quantity > 0 && quantity <= product.get()
            .getStock()) {
          for (int i = 0; i < quantity; i++) {
            basket.add(product.get());
          }
          String result = productService.decreaseStock(product.get(), quantity);
          if (!result.contains("successfully")) {
            errors.add(result);
          }
        }
      } catch (NumberFormatException e) {
        errors.add("Invalid product ID: " + key.replace("product-input-", ""));
      } catch (NullPointerException e) {
        errors.add("Invalid product quantity for product with ID=" + key);
      }
    }

    // check if the basket is empty or there were errors
    if (basket.isEmpty() || !errors.isEmpty()) {
      if (basket.isEmpty()) {
        redirectAttrs.addFlashAttribute("resultInfo", "Basket is empty.");
      }
      if (!errors.isEmpty()) {
        StringBuilder resultDanger = new StringBuilder();
        for (String error : errors) {
          resultDanger.append(error)
              .append(". ");
        }
        redirectAttrs.addFlashAttribute("resultDanger", resultDanger.toString());
      }
      return "redirect:/";

    } else {
      // create order
      Order order = new Order();
      order.setAddress(address);
      order.setProducts(basket);
      order.setUser((User) session.getAttribute("user"));
      String result = orderService.create(order);
      if (result.contains("created")) {
        redirectAttrs.addFlashAttribute("resultSuccess", result);
      } else {
        redirectAttrs.addFlashAttribute("resultDanger", result);
      }
      return "redirect:/";
    }
  }

  @GetMapping(value = "/orders")
  public String orders(Model model, HttpSession session) {
    if (session.getAttribute("user") == null) {
      return "redirect:/login";
    }
    model.addAttribute("user", session.getAttribute("user"));
    List<Order> allOrders = orderService.getAllByUser((User) session.getAttribute("user"), -1);
    model.addAttribute("allOrders", allOrders);
    return "orders";
  }


}

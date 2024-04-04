package com.sportyshoes.controller;

import com.sportyshoes.entity.Order;
import com.sportyshoes.entity.Product;
import com.sportyshoes.service.OrderService;
import com.sportyshoes.service.ProductService;
import com.sportyshoes.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

  @Autowired
  UserService userService;

  @GetMapping(value = "/")
  public String home(Model model) {
    List<Product> allProducts = productService.getAll();
    if (allProducts.isEmpty()) {
      model.addAttribute("resultInfo", "There are no products.");
    }
    model.addAttribute("allProducts", allProducts);
    return "shop";
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @PostMapping(value = "/order")
  public String order(@AuthenticationPrincipal UserDetails userDetails,
      RedirectAttributes redirectAttrs,
      @RequestBody MultiValueMap<String, String> userFormData) {

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
        if (quantity > 0) {
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

    } else {
      // create order
      Order order = new Order();
      order.setAddress(address);
      order.setProducts(basket);
      order.setUser(userService.get(userDetails)
          .get());
      String result = orderService.create(order);
      if (result.contains("created")) {
        redirectAttrs.addFlashAttribute("resultSuccess", result);
      } else {
        redirectAttrs.addFlashAttribute("resultDanger", result);
      }
    }
    return "redirect:/";
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @GetMapping(value = "/orders")
  public String orders(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    List<Order> allOrders = orderService.getAllByUser(userService.get(userDetails)
        .get(), -1);
    if (allOrders.isEmpty()) {
      model.addAttribute("resultInfo", "There are no orders.");
    }
    model.addAttribute("allOrders", allOrders);
    return "orders";
  }


}

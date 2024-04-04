package com.sportyshoes.controller;

import com.sportyshoes.entity.ProductCategory;
import com.sportyshoes.entity.User;
import com.sportyshoes.service.ProductCategoryService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminProductCategoryController {

  @Autowired
  ProductCategoryService categoryService;

  @GetMapping(value = "/product-categories")
  public String categories(Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return "redirect:/login";
    }
    if (!user.getIsAdmin()) {
      return "redirect:/";
    }
    model.addAttribute("user", user);
    List<ProductCategory> allCategories = categoryService.getAll();
    if (allCategories.isEmpty()) {
      model.addAttribute("resultInfo", "There are no product categories.");
    }
    model.addAttribute("allCategories", allCategories);
    return "admin_product_categories";
  }

  @GetMapping(value = "/product-categories/{id}")
  public String update(@PathVariable("id") Integer id, Model model, HttpSession session,
      RedirectAttributes redirectAttrs) {
    if (parseUser(session) != null) {
      return parseUser(session);
    }
    model.addAttribute("user", session.getAttribute("user"));
    Optional<ProductCategory> category = categoryService.getById(id);
    if (category.isEmpty()) {
      redirectAttrs.addFlashAttribute(
          "resultDanger", "ProductCategory with ID=" + id + " not found.");
      return "redirect:/admin/product-categories";
    }
    model.addAttribute("category", category.get());
    return "admin_product_categories_edit";
  }

  @GetMapping(value = "/product-categories/new")
  public String create(Model model, HttpSession session) {
    if (parseUser(session) != null) {
      return parseUser(session);
    }
    model.addAttribute("user", session.getAttribute("user"));
    model.addAttribute("category", new ProductCategory());
    return "admin_product_categories_edit";
  }

  @PostMapping(value = "/product-categories/{id}")
  public String update(@PathVariable("id") Integer id, ProductCategory category, Model model,
      HttpSession session, RedirectAttributes redirectAttrs) {
    if (parseUser(session) != null) {
      return parseUser(session);
    }
    model.addAttribute("user", session.getAttribute("user"));
    if (category.getId() != (long) id) {
      redirectAttrs.addFlashAttribute("resultDanger", "ProductCategory ID mismatch.");
      return "redirect:/admin/product-categories";
    }
    Optional<ProductCategory> existingProduct = categoryService.getById(id);
    if (existingProduct.isEmpty()) {
      redirectAttrs.addFlashAttribute(
          "resultDanger", "ProductCategory with ID=" + id + " not found.");
      return "redirect:/admin/product-categories";
    }
    String result = categoryService.update(category);
    if (result.contains("updated successfully")) {
      redirectAttrs.addFlashAttribute("resultSuccess", result);
    } else {
      redirectAttrs.addFlashAttribute("resultDanger", result);
    }
    return "redirect:/admin/product-categories";
  }

  @PostMapping(value = "/product-categories/new")
  public String create(ProductCategory category, Model model,
      HttpSession session, RedirectAttributes redirectAttrs) {
    if (parseUser(session) != null) {
      return parseUser(session);
    }
    model.addAttribute("user", session.getAttribute("user"));
    String result = categoryService.create(category);
    if (result.contains("created successfully")) {
      redirectAttrs.addFlashAttribute("resultSuccess", result);
    } else {
      redirectAttrs.addFlashAttribute("resultDanger", result);
    }
    return "redirect:/admin/product-categories";
  }

  @GetMapping(value = "/product-categories/{id}/delete")
  public String delete(@PathVariable("id") Integer id, Model model,
      HttpSession session, RedirectAttributes redirectAttrs) {
    if (parseUser(session) != null) {
      return parseUser(session);
    }
    model.addAttribute("user", session.getAttribute("user"));
    Optional<ProductCategory> existingCategory = categoryService.getById(id);
    if (existingCategory.isEmpty()) {
      redirectAttrs.addFlashAttribute(
          "resultDanger", "ProductCategory with ID=" + id + " not found.");
      return "redirect:/admin/product-categories";
    }
    String result = categoryService.delete(id);
    if (result.contains("deleted successfully")) {
      redirectAttrs.addFlashAttribute("resultSuccess", result);
    } else {
      redirectAttrs.addFlashAttribute("resultDanger", result);
    }
    return "redirect:/admin/product-categories";
  }

  private String parseUser(HttpSession session) {
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

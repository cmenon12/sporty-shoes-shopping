package com.sportyshoes.controller;

import com.sportyshoes.entity.Product;
import com.sportyshoes.entity.ProductCategory;
import com.sportyshoes.service.ProductCategoryService;
import com.sportyshoes.service.ProductService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminProductController {

  @Autowired
  ProductService productService;

  @Autowired
  ProductCategoryService categoryService;

  @GetMapping(value = "")
  public String productsRedirect() {
    return "redirect:/admin/products";
  }

  @GetMapping(value = "/products")
  public String products(Model model) {
    List<Product> allProducts = productService.getAll();
    if (allProducts.isEmpty()) {
      model.addAttribute("resultInfo", "There are no products.");
    }
    model.addAttribute("allProducts", allProducts);
    return "admin_products";
  }

  @GetMapping(value = "/products/{id}")
  public String update(@PathVariable("id") Integer id, Model model,
      RedirectAttributes redirectAttrs) {
    Optional<Product> product = productService.getById(id);
    if (product.isEmpty()) {
      redirectAttrs.addFlashAttribute("resultDanger", "Product with ID=" + id + " not found.");
      return "redirect:/admin/products";
    }
    model.addAttribute("product", product.get());
    List<ProductCategory> allCategories = categoryService.getAll();
    model.addAttribute("allCategories", allCategories);
    return "admin_products_edit";
  }

  @GetMapping(value = "/products/new")
  public String create(Model model) {
    model.addAttribute("product", new Product());
    List<ProductCategory> allCategories = categoryService.getAll();
    model.addAttribute("allCategories", allCategories);
    return "admin_products_edit";
  }

  @PostMapping(value = "/products/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String update(@PathVariable("id") Integer id, Product product,
      @RequestBody MultiValueMap<String, String> formData,
      RedirectAttributes redirectAttrs) {
    if (product.getId() != (long) id) {
      redirectAttrs.addFlashAttribute("resultDanger", "Product ID mismatch.");
      return "redirect:/admin/products";
    }
    Optional<Product> existingProduct = productService.getById(id);
    if (existingProduct.isEmpty()) {
      redirectAttrs.addFlashAttribute("resultDanger", "Product with ID=" + id + " not found.");
      return "redirect:/admin/products";
    }
    if (parseProductCategory(product, formData, redirectAttrs)) {
      return "redirect:/admin/products";
    }
    String result = productService.update(product);
    if (result.contains("updated successfully")) {
      redirectAttrs.addFlashAttribute("resultSuccess", result);
    } else {
      redirectAttrs.addFlashAttribute("resultDanger", result);
    }
    return "redirect:/admin/products";
  }

  @PostMapping(value = "/products/new", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String create(Product product,
      @RequestBody MultiValueMap<String, String> formData, RedirectAttributes redirectAttrs) {
    if (parseProductCategory(product, formData, redirectAttrs)) {
      return "redirect:/admin/products";
    }
    String result = productService.create(product);
    if (result.contains("created successfully")) {
      redirectAttrs.addFlashAttribute("resultSuccess", result);
    } else {
      redirectAttrs.addFlashAttribute("resultDanger", result);
    }
    return "redirect:/admin/products";
  }

  @GetMapping(value = "/products/{id}/delete")
  public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttrs) {
    Optional<Product> product = productService.getById(id);
    if (product.isEmpty()) {
      redirectAttrs.addFlashAttribute("resultDanger", "Product with ID=" + id + " not found.");
      return "redirect:/admin/products";
    }
    String result = productService.delete(id);
    if (result.contains("deleted successfully")) {
      redirectAttrs.addFlashAttribute("resultSuccess", result);
    } else {
      redirectAttrs.addFlashAttribute("resultDanger", result);
    }
    return "redirect:/admin/products";
  }

  private boolean parseProductCategory(Product product,
      @RequestBody MultiValueMap<String, String> formData,
      RedirectAttributes redirectAttrs) {
    if (Objects.equals(formData.get("categoryId")
        .get(0), "none")
        || Objects.isNull(formData.get("categoryId")
        .get(0))) {
      product.setCategory(null);
    } else {
      Optional<ProductCategory> category = categoryService.getById(Long.parseLong(
          formData.get("categoryId")
              .get(0)));
      if (category.isEmpty()) {
        redirectAttrs.addFlashAttribute(
            "resultDanger",
            "ProductCategory with ID=" + formData.get("categoryId")
                .get(0) + " not found.");
        return true;
      }
      product.setCategory(category.get());
    }
    return false;
  }

}

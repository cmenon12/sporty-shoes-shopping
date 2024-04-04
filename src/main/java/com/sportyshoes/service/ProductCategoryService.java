package com.sportyshoes.service;

import com.sportyshoes.entity.ProductCategory;
import com.sportyshoes.repository.ProductCategoryRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService {

  @Autowired
  ProductCategoryRepository productCategoryRepository;

  @Autowired
  ProductService productService;

  public List<ProductCategory> getAll() {
    return productCategoryRepository.findAll();
  }

  public Optional<ProductCategory> getById(long id) {
    return productCategoryRepository.findById(id);
  }

  public Optional<ProductCategory> getByName(String name) {
    return productCategoryRepository.findByName(name);
  }

  public String create(ProductCategory productCategory) {
    if (validate(productCategory) != null) {
      return validate(productCategory);
    }
    if (productCategory.getId() != null && getById(productCategory.getId()).isPresent()) {
      return "ProductCategory with ID=" + productCategory.getId() + "already exists";
    }
    if (getByName(productCategory.getName()).isPresent()) {
      return "ProductCategory " + productCategory.getName() + "already exists";
    }
    productCategoryRepository.save(productCategory);
    return "ProductCategory created successfully";
  }

  public String update(ProductCategory productCategory) {
    if (validate(productCategory) != null) {
      return validate(productCategory);
    }
    if (productCategory.getId() == null) {
      return "ProductCategory ID is null";
    }
    if (productCategory.getName() == null) {
      return "ProductCategory name is null";
    }
    if (getById(productCategory.getId()).isEmpty()) {
      return "ProductCategory with ID=" + productCategory.getId() + "not found";
    }
    if (!Objects.equals(getById(productCategory.getId()).get()
        .getName(), productCategory.getName()) && getByName(
        productCategory.getName()).isPresent()) {
      return "ProductCategory " + productCategory.getName() + " already exists";
    }
    productCategoryRepository.save(productCategory);
    return "ProductCategory " + productCategory.getName() + " updated successfully";
  }

  private String validate(ProductCategory productCategory) {
    if (productCategory == null) {
      return "ProductCategory is null";
    }
    if (productCategory.getName() == null || productCategory.getName().isEmpty()) {
      return "ProductCategory name is required";
    }
    if (productCategory.getName()
        .trim()
        .equals("none")) {
      return "ProductCategory name cannot be 'none'";
    }
    return null;
  }

}

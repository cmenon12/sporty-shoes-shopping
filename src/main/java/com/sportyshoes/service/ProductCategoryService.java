package com.sportyshoes.service;

import com.sportyshoes.entity.ProductCategory;
import com.sportyshoes.repository.ProductCategoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService {

  @Autowired
  ProductCategoryRepository productCategoryRepository;

  public List<ProductCategory> getAll() {
    return productCategoryRepository.findAll();
  }

  public Optional<ProductCategory> getByName(String name) {
    return productCategoryRepository.findById(name);
  }

  public String create(ProductCategory productCategory) {
    if (validate(productCategory) != null) {
      return validate(productCategory);
    }
    if (productCategory.getName() != null && getByName(productCategory.getName()).isPresent()) {
      return "ProductCategory already exists";
    }
    productCategoryRepository.save(productCategory);
    return "ProductCategory created successfully";
  }

  public String update(ProductCategory productCategory) {
    if (validate(productCategory) != null) {
      return validate(productCategory);
    }
    if (productCategory.getName() == null) {
      return "ProductCategory name is null";
    }
    if (getByName(productCategory.getName()).isEmpty()) {
      return "ProductCategory not found";
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
    return null;
  }

}

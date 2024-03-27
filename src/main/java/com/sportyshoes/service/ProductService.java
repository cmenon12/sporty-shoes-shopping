package com.sportyshoes.service;

import com.sportyshoes.entity.Product;
import com.sportyshoes.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired
  ProductRepository productRepository;

  public List<Product> getAll() {
    return productRepository.findAll();
  }

  public Optional<Product> getById(long id) {
    return productRepository.findById(id);
  }

  public String create(Product product) {
    if (validate(product) != null) {
      return validate(product);
    }
    if (product.getId() != null && getById(product.getId()).isPresent()) {
      return "Product already exists";
    }
    productRepository.save(product);
    return "Product created successfully";
  }

  public String update(Product product) {
    if (validate(product) != null) {
      return validate(product);
    }
    if (product.getId() == null) {
      return "Product ID is null";
    }
    if (getById(product.getId()).isEmpty()) {
      return "Product not found";
    }
    productRepository.save(product);
    return "Product " + product.getName() + " updated successfully";
  }

  public String delete(long id) {
    Optional<Product> product = getById(id);
    if (product.isPresent()) {
      product.get().setIsDeleted(true);
      productRepository.save(product.get());
      return "Product with ID=" + id + " deleted successfully";
    }
    return "Product with ID=" + id + " not found";
  }

  public String delete(Product product) {
    if (product == null) {
      return "Product is null";
    }
    if (getById(product.getId()).isEmpty()) {
      return "Product not found";
    }
    product.setIsDeleted(true);
    productRepository.save(product);
    return "Product " + product.getName() + " deleted successfully";
  }

  private String validate(Product product) {
    if (product == null) {
      return "Product is null";
    }
    if (product.getName() == null || product.getName().isEmpty()) {
      return "Product name is required";
    }
    if (product.getPrice() == null || product.getPrice() < 0) {
      return "Product price is required";
    }
    if (product.getStock() == null || product.getStock() < 0) {
      return "Product stock is required";
    }
    if (product.getIsDeleted()) {
      return "Product has been deleted";
    }
    return null;
  }

}

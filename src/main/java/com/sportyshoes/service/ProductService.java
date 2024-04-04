package com.sportyshoes.service;

import com.sportyshoes.entity.Product;
import com.sportyshoes.entity.ProductCategory;
import com.sportyshoes.repository.ProductRepository;
import java.util.List;
import java.util.Objects;
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

  public List<Product> getByProductCategory(ProductCategory category) {
    return productRepository.findByProductCategory(category);
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

  /**
   * Update product by copying it and soft-deleting the old one.
   *
   * @param product Product the new product
   * @return String the result message
   */
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
    Product newProduct = new Product();
    newProduct.setName(product.getName());
    newProduct.setDescription(product.getDescription());
    newProduct.setPrice(product.getPrice());
    newProduct.setStock(product.getStock());
    newProduct.setCategory(product.getCategory());
    Product existingProduct = getById(product.getId()).get();
    if (!equivalent(existingProduct, newProduct)) {
      productRepository.save(newProduct);
      product.setIsDeleted(true);
      productRepository.save(product);
    }
    return "Product " + newProduct.getName() + " updated successfully";
  }

  public String decreaseStock(Product product, int quantity) {
    if (validate(product) != null) {
      return validate(product);
    }
    if (product.getId() == null) {
      return "Product ID is null";
    }
    if (product.getStock() < quantity) {
      return "Product stock is not enough";
    }
    product.setStock(product.getStock() - quantity);
    productRepository.save(product);
    return "Product " + product.getName() + " stock decreased successfully";
  }

  public String delete(long id) {
    Optional<Product> product = getById(id);
    if (product.isPresent()) {
      return delete(product.get());
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

  private boolean equivalent(Product product1, Product product2) {
    return Objects.equals(product1.getName(), product2.getName())
        && Objects.equals(product1.getDescription(), product2.getDescription())
        && Objects.equals(product1.getPrice(), product2.getPrice())
        && Objects.equals(product1.getStock(), product2.getStock())
        && Objects.equals(product1.getCategory(), product2.getCategory());
  }

}

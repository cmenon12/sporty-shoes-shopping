package com.sportyshoes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @EqualsAndHashCode.Include
  private final LocalDateTime created = LocalDateTime.now();
  @Lob
  @Column(length = 1000)
  private String address;

  @ManyToOne
  @EqualsAndHashCode.Include
  private User user;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "product_orders",
      joinColumns = @JoinColumn(name = "orders_id"),
      inverseJoinColumns = @JoinColumn(name = "products_id"))
  private List<Product> products;

  public Float getTotalCost() {
    Float totalCost = 0.0f;
    for (int i = 0; i < this.getProducts()
        .size(); i++) {
      totalCost += this.getProducts()
          .get(i)
          .getPrice();
    }
    return totalCost;
  }

  public Map<Product, Integer> getProductsMap() {
    Map<Product, Integer> productsMap = new java.util.HashMap<>();
    for (int i = 0; i < this.getProducts()
        .size(); i++) {
      Product product = this.getProducts()
          .get(i);
      System.out.println(product);
      if (productsMap.containsKey(product)) {
        productsMap.put(product, productsMap.get(product) + 1);
      } else {
        productsMap.put(product, 1);
      }
    }
    System.out.println(productsMap);
    return productsMap;
  }

}

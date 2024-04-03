package com.sportyshoes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = "orders")
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;
  @EqualsAndHashCode.Include
  private final LocalDateTime created = LocalDateTime.now();
  private String name;
  @Lob
  @Column(length = 1000)
  private String description;
  private Float price;
  private Integer stock = 0;
  private Boolean isDeleted = false;

  @ManyToOne
  private ProductCategory category;

  @ManyToMany(mappedBy = "products")
  private List<Order> orders;

}

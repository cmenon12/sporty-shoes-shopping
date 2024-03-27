package com.sportyshoes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
  private Long id;
  @EqualsAndHashCode.Include
  private final LocalDateTime created = LocalDateTime.now();
  private String name;
  private String description;
  private Float price;
  private Integer stock = 0;
  private Boolean isDeleted = false;

  @ManyToOne
  private ProductCategory category;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Order> orders;

}

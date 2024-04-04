package com.sportyshoes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "product_category")
@Getter
@Setter
@ToString(exclude = "products")
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @EqualsAndHashCode.Include
  private String name;

  @OneToMany(mappedBy = "category")
  private Set<Product> products;

  public Set<Product> getProducts(boolean includeDeleted) {
    return products.stream()
        .filter(p -> includeDeleted || !p.getIsDeleted())
        .collect(Collectors.toSet());
  }

}

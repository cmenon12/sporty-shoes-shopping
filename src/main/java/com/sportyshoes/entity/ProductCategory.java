package com.sportyshoes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
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
  @EqualsAndHashCode.Include
  private String name;

  @OneToMany(mappedBy = "category")
  private List<Product> products;

}

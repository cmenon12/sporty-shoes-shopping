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

@Entity(name = "users")
@Getter
@Setter
@ToString(exclude = "orders")
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

  @Id
  @EqualsAndHashCode.Include
  private String email;
  private String password;
  private Boolean isAdmin = false;

  @OneToMany(mappedBy = "user")
  private List<Order> orders;

}

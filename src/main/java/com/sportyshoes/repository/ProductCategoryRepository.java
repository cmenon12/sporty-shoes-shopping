package com.sportyshoes.repository;

import com.sportyshoes.entity.ProductCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

  @Query("select c from product_category c where c.name = :name")
  Optional<ProductCategory> findByName(String name);

  @Query("select c.name from product_category c")
  List<String> findAllNames();

}

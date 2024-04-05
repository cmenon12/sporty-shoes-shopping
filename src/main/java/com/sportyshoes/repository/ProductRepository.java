package com.sportyshoes.repository;

import com.sportyshoes.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @SuppressWarnings("NullableProblems")
  @Query("select p from Product p where p.isDeleted = false")
  List<Product> findAll();

  @Query("select p from Product p where p.id = :id and p.isDeleted = false")
  Optional<Product> findById(@Param("id") long id);

}

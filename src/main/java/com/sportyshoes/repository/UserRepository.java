package com.sportyshoes.repository;

import com.sportyshoes.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @Query("select u from User u where u.isAdmin = true limit 1")
  Optional<User> getAdmin();

}

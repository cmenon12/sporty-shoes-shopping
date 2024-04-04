package com.sportyshoes.repository;

import com.sportyshoes.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @Query("select u from users u where u.isAdmin = true")
  List<User> getAdmin();

}

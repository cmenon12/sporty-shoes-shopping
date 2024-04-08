package com.sportyshoes.repository;

import com.sportyshoes.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @Query("select u.email from users u")
  List<String> findAllEmails();

}

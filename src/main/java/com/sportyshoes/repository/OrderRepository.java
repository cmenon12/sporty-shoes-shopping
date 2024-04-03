package com.sportyshoes.repository;

import com.sportyshoes.entity.Order;
import com.sportyshoes.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query("select o from orders o order by o.created asc")
  List<Order> findAllSortChronologicallyAscending();

  @Query("select o from orders o order by o.created desc")
  List<Order> findAllSortChronologicallyDescending();

  @Query("select o from orders o where o.user = :user")
  List<Order> findAllByUser(User user);

  @Query("select o from orders o where o.user = :user order by o.created asc")
  List<Order> findAllByUserSortChronologicallyAscending(User user);

  @Query("select o from orders o where o.user = :user order by o.created desc")
  List<Order> findAllByUserSortChronologicallyDescending(User user);

}

package com.sportyshoes.service;

import com.sportyshoes.entity.Order;
import com.sportyshoes.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  @Autowired
  OrderRepository orderRepository;

  public List<Order> getAll() {
    return orderRepository.findAll();
  }

  public Optional<Order> getById(long id) {
    return orderRepository.findById(id);
  }

  public String create(Order order) {
    if (validate(order) != null) {
      return validate(order);
    }
    if (order.getId() != null && getById(order.getId()).isPresent()) {
      return "Order already exists";
    }
    orderRepository.save(order);
    return "Order created successfully";
  }

  public String update(Order order) {
    if (validate(order) != null) {
      return validate(order);
    }
    if (order.getId() == null) {
      return "Order ID is null";
    }
    if (getById(order.getId()).isEmpty()) {
      return "Order not found";
    }
    orderRepository.save(order);
    return "Order with ID=" + order.getId() + " updated successfully";
  }

  private String validate(Order order) {
    if (order == null) {
      return "Order is null";
    }
    if (order.getUser() == null) {
      return "Order user is required";
    }
    if (order.getAddress() == null || order.getAddress().isEmpty()) {
      return "Order address is required";
    }
    if (order.getProducts() == null || order.getProducts().isEmpty()) {
      return "Order products is required";
    }
    return null;
  }

}

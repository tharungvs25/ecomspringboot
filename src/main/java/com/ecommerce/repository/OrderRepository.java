package com.ecommerce.repository;

import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser(User user, Pageable pageable);
    List<Order> findByUserOrderByOrderDateDesc(User user);
    Page<Order> findByStatus(Order.OrderStatus status, Pageable pageable);
    Page<Order> findAllByOrderByOrderDateDesc(Pageable pageable);
}
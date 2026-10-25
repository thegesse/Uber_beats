package com.thegeese.backend.repository;

import com.thegeese.backend.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByBuyerId(Long buyerId, Pageable page);
    Page<Order> findBySellerId(Long sellerId, Pageable page);

}

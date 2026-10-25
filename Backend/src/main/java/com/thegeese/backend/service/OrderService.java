package com.thegeese.backend.service;

import com.thegeese.backend.dto.OrderResponse;
import com.thegeese.backend.model.Order;
import com.thegeese.backend.model.User;
import com.thegeese.backend.repository.OrderRepository;
import com.thegeese.backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    private OrderResponse toOrderDto(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getSeller().getId(),
                order.getBuyer().getId(),
                order.getPrice(),
                order.getCompleted()
        );
    }

    public OrderResponse createOrder(Long buyerId, Long sellerId, Double price) {
        User buyer = userRepository.findById(buyerId).orElseThrow(() -> new RuntimeException("User dont exist"));
        User seller = userRepository.findById(sellerId).orElseThrow(() -> new RuntimeException("User dont exist"));

        Order order = new Order();
        order.setBuyer(buyer);
        order.setSeller(seller);
        order.setPrice(price);
        order.setCompleted(false);

        Order saved = orderRepository.save(order);

        return toOrderDto(saved);
    }

    public Page<OrderResponse> getAllOrders(Pageable page) {
        return orderRepository.findAll(page)
                .map(this::toOrderDto);
    }

    public Page<OrderResponse> getAllBuyerOrder(Pageable page, Long buyerId) {
        return orderRepository.findByBuyerId(buyerId, page)
                .map(this::toOrderDto);
    }

    public Page<OrderResponse> getAllSellerOrder(Pageable page, Long sellerId) {
        return orderRepository.findBySellerId(sellerId, page)
                .map(this::toOrderDto);
    }

    //TODO add a auto complete period after expiration date/completes after order delivered but not confirmed
    //also make status like pending, in progress, delivered, completed so both will have actions

    public OrderResponse completeOrder(Long buyerId, Long orderId) throws AccessDeniedException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("OrderNot found"));

        if(!Objects.equals(buyerId, order.getBuyer().getId())) {
            throw new AccessDeniedException("Only the buyer can complete this order");
        }

        order.setCompleted(true);
        order.setCompletedAt(LocalDateTime.now());

        Order saved = orderRepository.save(order);
        return toOrderDto(saved);
    }

    public OrderResponse cancelOrder(Long buyerId, Long orderId) throws AccessDeniedException{
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("OrderNot found"));

        if(!Objects.equals(buyerId, order.getBuyer().getId())) {
            throw new AccessDeniedException("Only the buyer can cancel this order");
        }

        order.setCompleted(false);
        order.setCompletedAt(LocalDateTime.now());

        Order saved = orderRepository.save(order);
        return toOrderDto(saved);
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        orderRepository.delete(order);
    }
}

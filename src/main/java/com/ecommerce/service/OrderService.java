package com.ecommerce.service;

import com.ecommerce.model.*;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    public Order createOrder(User user, String shippingAddress, String paymentMethod) {
        List<CartItem> cartItems = cartService.getCartItems(user);
        
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        if (!cartService.validateCartItems(user)) {
            throw new RuntimeException("Some items in cart are out of stock");
        }

        BigDecimal totalAmount = cartService.getCartTotal(user);
        Order order = new Order(user, totalAmount, shippingAddress);
        order.setPaymentMethod(paymentMethod);
        order = orderRepository.save(order);

        // Create order items and update stock
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem(order, cartItem.getProduct(), cartItem.getQuantity(), cartItem.getPrice());
            order.getOrderItems().add(orderItem);
            
            // Update product stock
            productService.updateStock(cartItem.getProduct(), cartItem.getQuantity());
        }

        order = orderRepository.save(order);
        
        // Clear cart after successful order
        cartService.clearCart(user);
        
        return order;
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Page<Order> findByUser(User user, Pageable pageable) {
        return orderRepository.findByUser(user, pageable);
    }

    public List<Order> findByUser(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAllByOrderByOrderDateDesc(pageable);
    }

    public Page<Order> findByStatus(Order.OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable);
    }

    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        throw new RuntimeException("Order not found");
    }

    public void cancelOrder(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if (order.getStatus() == Order.OrderStatus.PENDING || order.getStatus() == Order.OrderStatus.CONFIRMED) {
                order.setStatus(Order.OrderStatus.CANCELLED);
                
                // Restore stock for cancelled items
                for (OrderItem orderItem : order.getOrderItems()) {
                    productService.restoreStock(orderItem.getProduct(), orderItem.getQuantity());
                }
                
                orderRepository.save(order);
            } else {
                throw new RuntimeException("Order cannot be cancelled");
            }
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
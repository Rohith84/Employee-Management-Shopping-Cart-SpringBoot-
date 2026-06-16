package com.employee.management.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.employee.management.dto.OrderItemDTO;
import com.employee.management.dto.OrderResponse;
import com.employee.management.model.CartItem;
import com.employee.management.model.Order;
import com.employee.management.model.OrderItem;
import com.employee.management.model.OrderStatus;
import com.employee.management.model.User;
import com.employee.management.repository.OrderRepository;
import com.employee.management.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartItemService cartItemService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(String userId){
        List<CartItem> cartItems=cartItemService.getCart(userId);
        if(cartItems.isEmpty()){
            return Optional.empty();
        }
        //Validate the user
        Optional<User> userOptional=userRepository.findById(Long.valueOf(userId));
        if(userOptional.isEmpty()){
            return Optional.empty();
        }
        User user=userOptional.get();
        //Calculate total price
        BigDecimal totalPrice=cartItems.stream().map(CartItem::getPrice).reduce(BigDecimal.ZERO,BigDecimal::add);
        //Create order
        Order order=new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream()
        .map(item -> new OrderItem(
                null,
                item.getProduct(),
                item.getQuantity(),
                item.getPrice(),
                order
        )).toList();
        order.setItems(orderItems);
        Order savedOrder=orderRepository.save(order);
        //Clear the cart
        cartItemService.clearCart(userId);
        return Optional.of(mapToOrderResponse(savedOrder));
    }
    private OrderResponse mapToOrderResponse(Order savedOrder){
        return new OrderResponse(
            savedOrder.getId(),
            savedOrder.getTotalAmount(),
            savedOrder.getStatus(),
            savedOrder.getItems().stream().map(orderItem->new OrderItemDTO(
                orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
            )).toList(),savedOrder.getCreatedAt()
        );
    }
}

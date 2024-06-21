package edu.miu.minimarket.service.impl;

import edu.miu.minimarket.entity.Buyer;
import edu.miu.minimarket.entity.Order;
import edu.miu.minimarket.entity.OrderItem;
import edu.miu.minimarket.entity.OrderStatus;
import edu.miu.minimarket.entity.dto.response.CountResponse;
import edu.miu.minimarket.entity.dto.response.OrderResponse;
import edu.miu.minimarket.entity.dto.response.OrderItemResponse;
import edu.miu.minimarket.exception.OrderNotFoundException;
import edu.miu.minimarket.repository.OrderRepository;
import edu.miu.minimarket.service.EmailService;
import edu.miu.minimarket.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final EmailService emailService;

    @Override
    public OrderResponse cancelOrder(Long orderId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException("Order not found"));

            order.setOrderStatus(OrderStatus.CANCELLED);
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);

            List<OrderItemResponse> orderItemResponses = order.getOrderItems().stream()
                    .map(item -> new OrderItemResponse(item.getId(), item.getQuantity(), item.getPrice(), item.getProduct().getId()))
                    .collect(Collectors.toList());

            // Send order cancellation email
            Buyer buyer = order.getBuyer();
            StringBuilder emailText = new StringBuilder();
            emailText.append(String.format("Dear %s,\n\nYour order with ID %d has been cancelled successfully.\n\n", buyer.getUser().getFirstname(), order.getId()));
            emailText.append("Order Details:\n");
            for (OrderItem orderItem : order.getOrderItems()) {
                emailText.append(String.format("Product: %s, Quantity: %d, Price: %.2f\n", orderItem.getProduct().getName(), orderItem.getQuantity(), orderItem.getPrice()));
            }
            emailText.append(String.format("\nTotal Price: %.2f\n\n", order.getTotalPrice()));
            emailText.append("If you have any questions, feel free to contact us.\n\nBest regards,\nWaa mini market plc ");

            emailService.sendOrderConfirmationEmail(buyer.getUser().getEmail(), "Order Cancellation Confirmation", emailText.toString());

            return new OrderResponse(order.getId(), order.getOrderStatus().name(), order.getTotalPrice(),
                    order.getShippingAddress(), order.getBillingAddress(), order.getBuyer().getId(), orderItemResponses);
        } catch (Exception e) {
            throw new RuntimeException("Order could not be cancelled! " + e.getMessage(), e);
        }
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, String orderStatus) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException("Order not found"));

            OrderStatus newStatus = OrderStatus.valueOf(orderStatus.toUpperCase());
            order.setOrderStatus(newStatus);
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);

            List<OrderItemResponse> orderItemResponses = order.getOrderItems().stream()
                    .map(item -> new OrderItemResponse(item.getId(), item.getQuantity(), item.getPrice(), item.getProduct().getId()))
                    .collect(Collectors.toList());

            // Send order status update email
            Buyer buyer = order.getBuyer();
            StringBuilder emailText = new StringBuilder();
            emailText.append(String.format("Dear %s,\n\nYour order with ID %d has been updated to '%s' status.\n\n", buyer.getUser().getFirstname(), order.getId(), newStatus.name()));
            emailText.append("Order Details:\n");
            for (OrderItem orderItem : order.getOrderItems()) {
                emailText.append(String.format("Product: %s, Quantity: %d, Price: %.2f\n", orderItem.getProduct().getName(), orderItem.getQuantity(), orderItem.getPrice()));
            }
            emailText.append(String.format("\nTotal Price: %.2f\n\n", order.getTotalPrice()));
            emailText.append("If you have any questions, feel free to contact us.\n\nBest regards,\nWaa mini market plc ");

            emailService.sendOrderConfirmationEmail(buyer.getUser().getEmail(), "Order Status Update", emailText.toString());

            return new OrderResponse(order.getId(), order.getOrderStatus().name(), order.getTotalPrice(),
                    order.getShippingAddress(), order.getBillingAddress(), order.getBuyer().getId(), orderItemResponses);
        } catch (Exception e) {
            throw new RuntimeException("Order status could not be updated! " + e.getMessage(), e);
        }
    }


    @Override
    public List<OrderResponse> getOrdersBySellerId(Long sellerId) {
        return orderRepository.findBySellerId(sellerId).stream()
                .map(order -> new OrderResponse(order.getId(), order.getOrderStatus().name(), order.getTotalPrice(), order.getShippingAddress(), order.getBillingAddress(), order.getBuyer().getId(), order.getOrderItems().stream().map(item -> new OrderItemResponse(item.getId(), item.getQuantity(), item.getPrice(), item.getProduct().getId())).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public CountResponse getTotalOrders() {
        long count = orderRepository.count();
        return new CountResponse(count);
    }

    @Override
    public CountResponse getTotalOrdersBySellerId(Long sellerId) {
        long count = orderRepository.findBySellerId(sellerId).size();
        return new CountResponse(count);
    }
}

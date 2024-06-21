package edu.miu.minimarket.service;

import edu.miu.minimarket.entity.dto.response.CountResponse;
import edu.miu.minimarket.entity.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse cancelOrder(Long orderId);
    OrderResponse updateOrderStatus(Long orderId, String orderStatus);
    List<OrderResponse> getOrdersBySellerId(Long sellerId);
    CountResponse getTotalOrders();
    CountResponse getTotalOrdersBySellerId(Long sellerId);
}

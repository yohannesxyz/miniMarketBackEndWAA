package edu.miu.minimarket.service;

import edu.miu.minimarket.entity.dto.request.*;
import edu.miu.minimarket.entity.dto.response.*;

import java.util.List;

public interface BuyerService {
    BuyerResponse registerBuyer(BuyerRegistrationRequest request);
    BuyerResponse getBuyerById(Long buyerId);
    OrderResponse placeOrder(OrderRequest request);
    CartItemResponse addItemToCart(CartItemRequest request);
    CartItemResponse updateCartItem(Long itemId, UpdateCartItemRequest request);
    void removeItemFromCart(Long itemId);
    ShoppingCartResponse getCartItems(Long buyerId);
    ReviewResponse addReview(ReviewRequest request);
    ReviewResponse updateReview(Long reviewId, ReviewRequest request);
    void deleteReview(Long reviewId);
    List<ReviewResponse> getProductReviews(Long productId);
    CountResponse getTotalProductReviews(Long productId);
    PaymentResponse makePayment(PaymentRequest request);
    List<OrderResponse> getOrderHistory(Long buyerId);
    OrderResponse cancelOrder(Long orderId); // Add this line
}

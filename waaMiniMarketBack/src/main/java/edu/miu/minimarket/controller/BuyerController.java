package edu.miu.minimarket.controller;

import edu.miu.minimarket.entity.dto.request.*;
import edu.miu.minimarket.entity.dto.response.*;
import edu.miu.minimarket.service.BuyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buyers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BuyerController {

    private final BuyerService buyerService;

    @PostMapping("/register")
    public ResponseEntity<BuyerResponse> registerBuyer(@RequestBody BuyerRegistrationRequest request) {
        return ResponseEntity.ok(buyerService.registerBuyer(request));
    }

    @GetMapping("/{buyerId}")
    public ResponseEntity<BuyerResponse> getBuyerById(@PathVariable Long buyerId) {
        return ResponseEntity.ok(buyerService.getBuyerById(buyerId));
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(buyerService.placeOrder(request));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getOrderHistory(@RequestParam Long buyerId) {
        return ResponseEntity.ok(buyerService.getOrderHistory(buyerId));
    }

    @PutMapping("/orders/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(buyerService.cancelOrder(orderId));
    }

    @PostMapping("/cart/items")
    public ResponseEntity<CartItemResponse> addItemToCart(@RequestBody CartItemRequest request) {
        return ResponseEntity.ok(buyerService.addItemToCart(request));
    }

    @PutMapping("/cart/items/{itemId}")
    public ResponseEntity<CartItemResponse> updateCartItem(@PathVariable Long itemId, @RequestBody UpdateCartItemRequest request) {
        return ResponseEntity.ok(buyerService.updateCartItem(itemId, request));
    }

    @DeleteMapping("/cart/items/{itemId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable Long itemId) {
        buyerService.removeItemFromCart(itemId);
        return ResponseEntity.ok("Item removed from cart successfully");
    }

    @GetMapping("/cart")
    public ResponseEntity<ShoppingCartResponse> getCartItems(@RequestParam Long buyerId) {
        return ResponseEntity.ok(buyerService.getCartItems(buyerId));
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponse> addReview(@RequestBody ReviewRequest request) {
        return ResponseEntity.ok(buyerService.addReview(request));
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(buyerService.updateReview(reviewId, request));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        buyerService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted successfully");
    }

    @GetMapping("/reviews/{productId}")
    public ResponseEntity<List<ReviewResponse>> getProductReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(buyerService.getProductReviews(productId));
    }

    @GetMapping("/reviews/{productId}/count")
    public ResponseEntity<CountResponse> getTotalProductReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(buyerService.getTotalProductReviews(productId));
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(buyerService.makePayment(request));
    }
}

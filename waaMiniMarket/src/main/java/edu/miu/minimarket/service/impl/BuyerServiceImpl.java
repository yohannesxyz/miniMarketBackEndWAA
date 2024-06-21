package edu.miu.minimarket.service.impl;

import edu.miu.minimarket.entity.*;
import edu.miu.minimarket.entity.dto.request.*;
import edu.miu.minimarket.entity.dto.response.*;
import edu.miu.minimarket.exception.*;
import edu.miu.minimarket.repository.*;
import edu.miu.minimarket.service.BuyerService;
import edu.miu.minimarket.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BuyerServiceImpl implements BuyerService {

    private final BuyerRepository buyerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ReviewRepository reviewRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final OrderItemRepository orderItemRepository;

    @Override
    public BuyerResponse registerBuyer(BuyerRegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Fetch the BUYER role
        Role buyerRole = roleRepository.findById(3).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(List.of(buyerRole));

        user = userRepository.save(user);

        Buyer buyer = new Buyer();
        buyer.setUser(user);
        buyer.setShippingAddress(request.getShippingAddress());
        buyer.setBillingAddress(request.getBillingAddress());
        buyer = buyerRepository.save(buyer);

        return new BuyerResponse(
                buyer.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getFirstname(),
                user.getLastname(),
                buyer.getShippingAddress(),
                buyer.getBillingAddress()
        );
    }

    @Override
    public BuyerResponse getBuyerById(Long buyerId) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new BuyerNotFoundException("Buyer not found"));

        User user = buyer.getUser();

        return new BuyerResponse(
                buyer.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getFirstname(),
                user.getLastname(),
                buyer.getShippingAddress(),
                buyer.getBillingAddress()
        );
    }

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        try {
            Buyer buyer = buyerRepository.findById(request.getBuyerId())
                    .orElseThrow(() -> new BuyerNotFoundException("Buyer not found"));

            // Validate that the cart exists and is not empty
            ShoppingCart cart = shoppingCartRepository.findByBuyerId(request.getBuyerId())
                    .orElseThrow(() -> new BuyerNotFoundException("Cart not found"));
            if (cart.getCartItems().isEmpty()) {
                throw new IllegalArgumentException("Cart is empty");
            }

            // Validate product existence and stock availability for all cart items
            List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> {
                Product product = productRepository.findById(cartItem.getProduct().getId())
                        .orElseThrow(() -> new ProductNotFoundException("Product not found"));
                if (product.getStockQuantity() < cartItem.getQuantity()) {
                    throw new InsufficientStockException("Insufficient stock for product " + product.getName());
                }
                // Decrease the stock quantity
                product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
                productRepository.save(product);

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(null);
                orderItem.setProduct(product);
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(product.getPrice());
                orderItem.setCreatedAt(LocalDateTime.now());
                orderItem.setUpdatedAt(LocalDateTime.now());
                return orderItem;
            }).collect(Collectors.toList());

            // If all validations pass, create and save the order
            Order order = new Order();
            order.setBuyer(buyer);
            order.setShippingAddress(request.getShippingAddress());
            order.setBillingAddress(request.getBillingAddress());
            order.setOrderStatus(OrderStatus.PENDING);
            order.setCreatedAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());
            order.setTotalPrice(orderItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());

            orderRepository.save(order);

            // Save each order item and update the order reference
            for (OrderItem orderItem : orderItems) {
                orderItem.setOrder(order);
                orderItemRepository.save(orderItem);
            }
            order.getOrderItems().addAll(orderItems);
            orderRepository.save(order);

            // Clear the cart after placing the order
            cart.getCartItems().clear();
            shoppingCartRepository.save(cart);

            List<OrderItemResponse> orderItemResponses = orderItems.stream()
                    .map(item -> new OrderItemResponse(item.getId(), item.getQuantity(), item.getPrice(), item.getProduct().getId()))
                    .collect(Collectors.toList());

            // Send order confirmation email
            StringBuilder emailText = new StringBuilder();
            emailText.append(String.format("Dear %s,\n\nYour order with ID %d has been placed successfully.\n\n", buyer.getUser().getFirstname(), order.getId()));
            emailText.append("Order Details:\n");
            for (OrderItem orderItem : orderItems) {
                emailText.append(String.format("Product: %s, Quantity: %d, Price: %.2f\n", orderItem.getProduct().getName(), orderItem.getQuantity(), orderItem.getPrice()));
            }
            emailText.append(String.format("\nTotal Price: %.2f\n\n", order.getTotalPrice()));
            emailText.append("Thank you for shopping with us!\n\nBest regards,\n Waa mini market plc ");

            emailService.sendOrderConfirmationEmail(buyer.getUser().getEmail(), "Order Confirmation", emailText.toString());

            return new OrderResponse(order.getId(), order.getOrderStatus().name(), order.getTotalPrice(),
                    order.getShippingAddress(), order.getBillingAddress(), order.getBuyer().getId(), orderItemResponses);
        } catch (Exception e) {
            throw new RuntimeException("Order could not be completed! " + e.getMessage(), e);
        }
    }



    @Override
    public CartItemResponse addItemToCart(CartItemRequest request) {
        ShoppingCart cart = shoppingCartRepository.findByBuyerId(request.getBuyerId())
                .orElseGet(() -> {
                    Buyer buyer = buyerRepository.findById(request.getBuyerId())
                            .orElseThrow(() -> new BuyerNotFoundException("Buyer not found"));
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setBuyer(buyer);
                    newCart.setCreatedAt(LocalDateTime.now());
                    newCart.setUpdatedAt(LocalDateTime.now());
                    return shoppingCartRepository.save(newCart);
                });

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        if (product.getStockQuantity() < request.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock for product " + product.getName());
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(request.getQuantity());
        cartItem = cartItemRepository.save(cartItem);

        return new CartItemResponse(cartItem.getId(), cartItem.getQuantity(), cartItem.getProduct().getId(), cartItem.getCart().getBuyer().getId());
    }

    @Override
    public CartItemResponse updateCartItem(Long itemId, UpdateCartItemRequest request) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ProductNotFoundException("Cart item not found"));
        Product product = cartItem.getProduct();
        if (product.getStockQuantity() < request.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock for product " + product.getName());
        }
        cartItem.setQuantity(request.getQuantity());
        cartItem = cartItemRepository.save(cartItem);

        return new CartItemResponse(cartItem.getId(), cartItem.getQuantity(), cartItem.getProduct().getId(), cartItem.getCart().getBuyer().getId());
    }

    @Override
    public void removeItemFromCart(Long itemId) {
        if (!cartItemRepository.existsById(itemId)) {
            throw new ProductNotFoundException("Cart item not found");
        }
        cartItemRepository.deleteById(itemId);
    }

    @Override
    public ShoppingCartResponse getCartItems(Long buyerId) {
        ShoppingCart cart = shoppingCartRepository.findByBuyerId(buyerId)
                .orElseThrow(() -> new BuyerNotFoundException("Cart not found"));
        List<CartItemResponse> cartItems = cart.getCartItems().stream()
                .map(item -> new CartItemResponse(item.getId(), item.getQuantity(), item.getProduct().getId(), cart.getBuyer().getId()))
                .collect(Collectors.toList());

        return new ShoppingCartResponse(cart.getId(), cart.getBuyer().getId(), cartItems);
    }

    @Override
    public ReviewResponse addReview(ReviewRequest request) {
        Review review = new Review();
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setProduct(productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found")));
        review.setBuyer(buyerRepository.findById(request.getBuyerId())
                .orElseThrow(() -> new BuyerNotFoundException("Buyer not found")));
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());
        review = reviewRepository.save(review);

        return new ReviewResponse(review.getId(), review.getRating(), review.getComment(), review.getProduct().getId(), review.getBuyer().getId());
    }

    @Override
    public ReviewResponse updateReview(Long reviewId, ReviewRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found"));
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setUpdatedAt(LocalDateTime.now());
        review = reviewRepository.save(review);

        return new ReviewResponse(review.getId(), review.getRating(), review.getComment(), review.getProduct().getId(), review.getBuyer().getId());
    }

    @Override
    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new ReviewNotFoundException("Review not found");
        }
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<ReviewResponse> getProductReviews(Long productId) {
        return reviewRepository.findByProductId(productId).stream()
                .map(review -> new ReviewResponse(review.getId(), review.getRating(), review.getComment(), review.getProduct().getId(), review.getBuyer().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public CountResponse getTotalProductReviews(Long productId) {
        long count = reviewRepository.countByProductId(productId);
        return new CountResponse(count);
    }

    @Override
    public PaymentResponse makePayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setOrder(orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found")));
        payment.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase()));
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setAmount(request.getAmount());
        payment = paymentRepository.save(payment);

        return new PaymentResponse(payment.getId(), payment.getPaymentMethod().name(), payment.getPaymentStatus().name(), payment.getAmount(), payment.getOrder().getId());
    }

    @Override
    public List<OrderResponse> getOrderHistory(Long buyerId) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new BuyerNotFoundException("Buyer not found"));

        List<Order> orders = orderRepository.findByBuyerId(buyerId);

        return orders.stream()
                .map(order -> {
                    List<OrderItemResponse> orderItemResponses = order.getOrderItems().stream()
                            .map(orderItem -> new OrderItemResponse(
                                    orderItem.getId(),
                                    orderItem.getQuantity(),
                                    orderItem.getPrice(),
                                    orderItem.getProduct().getId()))
                            .collect(Collectors.toList());

                    return new OrderResponse(
                            order.getId(),
                            order.getOrderStatus().name(),
                            order.getTotalPrice(),
                            order.getShippingAddress(),
                            order.getBillingAddress(),
                            order.getBuyer().getId(),
                            orderItemResponses);
                })
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse cancelOrder(Long orderId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException("Order not found"));

            // Check if the order can be cancelled
            if (order.getOrderStatus() == OrderStatus.SHIPPED ||
                    order.getOrderStatus() == OrderStatus.ON_THE_WAY ||
                    order.getOrderStatus() == OrderStatus.DELIVERED) {
                throw new IllegalStateException("Order cannot be cancelled after it has been shipped");
            }

            order.setOrderStatus(OrderStatus.CANCELLED);
            order.setUpdatedAt(LocalDateTime.now());
            order = orderRepository.save(order);

            // Restore the stock quantity of the products
            for (OrderItem item : order.getOrderItems()) {
                Product product = item.getProduct();
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                productRepository.save(product);
            }

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
            emailText.append("If you have any questions, feel free to contact us.\n\nBest regards,\nWaa mini market plc");

            emailService.sendOrderConfirmationEmail(buyer.getUser().getEmail(), "Order Cancellation Confirmation", emailText.toString());

            return new OrderResponse(order.getId(), order.getOrderStatus().name(), order.getTotalPrice(),
                    order.getShippingAddress(), order.getBillingAddress(), order.getBuyer().getId(), orderItemResponses);
        } catch (Exception e) {
            throw new RuntimeException("Order could not be cancelled! " + e.getMessage(), e);
        }
    }

}

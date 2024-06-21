package edu.miu.minimarket.controller;

import edu.miu.minimarket.entity.dto.request.ProductRequest;
import edu.miu.minimarket.entity.dto.request.SellerRegistrationRequest;
import edu.miu.minimarket.entity.dto.request.UpdateOrderStatusRequest;
import edu.miu.minimarket.entity.dto.response.CountResponse;
import edu.miu.minimarket.entity.dto.response.OrderResponse;
import edu.miu.minimarket.entity.dto.response.ProductResponse;
import edu.miu.minimarket.entity.dto.response.SellerResponse;
import edu.miu.minimarket.service.OrderService;
import edu.miu.minimarket.service.SellerService;
import edu.miu.minimarket.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SellerController {
    @Autowired
    private final OrderService orderService;
    private final SellerService sellerService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<SellerResponse>> registerSeller(@RequestBody SellerRegistrationRequest request) {
        try {
            SellerResponse response = sellerService.registerSeller(request);
            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage()));
        }
    }

    @GetMapping("/{sellerId}")
    public ResponseEntity<ApiResponse<SellerResponse>> getSellerById(@PathVariable Long sellerId) {
        try {
            SellerResponse response = sellerService.getSellerById(sellerId);
            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SellerResponse>>> getAllSellers() {
        try {
            List<SellerResponse> response = sellerService.getAllSellers();
            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage()));
        }
    }

    @PostMapping("/products")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductRequest request) {
        try {
            ProductResponse response = sellerService.createProduct(request);
            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage()));
        }
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable Long productId, @RequestBody ProductRequest request) {
        try {
            ProductResponse response = sellerService.updateProduct(productId, request);
            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage()));
        }
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long productId) {
        try {
            sellerService.deleteProduct(productId);
            return ResponseEntity.ok(new ApiResponse<>("Product deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage()));
        }
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getSellerProducts(@RequestParam Long sellerId) {
        try {
            List<ProductResponse> response = sellerService.getSellerProducts(sellerId);
            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage()));
        }
    }

    @PutMapping("/orders/{orderId}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(@PathVariable Long orderId) {
        try {
            OrderResponse response = orderService.cancelOrder(orderId);
            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage()));
        }
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest request) {
        try {
            OrderResponse response = orderService.updateOrderStatus(orderId, request.getOrderStatus());
            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage()));
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getSellerOrders(@RequestParam Long sellerId) {
        try {
            List<OrderResponse> response = orderService.getOrdersBySellerId(sellerId);
            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage()));
        }
    }

    @GetMapping("/orders/count")
    public ResponseEntity<ApiResponse<CountResponse>> getTotalOrders(@RequestParam(required = false) Long sellerId) {
        try {
            CountResponse response;
            if (sellerId != null) {
                response = orderService.getTotalOrdersBySellerId(sellerId);
            } else {
                response = orderService.getTotalOrders();
            }
            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage()));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<CountResponse>> getTotalSellers() {
        try {
            CountResponse response = sellerService.getTotalSellers();
            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage()));
        }
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<ApiResponse<String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = sellerService.uploadImage(file);
            return ResponseEntity.ok(new ApiResponse<>(imageUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage()));
        }
    }
}

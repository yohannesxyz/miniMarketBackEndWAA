package edu.miu.minimarket.controller;

import edu.miu.minimarket.entity.dto.response.ImageResponse;
import edu.miu.minimarket.entity.dto.response.ProductResponse;
import edu.miu.minimarket.entity.dto.response.ReviewResponse;
import edu.miu.minimarket.entity.dto.response.CountResponse;
import edu.miu.minimarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductsController {

    private final ProductService productService;

    @GetMapping("/images")
    public ResponseEntity<List<ImageResponse>> getAllImages() {
        return ResponseEntity.ok(productService.getAllImages());
    }


    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/count")
    public ResponseEntity<CountResponse> getTotalProducts() {
        return ResponseEntity.ok(productService.getTotalProducts());
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<List<ReviewResponse>> getProductReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductReviews(productId));
    }

    @GetMapping("/{productId}/reviews/count")
    public ResponseEntity<CountResponse> getTotalProductReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getTotalProductReviews(productId));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponse>> filterProducts(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(productService.filterProducts(filters));
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponse>> filterReviews(@RequestParam(required = false) Long buyerId,
                                                              @RequestParam(required = false) Long sellerId,
                                                              @RequestParam(required = false) Long productId) {
        return ResponseEntity.ok(productService.filterReviews(buyerId, sellerId, productId));
    }
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable Long reviewId) {
        return ResponseEntity.ok(productService.getReviewById(reviewId));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProductResponse>> getProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getProducts(pageable));
    }
}

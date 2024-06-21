package edu.miu.minimarket.service;

import edu.miu.minimarket.entity.dto.request.ProductRequest;
import edu.miu.minimarket.entity.dto.response.ImageResponse;
import edu.miu.minimarket.entity.dto.response.ProductResponse;
import edu.miu.minimarket.entity.dto.response.ReviewResponse;
import edu.miu.minimarket.entity.dto.response.CountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long productId);
    CountResponse getTotalProducts();
    List<ReviewResponse> getProductReviews(Long productId);
    ReviewResponse getReviewById(Long reviewId);
    CountResponse getTotalProductReviews(Long productId);
    List<ProductResponse> filterProducts(Map<String, String> filters);
    List<ReviewResponse> filterReviews(Long buyerId, Long sellerId, Long productId);
    Page<ProductResponse> getProducts(Pageable pageable);
    List<ImageResponse> getAllImages();
}

package edu.miu.minimarket.service.impl;

import edu.miu.minimarket.entity.Product;
import edu.miu.minimarket.entity.dto.response.ImageResponse;
import edu.miu.minimarket.entity.dto.response.ProductResponse;
import edu.miu.minimarket.entity.dto.response.ReviewResponse;
import edu.miu.minimarket.entity.dto.response.CountResponse;
import edu.miu.minimarket.exception.ProductNotFoundException;
import edu.miu.minimarket.exception.ReviewNotFoundException;
import edu.miu.minimarket.repository.ImageRepository;
import edu.miu.minimarket.repository.ProductRepository;
import edu.miu.minimarket.repository.ReviewRepository;
import edu.miu.minimarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;


    @Override
    public List<ImageResponse> getAllImages() {
        return imageRepository.findAll().stream()
                .map(image -> new ImageResponse(image.getId(), image.getLink()))
                .collect(Collectors.toList());
    }


    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(),
                        product.getPrice(), product.getStockQuantity(), product.getCategory(),
                        product.getSubcategory(), product.getBrand(), product.getSeller().getId(),
                        product.getImage() != null ? product.getImage().getLink() : null))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(),
                product.getPrice(), product.getStockQuantity(), product.getCategory(),
                product.getSubcategory(), product.getBrand(), product.getSeller().getId(),
                product.getImage() != null ? product.getImage().getLink() : null);
    }

    @Override
    public CountResponse getTotalProducts() {
        long count = productRepository.count();
        return new CountResponse(count);
    }

    @Override
    public List<ReviewResponse> getProductReviews(Long productId) {
        return reviewRepository.findByProductId(productId).stream()
                .map(review -> new ReviewResponse(review.getId(), review.getRating(), review.getComment(),
                        review.getProduct().getId(), review.getBuyer().getId()))
                .collect(Collectors.toList());
    }
    @Override
    public ReviewResponse getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(review -> new ReviewResponse(review.getId(), review.getRating(), review.getComment(),
                        review.getProduct().getId(), review.getBuyer().getId()))
                .orElseThrow(() -> new ReviewNotFoundException("Review not found"));
    }

    @Override
    public CountResponse getTotalProductReviews(Long productId) {
        long count = reviewRepository.countByProductId(productId);
        return new CountResponse(count);
    }

    @Override
    public List<ProductResponse> filterProducts(Map<String, String> filters) {
        String category = filters.get("category");
        String subcategory = filters.get("subcategory");
        String brand = filters.get("brand");
        Double minPrice = filters.get("minPrice") != null ? Double.parseDouble(filters.get("minPrice")) : null;
        Double maxPrice = filters.get("maxPrice") != null ? Double.parseDouble(filters.get("maxPrice")) : null;
        Integer minRating = filters.get("minRating") != null ? Integer.parseInt(filters.get("minRating")) : null;
        Boolean available = filters.get("available") != null ? Boolean.parseBoolean(filters.get("available")) : null;

        List<Product> products = productRepository.filterProducts(category, subcategory, brand, minPrice, maxPrice, minRating, available);

        return products.stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(),
                        product.getPrice(), product.getStockQuantity(), product.getCategory(),
                        product.getSubcategory(), product.getBrand(), product.getSeller().getId(),
                        product.getImage() != null ? product.getImage().getLink() : null))
                .collect(Collectors.toList());
    }


    @Override
    public List<ReviewResponse> filterReviews(Long buyerId, Long sellerId, Long productId) {
        return reviewRepository.findFilteredReviews(buyerId, sellerId, productId).stream()
                .map(review -> new ReviewResponse(review.getId(), review.getRating(), review.getComment(),
                        review.getProduct().getId(), review.getBuyer().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponse> getProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductResponse> productResponses = productPage.getContent().stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(),
                        product.getPrice(), product.getStockQuantity(), product.getCategory(),
                        product.getSubcategory(), product.getBrand(), product.getSeller().getId(),
                        product.getImage() != null ? product.getImage().getLink() : null))
                .collect(Collectors.toList());
        return new PageImpl<>(productResponses, pageable, productPage.getTotalElements());
    }
}

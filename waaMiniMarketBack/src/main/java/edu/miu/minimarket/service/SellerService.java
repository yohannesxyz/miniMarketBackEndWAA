package edu.miu.minimarket.service;

import edu.miu.minimarket.entity.dto.request.ProductRequest;
import edu.miu.minimarket.entity.dto.request.SellerRegistrationRequest;
import edu.miu.minimarket.entity.dto.response.CountResponse;
import edu.miu.minimarket.entity.dto.response.ProductResponse;
import edu.miu.minimarket.entity.dto.response.SellerResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SellerService {
    SellerResponse registerSeller(SellerRegistrationRequest request);
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(Long productId, ProductRequest request);
    void deleteProduct(Long productId);
    List<ProductResponse> getSellerProducts(Long sellerId);
    List<SellerResponse> getAllSellers();
    SellerResponse getSellerById(Long sellerId);
    CountResponse getTotalSellers();
    String uploadImage(MultipartFile file);  // Method for uploading images
}

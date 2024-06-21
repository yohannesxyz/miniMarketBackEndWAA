package edu.miu.minimarket.service.impl;

import edu.miu.minimarket.entity.*;
import edu.miu.minimarket.entity.dto.request.ProductRequest;
import edu.miu.minimarket.entity.dto.request.SellerRegistrationRequest;
import edu.miu.minimarket.entity.dto.response.CountResponse;
import edu.miu.minimarket.entity.dto.response.ProductResponse;
import edu.miu.minimarket.entity.dto.response.SellerResponse;
import edu.miu.minimarket.exception.EmailAlreadyExistsException;
import edu.miu.minimarket.exception.ProductNotFoundException;
import edu.miu.minimarket.exception.SellerNotApprovedException;
import edu.miu.minimarket.exception.SellerNotFoundException;
import edu.miu.minimarket.repository.*;
import edu.miu.minimarket.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public SellerResponse registerSeller(SellerRegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encrypt the password
        user.setPhoneNumber(request.getPhoneNumber());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Fetch the SELLER role
        Role sellerRole = roleRepository.findById(2).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(List.of(sellerRole));

        user = userRepository.save(user);

        Seller seller = new Seller();
        seller.setUser(user);
        seller.setApproved(false);
        seller.setStoreName(request.getStoreName());
        seller.setStoreDescription(request.getStoreDescription());
        seller = sellerRepository.save(seller);

        return new SellerResponse(
                seller.getId(),
                seller.getStoreName(),
                seller.getStoreDescription(),
                seller.getApproved(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getFirstname(),
                user.getLastname(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }


    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Seller seller = sellerRepository.findById(request.getSellerId())
                .orElseThrow(() -> new SellerNotFoundException("Seller not found"));

        if (!seller.getApproved()) {
            throw new SellerNotApprovedException("Seller not approved");
        }

        Image image = imageRepository.findById(request.getImageId())
                .orElseThrow(() -> new IllegalArgumentException("Image not found"));

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        product.setSubcategory(request.getSubcategory());
        product.setBrand(request.getBrand());
        product.setSeller(seller);
        product.setImage(image);
        product = productRepository.save(product);

        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getStockQuantity(), product.getCategory(), product.getSubcategory(), product.getBrand(), product.getSeller().getId(), product.getImage() != null ? product.getImage().getLink() : null);
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Seller seller = product.getSeller();
        if (!seller.getApproved()) {
            throw new SellerNotApprovedException("Seller not approved");
        }

        Image image = imageRepository.findById(request.getImageId())
                .orElseThrow(() -> new IllegalArgumentException("Image not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        product.setSubcategory(request.getSubcategory());
        product.setBrand(request.getBrand());
        product.setImage(image);
        product = productRepository.save(product);

        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getStockQuantity(), product.getCategory(), product.getSubcategory(), product.getBrand(), product.getSeller().getId(), product.getImage() != null ? product.getImage().getLink() : null);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product ID " + productId + " doesn't exist"));

        Seller seller = product.getSeller();
        if (!seller.getApproved()) {
            throw new SellerNotApprovedException("Seller not approved");
        }

        // Check if the product has been purchased
        if (orderItemRepository.existsByProductId(productId)) {
            throw new IllegalStateException("Product has been purchased and cannot be deleted");
        }

        productRepository.deleteById(productId);
    }

    @Override
    public List<ProductResponse> getSellerProducts(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller ID " + sellerId + " doesn't exist"));

        if (!seller.getApproved()) {
            throw new SellerNotApprovedException("Seller not approved");
        }

        return productRepository.findBySellerId(sellerId).stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getStockQuantity(), product.getCategory(), product.getSubcategory(), product.getBrand(), product.getSeller().getId(), product.getImage() != null ? product.getImage().getLink() : null))
                .collect(Collectors.toList());
    }

    @Override
    public List<SellerResponse> getAllSellers() {
        return sellerRepository.findAll().stream()
                .map(seller -> {
                    User user = seller.getUser();
                    return new SellerResponse(
                            seller.getId(),
                            seller.getStoreName(),
                            seller.getStoreDescription(),
                            seller.getApproved(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getPhoneNumber(),
                            user.getFirstname(),
                            user.getLastname(),
                            user.getCreatedAt(),
                            user.getUpdatedAt()
                    );
                })
                .collect(Collectors.toList());
    }


    @Override
    public SellerResponse getSellerById(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller ID " + sellerId + " doesn't exist"));
        User user = seller.getUser();

        return new SellerResponse(
                seller.getId(),
                seller.getStoreName(),
                seller.getStoreDescription(),
                seller.getApproved(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getFirstname(),
                user.getLastname(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    @Override
    public CountResponse getTotalSellers() {
        long count = sellerRepository.count();
        return new CountResponse(count);
    }

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            // Save the file
            Path uploadDir = Paths.get("uploads");
            Files.createDirectories(uploadDir);
            Path filePath = uploadDir.resolve(file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            // Save the image record in the database
            Image image = new Image();
            image.setLink(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(file.getOriginalFilename())
                    .toUriString());
            imageRepository.save(image);

            return image.getLink();
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }
}

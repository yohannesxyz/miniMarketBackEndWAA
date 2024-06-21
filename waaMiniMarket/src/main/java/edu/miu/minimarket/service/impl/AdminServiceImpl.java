package edu.miu.minimarket.service.impl;

import edu.miu.minimarket.entity.Seller;
import edu.miu.minimarket.entity.User;
import edu.miu.minimarket.entity.dto.response.SellerResponse;
import edu.miu.minimarket.exception.ReviewNotFoundException;
import edu.miu.minimarket.exception.SellerNotFoundException;
import edu.miu.minimarket.repository.ReviewRepository;
import edu.miu.minimarket.repository.SellerRepository;
import edu.miu.minimarket.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final SellerRepository sellerRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public SellerResponse approveSeller(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found"));

        seller.setApproved(true);
        seller = sellerRepository.save(seller);

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
    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new ReviewNotFoundException("Review not found");
        }
        reviewRepository.deleteById(reviewId);
    }
}

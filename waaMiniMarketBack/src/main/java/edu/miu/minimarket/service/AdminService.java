package edu.miu.minimarket.service;

import edu.miu.minimarket.entity.dto.response.SellerResponse;

public interface AdminService {
    SellerResponse approveSeller(Long sellerId);
    void deleteReview(Long reviewId);
}

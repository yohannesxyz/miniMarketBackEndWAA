package edu.miu.minimarket.controller;

import edu.miu.minimarket.entity.dto.response.CountResponse;
import edu.miu.minimarket.entity.dto.response.SellerResponse;
import edu.miu.minimarket.service.AdminService;
import edu.miu.minimarket.service.SellerService;
import edu.miu.minimarket.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    private final AdminService adminService;
    private final SellerService sellerService;
    @PostMapping("/approve-seller/{sellerId}")
    public ResponseEntity<SellerResponse> approveSeller(@PathVariable Long sellerId) {
        SellerResponse response = adminService.approveSeller(sellerId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        adminService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted successfully");
    }

}

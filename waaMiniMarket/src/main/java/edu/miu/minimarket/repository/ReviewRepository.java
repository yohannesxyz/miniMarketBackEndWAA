package edu.miu.minimarket.repository;

import edu.miu.minimarket.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);
    long countByProductId(Long productId);
    @Query("SELECT r FROM Review r WHERE (:buyerId IS NULL OR r.buyer.id = :buyerId) " +
            "AND (:sellerId IS NULL OR r.product.seller.id = :sellerId) " +
            "AND (:productId IS NULL OR r.product.id = :productId)")
    List<Review> findFilteredReviews(@Param("buyerId") Long buyerId,
                                     @Param("sellerId") Long sellerId,
                                     @Param("productId") Long productId);
}

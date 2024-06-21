package edu.miu.minimarket.repository;

import edu.miu.minimarket.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySellerId(Long sellerId);

    @Query("SELECT p FROM Product p WHERE (:category IS NULL OR p.category = :category) " +
            "AND (:subcategory IS NULL OR p.subcategory = :subcategory) " +
            "AND (:brand IS NULL OR p.brand = :brand) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice)" +
            "AND (:minRating IS NULL OR p.rating >= :minRating) " +
            "AND (:available IS NULL OR p.stockQuantity > 0)")
    List<Product> filterProducts(
            @Param("category") String category,
            @Param("subcategory") String subcategory,
            @Param("brand") String brand,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minRating") Integer minRating,
            @Param("available") Boolean available);

    // Method to support pagination
    Page<Product> findAll(Pageable pageable);
}

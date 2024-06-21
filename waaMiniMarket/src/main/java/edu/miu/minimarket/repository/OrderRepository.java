package edu.miu.minimarket.repository;

import edu.miu.minimarket.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o JOIN o.orderItems oi JOIN oi.product p WHERE p.seller.id = :sellerId")
    List<Order> findBySellerId(@Param("sellerId") Long sellerId);

    List<Order> findByBuyerId(Long buyerId);
}

package edu.miu.minimarket.repository;

import edu.miu.minimarket.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
   Optional<ShoppingCart> findByBuyerId(Long buyerId);
}

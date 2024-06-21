package edu.miu.minimarket.repository;

import edu.miu.minimarket.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}

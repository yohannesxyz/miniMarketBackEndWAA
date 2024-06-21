package edu.miu.minimarket.repository;

import edu.miu.minimarket.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}

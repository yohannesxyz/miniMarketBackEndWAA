package edu.miu.minimarket.repository;

import edu.miu.minimarket.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
}

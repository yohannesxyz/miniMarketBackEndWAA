package edu.miu.minimarket.repository;

import edu.miu.minimarket.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}

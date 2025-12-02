package com.example.order_service.repository;

import com.example.order_service.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByRoleName(String roleName);
}

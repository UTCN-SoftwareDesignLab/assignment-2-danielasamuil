package com.example.bookstore.frontoffice.user.repository;

import com.example.bookstore.frontoffice.user.model.ERole;
import com.example.bookstore.frontoffice.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole role);
}

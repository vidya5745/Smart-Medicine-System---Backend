package com.smartmedicine.smartmedicine.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmedicine.smartmedicine.model.user;

public interface UserRepository extends JpaRepository<user, Long>{

    Optional<user> findByEmail(String email);

    boolean existsByEmail(String email);

    List<user> findByRole(String role);
}
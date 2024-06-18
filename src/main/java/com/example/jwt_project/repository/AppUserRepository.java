package com.example.jwt_project.repository;

import com.example.jwt_project.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
   Optional<AppUser> findByUsername(String username);
   Boolean existsByUsername(String username);
}

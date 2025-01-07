package com.example.demo.repository;

import com.example.demo.model.Role;
import com.example.demo.model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    // Μέθοδος για εύρεση ρόλου με βάση το όνομα του ρόλου (ERole)
    Optional<Role> findByName(ERole name);
}

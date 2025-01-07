package com.example.demo.repository;

import com.example.demo.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.UserRoleKey;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleKey> {

    // Πρόσθεσε αν χρειαστεί προσαρμοσμένα queries εδώ
}

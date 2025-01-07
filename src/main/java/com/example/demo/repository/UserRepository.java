package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Μέθοδος για εύρεση χρήστη με βάση το username
    Optional<User> findByUsername(String username);

    // Μέθοδος για έλεγχο αν υπάρχει χρήστης με συγκεκριμένο email
    Boolean existsByEmail(String email);

    // Μέθοδος για έλεγχο αν υπάρχει χρήστης με συγκεκριμένο username
    Boolean existsByUsername(String username);
}

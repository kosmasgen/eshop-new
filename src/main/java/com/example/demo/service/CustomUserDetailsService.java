package com.example.demo.service;

import com.example.demo.mapper.CustomUserDetailsMapper;
import com.example.demo.model.User;
import com.example.demo.security.AESEncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Υλοποίηση του UserDetailsService για το Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Αναζήτηση χρήστη μέσω UserService
        User user = userService.getUserByUsername(username);

        // Αποκρυπτογράφηση του κωδικού πρόσβασης
        String decryptedPassword = decryptPassword(user.getPassword());

        // Χρήση του CustomUserDetailsMapper για τη δημιουργία του UserDetails
        return CustomUserDetailsMapper.toCustomUserDetails(user, decryptedPassword);
    }

    /**
     * Μέθοδος αποκρυπτογράφησης του κωδικού πρόσβασης.
     */
    private String decryptPassword(String encryptedPassword) {
        try {
            return AESEncryptionUtil.decrypt(encryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Σφάλμα κατά την αποκρυπτογράφηση του κωδικού: " + e.getMessage(), e);
        }
    }
}


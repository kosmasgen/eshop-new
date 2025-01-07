package com.example.demo.security;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.AESEncryptionUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Κλάση για τη φόρτωση ρυθμίσεων JWT από το application.properties.
 */
@Component
@ConfigurationProperties(prefix = "bezkoder.app")
@Getter
@Setter
public class JwtProperties {

    private String jwtSecret;
    private int jwtExpirationMs;

    /**
     * CustomUserDetailsService για τη φόρτωση χρηστών από τη βάση δεδομένων.
     */
    @Service
    public static class CustomUserDetailsService implements UserDetailsService {

        private final UserRepository userRepository;

        @Autowired
        public CustomUserDetailsService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // Αναζήτηση χρήστη από τη βάση δεδομένων
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Ο χρήστης δεν βρέθηκε: " + username));

            String decryptedPassword;
            try {
                // Αποκρυπτογράφηση του password που είναι κρυπτογραφημένο
                decryptedPassword = AESEncryptionUtil.decrypt(user.getPassword());
            } catch (Exception e) {
                System.err.println("Σφάλμα κατά την αποκρυπτογράφηση του κωδικού: " + e.getMessage());
                throw new RuntimeException("Προέκυψε σφάλμα κατά την αποκρυπτογράφηση του κωδικού.", e);
            }

            // Επιστροφή UserDetails με το αποκρυπτογραφημένο password
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    decryptedPassword, // Χρησιμοποιούμε το αποκρυπτογραφημένο password
                    user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                            .collect(Collectors.toList())
            );
        }
    }
}

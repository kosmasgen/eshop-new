package com.example.demo.mapper;

import com.example.demo.model.User;
import com.example.demo.service.CustomUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;

public class CustomUserDetailsMapper {

    /**
     * Μετατρέπει μια οντότητα User σε CustomUserDetails.
     *
     * @param user               Η οντότητα User.
     * @param decryptedPassword  Ο αποκρυπτογραφημένος κωδικός.
     * @return Το CustomUserDetails.
     */
    public static CustomUserDetails toCustomUserDetails(User user, String decryptedPassword) {
        return new CustomUserDetails(
                user.getUsername(),
                decryptedPassword,
                user.getEmail(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList())
        );
    }
}

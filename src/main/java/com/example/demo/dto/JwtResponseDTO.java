package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO για την απόκριση JWT.
 * Περιέχει το JWT token που επιστρέφεται μετά από επιτυχημένη αυθεντικοποίηση.
 */
@Getter
@Setter
@NoArgsConstructor
public class JwtResponseDTO {

    /**
     * Το JWT token που δημιουργείται για τον χρήστη.
     */
    private String token;

    /**
     * Κατασκευαστής που αρχικοποιεί το token.
     *
     * @param token Το JWT token.
     */
    public JwtResponseDTO(String token) {
        this.token = token;
    }
}

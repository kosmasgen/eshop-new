package com.example.demo.serviceTest;
import com.example.demo.security.jwt.JwtTokenUtil;
import io.jsonwebtoken.Claims;


public class JwtTest {
    public static void main(String[] args) {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

        // Δημιουργία token
        String username = "testuser";
        String email = "testuser@example.com";
        String token = jwtTokenUtil.generateToken(username, email);
        System.out.println("Generated Token: " + token);

        // Επαλήθευση token
        boolean isValid = jwtTokenUtil.validateToken(token);
        System.out.println("Is Token Valid? " + isValid);

        // Εξαγωγή claims
        try {
            Claims claims = jwtTokenUtil.extractClaims(token);
            System.out.println("Token Claims: " + claims);
        } catch (Exception e) {
            System.out.println("Invalid Token: " + e.getMessage());
        }
    }
}

package com.example.demo.controller;

import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtTokenUtil;
import com.example.demo.security.AESEncryptionUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final MessageSource messageSource;

    @Autowired
    public AuthController(UserRepository userRepository, JwtTokenUtil jwtTokenUtil, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.messageSource = messageSource;
    }

    /**
     * Endpoint για σύνδεση χρήστη.
     *
     * @param loginRequest δεδομένα σύνδεσης.
     * @return JWT token εάν τα στοιχεία είναι σωστά.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            String username = loginRequest.getUsername();
            String plainPassword = loginRequest.getPassword();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            messageSource.getMessage("validation.user.notfound",
                                    new Object[]{username}, LocaleContextHolder.getLocale())));

            String decryptedPassword = AESEncryptionUtil.decrypt(user.getPassword());

            if (plainPassword.equals(decryptedPassword)) {
                String token = jwtTokenUtil.generateToken(username, user.getEmail());
                return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        messageSource.getMessage("validation.login.invalid", null, LocaleContextHolder.getLocale()));
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    messageSource.getMessage("validation.login.error", null, LocaleContextHolder.getLocale()));
        }
    }

    /**
     * Endpoint για εγγραφή χρήστη.
     *
     * @param registerRequest δεδομένα εγγραφής.
     * @return μήνυμα επιτυχίας ή αποτυχίας.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        try {
            String encryptedPassword = AESEncryptionUtil.encrypt(registerRequest.getPassword());

            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(encryptedPassword);

            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    messageSource.getMessage("registration.success", null, LocaleContextHolder.getLocale()));
        } catch (DataIntegrityViolationException e) {
            String errorMessage;
            if (e.getMessage().contains("users.username")) {
                errorMessage = messageSource.getMessage("validation.username.unique", null, LocaleContextHolder.getLocale());
            } else if (e.getMessage().contains("users.email")) {
                errorMessage = messageSource.getMessage("validation.email.unique", null, LocaleContextHolder.getLocale());
            } else {
                errorMessage = messageSource.getMessage("validation.database.error", null, LocaleContextHolder.getLocale());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    messageSource.getMessage("registration.error", null, LocaleContextHolder.getLocale()));
        }
    }
}

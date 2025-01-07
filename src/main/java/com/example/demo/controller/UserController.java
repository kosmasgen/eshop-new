package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller για τη διαχείριση χρηστών.
 * Παρέχει endpoints για CRUD λειτουργίες στο σύστημα χρηστών.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Δημιουργεί ένα νέο UserController με τις απαραίτητες εξαρτήσεις.
     *
     * @param userRepository το repository για τη διαχείριση των χρηστών.
     * @param userMapper     ο mapper για τη μετατροπή μεταξύ User και UserDTO.
     */
    @Autowired
    public UserController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Επιστρέφει όλους τους χρήστες του συστήματος.
     *
     * @return λίστα με όλους τους χρήστες ως UserDTO.
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userRepository.findAll().stream()
                .map(user -> userMapper.toDTO(user, false))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    /**
     * Επιστρέφει έναν χρήστη βάσει του ID του.
     *
     * @param id το μοναδικό αναγνωριστικό του χρήστη.
     * @return το UserDTO του χρήστη αν βρεθεί, αλλιώς 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(userMapper.toDTO(user, false)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Δημιουργεί έναν νέο χρήστη.
     *
     * @param userDTO τα δεδομένα του χρήστη που θα δημιουργηθεί.
     * @return το UserDTO του δημιουργημένου χρήστη.
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDTO(savedUser, false));
    }

    /**
     * Ενημερώνει έναν υπάρχοντα χρήστη βάσει του ID του.
     *
     * @param id      το μοναδικό αναγνωριστικό του χρήστη που θα ενημερωθεί.
     * @param userDTO τα νέα δεδομένα για τον χρήστη.
     * @return το ενημερωμένο UserDTO αν βρεθεί ο χρήστης, αλλιώς 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @Valid @RequestBody UserDTO userDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    userMapper.updateEntityFromDTO(userDTO, existingUser);
                    User updatedUser = userRepository.save(existingUser);
                    return ResponseEntity.ok(userMapper.toDTO(updatedUser, false));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Διαγράφει έναν χρήστη βάσει του ID του.
     *
     * @param id το μοναδικό αναγνωριστικό του χρήστη που θα διαγραφεί.
     * @return μήνυμα επιτυχίας αν διαγραφεί ο χρήστης, αλλιώς 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("Ο χρήστης διαγράφηκε επιτυχώς.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ο χρήστης με ID " + id + " δεν βρέθηκε.");
        }
    }
}

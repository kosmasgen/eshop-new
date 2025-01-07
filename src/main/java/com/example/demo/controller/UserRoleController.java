package com.example.demo.controller;

import com.example.demo.dto.UserRoleDTO;
import com.example.demo.model.UserRoleKey;
import com.example.demo.service.UserRoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {

    private final UserRoleService userRoleService;

    @Autowired
    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * Ανάθεση Ρόλου σε Χρήστη.
     *
     * @param userRoleDTO Το DTO της σχέσης χρήστη-ρόλου.
     * @return Το ανατεθειμένο UserRoleDTO.
     */
    @PostMapping
    public ResponseEntity<UserRoleDTO> assignRoleToUser(@Valid @RequestBody UserRoleDTO userRoleDTO) {
        UserRoleDTO assignedUserRole = userRoleService.assignRoleToUser(userRoleDTO);
        return ResponseEntity.ok(assignedUserRole);
    }

    /**
     * Ανάκτηση Όλων των Σχέσεων Χρήστη-Ρόλου.
     *
     * @return Λίστα με όλα τα UserRoleDTO.
     */
    @GetMapping
    public ResponseEntity<List<UserRoleDTO>> getAllUserRoles() {
        List<UserRoleDTO> userRoles = userRoleService.getAllUserRoles();
        return ResponseEntity.ok(userRoles);
    }

    /**
     * Ανάκτηση Σχέσης Χρήστη-Ρόλου με Βάση το ID.
     *
     * @param userId Το ID του χρήστη.
     * @param roleId Το ID του ρόλου.
     * @return Το UserRoleDTO που αντιστοιχεί στη σχέση.
     */
    @GetMapping("/{userId}/{roleId}")
    public ResponseEntity<UserRoleDTO> getUserRoleById(
            @PathVariable Integer userId,
            @PathVariable Integer roleId) {
        UserRoleKey userRoleKey = new UserRoleKey(userId, roleId);
        UserRoleDTO userRole = userRoleService.getUserRoleById(userRoleKey);
        return ResponseEntity.ok(userRole);
    }

    /**
     * Διαγραφή Σχέσης Χρήστη-Ρόλου.
     *
     * @param userId Το ID του χρήστη.
     * @param roleId Το ID του ρόλου.
     * @return HTTP Status 204 αν η διαγραφή ήταν επιτυχής.
     */
    @DeleteMapping("/{userId}/{roleId}")
    public ResponseEntity<Void> deleteUserRole(
            @PathVariable Integer userId,
            @PathVariable Integer roleId) {
        UserRoleKey userRoleKey = new UserRoleKey(userId, roleId);
        userRoleService.deleteUserRole(userRoleKey);
        return ResponseEntity.noContent().build();
    }
}

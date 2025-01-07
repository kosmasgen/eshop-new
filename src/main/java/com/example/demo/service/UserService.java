package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

import java.util.List;
import java.util.Set;

/**
 * Διεπαφή για τη διαχείριση των χρηστών.
 */
public interface UserService {

    UserDTO createUser(UserDTO userDTO); // Δημιουργία Χρήστη

    List<UserDTO> getAllUsers(); // Ανάκτηση Όλων των Χρηστών

    UserDTO getUserById(Integer id); // Ανάκτηση Χρήστη με Βάση το ID

    UserDTO updateUser(Integer id, UserDTO userDTO); // Ενημέρωση Χρήστη

    void deleteUser(Integer id); // Διαγραφή Χρήστη

    UserDTO updateUserRoles(Integer userId, Set<String> roles); // Ενημέρωση Ρόλων Χρήστη

    User getUserByUsername(String username); // Επιστροφή Χρήστη με βάση το Username

    User saveUser(User user); // Αποθήκευση Χρήστη
}

package com.example.demo.service;

import com.example.demo.dto.UserRoleDTO;
import com.example.demo.model.UserRoleKey;
import java.util.List;

public interface UserRoleService {

    UserRoleDTO assignRoleToUser(UserRoleDTO userRoleDTO); // Ανάθεση Ρόλου σε Χρήστη

    List<UserRoleDTO> getAllUserRoles(); // Ανάκτηση Όλων των Σχέσεων Χρήστη-Ρόλου

    UserRoleDTO getUserRoleById(UserRoleKey userRoleKey); // Ανάκτηση Σχέσης Χρήστη-Ρόλου με Βάση το ID

    void deleteUserRole(UserRoleKey userRoleKey); // Διαγραφή Σχέσης Χρήστη-Ρόλου
}

package com.example.demo.service;

import com.example.demo.dto.RoleDTO;
import com.example.demo.model.Role;
import com.example.demo.model.ERole;

import java.util.List;

public interface RoleService {

    RoleDTO createRole(RoleDTO roleDTO); // Δημιουργία Ρόλου

    List<RoleDTO> getAllRoles(); // Ανάκτηση Όλων των Ρόλων

    RoleDTO getRoleById(Integer id); // Ανάκτηση Ρόλου με Βάση το ID

    RoleDTO updateRole(Integer id, RoleDTO roleDTO); // Ενημέρωση Ρόλου

    void deleteRole(Integer id); // Διαγραφή Ρόλου

    Role getRoleByName(ERole roleName);

}

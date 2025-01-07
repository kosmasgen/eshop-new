package com.example.demo.service;

import com.example.demo.dto.UserRoleDTO;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserRoleMapper;
import com.example.demo.model.ERole;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.model.UserRoleKey;
import com.example.demo.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Υλοποίηση του UserRoleService για διαχείριση των ρόλων χρηστών.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserService userService, RoleService roleService) {
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public UserRoleDTO assignRoleToUser(UserRoleDTO userRoleDTO) {
        if (userRoleDTO.getRoleName() == null || userRoleDTO.getRoleName().isBlank()) {
            throw new IllegalArgumentException("Το όνομα του ρόλου δεν μπορεί να είναι null ή κενό.");
        }

        // Έλεγχος αν το username είναι έγκυρο
        User user = userService.getUserByUsername(userRoleDTO.getUsername());

        // Έλεγχος αν το roleName είναι έγκυρο
        ERole roleName = ERole.valueOf(userRoleDTO.getRoleName().toUpperCase());
        Role role = roleService.getRoleByName(roleName);

        // Δημιουργία σχέσης χρήστη-ρόλου
        UserRole userRole = new UserRole(user, role);
        UserRole savedUserRole = userRoleRepository.save(userRole);

        return UserRoleMapper.toDTO(savedUserRole);
    }

    @Override
    public List<UserRoleDTO> getAllUserRoles() {
        return userRoleRepository.findAll().stream()
                .map(UserRoleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserRoleDTO getUserRoleById(UserRoleKey userRoleKey) {
        UserRole userRole = userRoleRepository.findById(userRoleKey)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.USER_ROLE_NOT_FOUND, userRoleKey));
        return UserRoleMapper.toDTO(userRole);
    }

    @Override
    public void deleteUserRole(UserRoleKey userRoleKey) {
        UserRole userRole = userRoleRepository.findById(userRoleKey)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.USER_ROLE_NOT_FOUND, userRoleKey));
        userRoleRepository.delete(userRole);
    }
}

package com.example.demo.mapper;

import com.example.demo.dto.UserRoleDTO;
import com.example.demo.model.UserRole;
import com.example.demo.model.UserRoleKey;
import com.example.demo.model.User;
import com.example.demo.model.Role;

/**
 * Mapper για τη χαρτογράφηση μεταξύ UserRole και UserRoleDTO.
 */
public class UserRoleMapper {

    public static UserRoleDTO toDTO(UserRole userRole) {
        if (userRole == null || userRole.getId() == null) {
            throw new IllegalArgumentException("Η οντότητα UserRole ή το ID της είναι null.");
        }

        UserRoleDTO dto = new UserRoleDTO();
        dto.setUserId(userRole.getId().getUserId());
        dto.setRoleId(userRole.getId().getRoleId());
        dto.setUsername(userRole.getUser().getUsername());
        dto.setRoleName(userRole.getRole().getName().name());
        return dto;
    }

    public static UserRole toEntity(UserRoleDTO dto, User user, Role role) {
        if (dto == null || user == null || role == null) {
            throw new IllegalArgumentException("Το UserRoleDTO, ο User ή ο Role δεν μπορούν να είναι null.");
        }

        UserRole userRole = new UserRole();
        UserRoleKey key = new UserRoleKey(dto.getUserId(), dto.getRoleId());
        userRole.setId(key);
        userRole.setUser(user);
        userRole.setRole(role);
        return userRole;
    }
}

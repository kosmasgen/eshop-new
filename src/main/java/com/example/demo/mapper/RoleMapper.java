package com.example.demo.mapper;

import com.example.demo.dto.RoleDTO;
import com.example.demo.model.Role;
import com.example.demo.model.ERole;



public class RoleMapper {

    public static RoleDTO toDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName().name());
        return roleDTO;
    }

    public static Role toEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(Enum.valueOf(ERole.class, roleDTO.getName())); // Μετατροπή από String σε ERole
        return role;
    }
}

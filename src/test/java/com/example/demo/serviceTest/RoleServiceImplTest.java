package com.example.demo.serviceTest;

import com.example.demo.dto.RoleDTO;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.model.ERole;
import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRole_ShouldReturnCreatedRole() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ROLE_USER");

        Role role = new Role();
        role.setName(ERole.ROLE_USER);

        Role savedRole = new Role();
        savedRole.setId(1);
        savedRole.setName(ERole.ROLE_USER);

        when(roleRepository.save(any(Role.class))).thenReturn(savedRole);

        RoleDTO result = roleService.createRole(roleDTO);

        assertNotNull(result);
        assertEquals("ROLE_USER", result.getName());
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void getAllRoles_ShouldReturnAllRoles() {
        Role role1 = new Role();
        role1.setId(1);
        role1.setName(ERole.ROLE_USER);

        Role role2 = new Role();
        role2.setId(2);
        role2.setName(ERole.ROLE_ADMIN);

        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        List<RoleDTO> result = roleService.getAllRoles();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("ROLE_USER", result.get(0).getName());
        assertEquals("ROLE_ADMIN", result.get(1).getName());
        verify(roleRepository).findAll();
    }

    @Test
    void getRoleById_ShouldReturnRole() {
        int roleId = 1;

        Role role = new Role();
        role.setId(roleId);
        role.setName(ERole.ROLE_USER);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        RoleDTO result = roleService.getRoleById(roleId);

        assertNotNull(result);
        assertEquals("ROLE_USER", result.getName());
        verify(roleRepository).findById(roleId);
    }

    @Test
    void getRoleById_ShouldThrowException_WhenRoleNotFound() {
        int roleId = 1;

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> roleService.getRoleById(roleId));

        assertEquals("Ο ρόλος με ID 1 δεν βρέθηκε.", exception.getMessage());
        verify(roleRepository).findById(roleId);
    }

    @Test
    void updateRole_ShouldReturnUpdatedRole() {
        int roleId = 1;

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ROLE_ADMIN");

        Role existingRole = new Role();
        existingRole.setId(roleId);
        existingRole.setName(ERole.ROLE_USER);

        Role updatedRole = new Role();
        updatedRole.setId(roleId);
        updatedRole.setName(ERole.ROLE_ADMIN);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(existingRole));
        when(roleRepository.save(existingRole)).thenReturn(updatedRole);

        RoleDTO result = roleService.updateRole(roleId, roleDTO);

        assertNotNull(result);
        assertEquals("ROLE_ADMIN", result.getName());
        verify(roleRepository).findById(roleId);
        verify(roleRepository).save(existingRole);
    }

    @Test
    void updateRole_ShouldThrowException_WhenRoleNotFound() {
        int roleId = 1;

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ROLE_ADMIN");

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> roleService.updateRole(roleId, roleDTO));

        assertEquals("Ο ρόλος με ID 1 δεν βρέθηκε.", exception.getMessage());
        verify(roleRepository).findById(roleId);
    }

    @Test
    void deleteRole_ShouldDeleteRole() {
        int roleId = 1;

        Role role = new Role();
        role.setId(roleId);
        role.setName(ERole.ROLE_USER);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        doNothing().when(roleRepository).delete(role);

        roleService.deleteRole(roleId);

        verify(roleRepository).findById(roleId);
        verify(roleRepository).delete(role);
    }

    @Test
    void deleteRole_ShouldThrowException_WhenRoleNotFound() {
        int roleId = 1;

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> roleService.deleteRole(roleId));

        assertEquals("Ο ρόλος με ID 1 δεν βρέθηκε.", exception.getMessage());
        verify(roleRepository).findById(roleId);
    }
}

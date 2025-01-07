package com.example.demo.serviceTest;

import com.example.demo.dto.UserRoleDTO;
import com.example.demo.model.ERole;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.model.UserRoleKey;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserRoleServiceImpl;
import com.example.demo.service.UserService;
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

class UserRoleServiceImplTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private UserService userService; // Προσθήκη mock για το UserService

    @Mock
    private RoleService roleService; // Προσθήκη mock για το RoleService

    @InjectMocks
    private UserRoleServiceImpl userRoleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void assignRoleToUser_ShouldAssignSuccessfully() {
        // Mock δεδομένα
        User user = new User();
        user.setId(1);
        user.setUsername("john_doe");

        Role role = new Role();
        role.setId(2);
        role.setName(ERole.ROLE_USER);

        UserRole userRole = new UserRole(user, role);
        UserRoleKey userRoleKey = new UserRoleKey(1, 2);
        userRole.setId(userRoleKey);

        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUsername("john_doe");
        userRoleDTO.setRoleName("ROLE_USER");

        // Mock συμπεριφορά για τις εξαρτήσεις
        when(userService.getUserByUsername("john_doe")).thenReturn(user);
        when(roleService.getRoleByName(ERole.ROLE_USER)).thenReturn(role);
        when(userRoleRepository.save(any(UserRole.class))).thenReturn(userRole);

        // Εκτέλεση
        UserRoleDTO result = userRoleService.assignRoleToUser(userRoleDTO);

        // Έλεγχοι
        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        assertEquals("ROLE_USER", result.getRoleName());
        verify(userService, times(1)).getUserByUsername("john_doe");
        verify(roleService, times(1)).getRoleByName(ERole.ROLE_USER);
        verify(userRoleRepository, times(1)).save(any(UserRole.class));
    }

    @Test
    void getAllUserRoles_ShouldReturnUserRoles() {
        // Mock δεδομένα
        User user1 = new User();
        user1.setId(1);
        Role role1 = new Role();
        role1.setId(2);
        role1.setName(ERole.ROLE_USER);
        UserRole userRole1 = new UserRole(user1, role1);
        userRole1.setId(new UserRoleKey(1, 2));

        User user2 = new User();
        user2.setId(3);
        Role role2 = new Role();
        role2.setId(4);
        role2.setName(ERole.ROLE_ADMIN);
        UserRole userRole2 = new UserRole(user2, role2);
        userRole2.setId(new UserRoleKey(3, 4));

        when(userRoleRepository.findAll()).thenReturn(Arrays.asList(userRole1, userRole2));

        // Εκτέλεση
        List<UserRoleDTO> result = userRoleService.getAllUserRoles();

        // Έλεγχοι
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRoleRepository, times(1)).findAll();
    }

    @Test
    void getUserRoleById_ShouldReturnUserRole() {
        // Mock δεδομένα
        User user = new User();
        user.setId(1);
        Role role = new Role();
        role.setId(2);
        role.setName(ERole.ROLE_USER);
        UserRole userRole = new UserRole(user, role);
        UserRoleKey userRoleKey = new UserRoleKey(1, 2);
        userRole.setId(userRoleKey);

        when(userRoleRepository.findById(userRoleKey)).thenReturn(Optional.of(userRole));

        // Εκτέλεση
        UserRoleDTO result = userRoleService.getUserRoleById(userRoleKey);

        // Έλεγχοι
        assertNotNull(result);
        assertEquals(1, result.getUserId());
        assertEquals(2, result.getRoleId());
        verify(userRoleRepository, times(1)).findById(userRoleKey);
    }

    @Test
    void deleteUserRole_ShouldDeleteSuccessfully() {
        // Mock δεδομένα
        User user = new User();
        user.setId(1);
        Role role = new Role();
        role.setId(2);
        role.setName(ERole.ROLE_USER);
        UserRole userRole = new UserRole(user, role);
        UserRoleKey userRoleKey = new UserRoleKey(1, 2);
        userRole.setId(userRoleKey);

        when(userRoleRepository.findById(userRoleKey)).thenReturn(Optional.of(userRole));

        // Εκτέλεση
        userRoleService.deleteUserRole(userRoleKey);

        // Έλεγχοι
        verify(userRoleRepository, times(1)).findById(userRoleKey);
        verify(userRoleRepository, times(1)).delete(userRole);
    }
}

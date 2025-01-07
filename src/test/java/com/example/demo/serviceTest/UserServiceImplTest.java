package com.example.demo.serviceTest;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.ERole;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("john_doe");
        userDTO.setPassword("password123");
        userDTO.setRoles(Set.of("ROLE_USER"));

        User user = new User();
        user.setUsername("john_doe");
        user.setPassword("encryptedPassword");

        Role role = new Role();
        role.setId(1);
        role.setName(ERole.ROLE_USER);

        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(roleService.getRoleByName(ERole.ROLE_USER)).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDTO(user, false)).thenReturn(userDTO);

        UserDTO result = userService.createUser(userDTO);

        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        verify(roleService, times(1)).getRoleByName(ERole.ROLE_USER);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        User user1 = new User();
        User user2 = new User();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        when(userMapper.toDTO(user1, false)).thenReturn(new UserDTO());
        when(userMapper.toDTO(user2, false)).thenReturn(new UserDTO());

        List<UserDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_ShouldReturnUser() {
        User user = new User();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user, false)).thenReturn(new UserDTO());

        UserDTO result = userService.getUserById(1);

        assertNotNull(result);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("john_doe");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("john_doe_updated");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        doNothing().when(userMapper).updateEntityFromDTO(userDTO, user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user, false)).thenReturn(userDTO);

        UserDTO result = userService.updateUser(1, userDTO);

        assertNotNull(result);
        assertEquals("john_doe_updated", result.getUsername());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser_ShouldDeleteSuccessfully() {
        User user = new User();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.deleteUser(1);

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void updateUserRoles_ShouldUpdateRolesSuccessfully() {
        User user = new User();
        user.setId(1);

        Role role = new Role();
        role.setId(2);
        role.setName(ERole.ROLE_ADMIN);

        Set<String> roles = Set.of("ROLE_ADMIN");
        Set<Role> roleEntities = Set.of(role);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(roleService.getRoleByName(ERole.ROLE_ADMIN)).thenReturn(role);

        userService.updateUserRoles(1, roles);

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
    }
}

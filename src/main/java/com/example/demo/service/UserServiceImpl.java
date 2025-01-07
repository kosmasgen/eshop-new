package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.ERole;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.AESEncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Υλοποίηση της διεπαφής UserService για τη διαχείριση των χρηστών.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleService roleService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);

        // Κρυπτογράφηση του κωδικού πρόσβασης
        try {
            String encryptedPassword = AESEncryptionUtil.encrypt(user.getPassword());
            user.setPassword(encryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Σφάλμα κατά την κρυπτογράφηση του κωδικού: " + e.getMessage(), e);
        }

        // Προσθήκη προεπιλεγμένου ρόλου ROLE_USER αν δε δοθούν ρόλοι
        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
            Role role = roleService.getRoleByName(ERole.ROLE_USER);
            user.setRoles(Set.of(role));
        } else {
            user.setRoles(mapRoles(userDTO.getRoles()));
        }

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser, false);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> userMapper.toDTO(user, false))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.USER_NOT_FOUND,
                        new Object[]{id} // Αλλαγή στην παράμετρο του exception
                ));
        return userMapper.toDTO(user, false);
    }

    @Override
    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.USER_NOT_FOUND,
                        new Object[]{id} // Αλλαγή στην παράμετρο του exception
                ));

        // Ενημέρωση των πεδίων του User μέσω του UserMapper
        userMapper.updateEntityFromDTO(userDTO, user);

        // Ενημέρωση ρόλων αν υπάρχουν
        if (userDTO.getRoles() != null) {
            user.setRoles(mapRoles(userDTO.getRoles()));
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser, false);
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.USER_NOT_FOUND,
                        new Object[]{id} // Αλλαγή στην παράμετρο του exception
                ));
        userRepository.delete(user);
    }

    @Override
    public UserDTO updateUserRoles(Integer userId, Set<String> roles) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.USER_NOT_FOUND,
                        new Object[]{userId} // Αλλαγή στην παράμετρο του exception
                ));

        Set<Role> roleEntities = mapRoles(roles);
        user.setRoles(roleEntities);

        userRepository.save(user);
        return userMapper.toDTO(user, false);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.USER_NOT_FOUND_BY_USERNAME,
                        new Object[]{username} // Αλλαγή στην παράμετρο του exception
                ));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Χαρτογραφεί τα ονόματα των ρόλων σε οντότητες Role μέσω του RoleService.
     */
    private Set<Role> mapRoles(Set<String> roles) {
        return roles.stream()
                .map(roleName -> roleService.getRoleByName(ERole.valueOf(roleName.toUpperCase())))
                .collect(Collectors.toSet());
    }
}

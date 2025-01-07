package com.example.demo.mapper;

import org.springframework.stereotype.Component;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

import java.util.stream.Collectors;

/**
 * Mapper για τη χαρτογράφηση μεταξύ της οντότητας User και του DTO UserDTO.
 */
@Component
public class UserMapper {

    /**
     * Μετατρέπει μια οντότητα User σε DTO UserDTO.
     *
     * @param user            η οντότητα User που θα μετατραπεί.
     * @param includePassword αν το password πρέπει να συμπεριληφθεί στο DTO.
     * @return το αντίστοιχο UserDTO.
     * @throws IllegalArgumentException αν η οντότητα User είναι null.
     */
    public UserDTO toDTO(User user, boolean includePassword) {
        if (user == null) {
            throw new IllegalArgumentException("Η οντότητα User δεν μπορεί να είναι null.");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles()
                .stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet()));

        if (includePassword) {
            userDTO.setPassword(user.getPassword());
        }

        return userDTO;
    }

    /**
     * Μετατρέπει ένα DTO UserDTO σε οντότητα User.
     *
     * @param userDTO το DTO UserDTO που θα μετατραπεί.
     * @return η αντίστοιχη οντότητα User.
     * @throws IllegalArgumentException αν το UserDTO είναι null.
     */
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("Το UserDTO δεν μπορεί να είναι null.");
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // Περιλαμβάνει το password από το DTO, αν υπάρχει

        return user;
    }

    /**
     * Ενημερώνει μια υπάρχουσα οντότητα User με δεδομένα από ένα UserDTO.
     *
     * @param userDTO το DTO με τα νέα δεδομένα.
     * @param user    η οντότητα User που θα ενημερωθεί.
     * @throws IllegalArgumentException αν το UserDTO ή η οντότητα User είναι null.
     */
    public void updateEntityFromDTO(UserDTO userDTO, User user) {
        if (userDTO == null || user == null) {
            throw new IllegalArgumentException("Το UserDTO ή η οντότητα User δεν μπορεί να είναι null.");
        }

        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(userDTO.getPassword());
        }
        // Τα roles θα ενημερώνονται ξεχωριστά στην υπηρεσία
    }
}

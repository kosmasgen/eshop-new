package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "user_roles")
@Getter
@Setter
@NoArgsConstructor
public class UserRole {

    @EmbeddedId
    private UserRoleKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public UserRole(User user, Role role) {
        if (user == null || role == null) {
            throw new IllegalArgumentException("Ο χρήστης και ο ρόλος δεν μπορούν να είναι null.");
        }
        this.user = user;
        this.role = role;
        this.id = new UserRoleKey(user.getId(), role.getId());
    }

    @Override
    public String toString() {
        return "Σχέση Χρήστη-Ρόλου {" +
                "Χρήστης = " + (user != null ? user.getUsername() : "null") +
                ", Ρόλος = " + (role != null ? role.getName() : "null") +
                '}';
    }
}

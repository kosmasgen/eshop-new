package com.example.demo.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class UserRoleKey implements Serializable {

    private Integer userId;
    private Integer roleId;


    public UserRoleKey(Integer userId, Integer roleId) {
        if (userId == null || roleId == null) {
            throw new IllegalArgumentException("Τα πεδία userId και roleId δεν μπορούν να είναι null.");
        }
        this.userId = userId;
        this.roleId = roleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleKey that = (UserRoleKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }

    @Override
    public String toString() {
        return "UserRoleKey{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}

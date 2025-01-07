package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO για τη σχέση χρήστη-ρόλου.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleDTO {

    private Integer id;

    @NotNull(message = "{validation.userRole.userId.notnull}")
    private Integer userId;

    private String username;

    @NotNull(message = "{validation.userRole.roleId.notnull}")
    private Integer roleId;

    private String roleName;

    @Override
    public String toString() {
        return "Σχέση Χρήστη-Ρόλου {" +
                "ID = " + id +
                ", Χρήστης = '" + username + '\'' +
                ", ID Χρήστη = " + userId +
                ", Ρόλος = '" + roleName + '\'' +
                ", ID Ρόλου = " + roleId +
                '}';
    }
}

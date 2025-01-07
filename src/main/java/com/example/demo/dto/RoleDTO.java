package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO {

    private Integer id;    // Αναγνωριστικό Ρόλου

    @NotNull(message = "{validation.role.name.notnull}")
    @Pattern(
            regexp = "ROLE_[A-Z]+",
            message = "{validation.role.name.pattern}"
    )
    private String name;   // Όνομα Ρόλου (π.χ., ROLE_USER, ROLE_ADMIN)

    @Override
    public String toString() {
        return "Ρόλος {" +
                "ID = " + id +
                ", Όνομα Ρόλου = '" + name + '\'' +
                '}';
    }
}

package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Integer id;

    @NotNull(message = "{validation.password.notnull}")
    @Size(min = 8, max = 20, message = "{validation.password.size}")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+=<>?])[a-zA-Z0-9!@#$%^&*()_+=<>?]{8,20}$",
            message = "{validation.password.pattern}"
    )
    private String password;

    @NotNull(message = "{validation.username.notnull}")
    @Size(max = 50, message = "{validation.username.size}")
    @Pattern(
            regexp = "^[a-zA-Z0-9._-]+$",
            message = "{validation.username.pattern}"
    )
    private String username;

    @NotNull(message = "{validation.email.notnull}")
    @Email(message = "{validation.email.invalid}")
    @Size(max = 50, message = "{validation.email.size}")
    private String email;

    private Set<String> roles = new HashSet<>();

    @Override
    public String toString() {
        return "Χρήστης {" +
                "ID = " + id +
                ", Όνομα Χρήστη = '" + username + '\'' +
                ", Email = '" + email + '\'' +
                ", Ρόλος = " + (roles != null ? roles : "[]") +
                '}';
    }
}

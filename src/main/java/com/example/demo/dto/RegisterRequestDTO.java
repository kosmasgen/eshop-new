package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequestDTO {

    @NotNull(message = "{validation.username.notnull}")
    @Size(min = 5, max = 50, message = "{validation.username.size}")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "{validation.username.pattern}")
    private String username;

    @NotNull(message = "{validation.password.notnull}")
    @Size(min = 8, max = 20, message = "{validation.password.size}")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+=<>?])[a-zA-Z0-9!@#$%^&*()_+=<>?]{8,20}$",
            message = "{validation.password.pattern}"
    )
    private String password;

    @NotNull(message = "{validation.email.notnull}")
    @Email(message = "{validation.email.invalid}")
    @Size(max = 50, message = "{validation.email.size}")
    private String email;

    @Override
    public String toString() {
        return "Αίτημα Εγγραφής {" +
                "Όνομα Χρήστη = '" + username + '\'' +
                ", Κωδικός = '" + password + '\'' +
                ", Email = '" + email + '\'' +
                '}';
    }
}

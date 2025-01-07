package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO για το αίτημα σύνδεσης ενός χρήστη.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Τα πεδία με τιμές null δεν περιλαμβάνονται στο JSON
public class LoginRequestDTO {

    @NotNull(message = "{validation.username.notnull}")
    @Size(min = 5, max = 50, message = "{validation.username.size}")
    private String username;

    @NotNull(message = "{validation.password.notnull}")
    @Size(min = 8, max = 20, message = "{validation.password.size}")
    private String password;

    @Override
    public String toString() {
        return "LoginRequestDTO{" +
                "username='" + username + '\'' +
                ", password='***'}"; // Απόκρυψη του password για λόγους ασφαλείας
    }
}

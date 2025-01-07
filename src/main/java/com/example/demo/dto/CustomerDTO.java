package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {

    private Integer id;

    @NotNull(message = "{validation.customer.firstName.notnull}")
    @Size(max = 15, message = "{validation.customer.firstName.size}")
    private String firstName;

    @NotNull(message = "{validation.customer.lastName.notnull}")
    @Size(max = 15, message = "{validation.customer.lastName.size}")
    private String lastName;

    @NotNull(message = "{validation.customer.telephone.notnull}")
    @Size(max = 13, message = "{validation.customer.telephone.size}")
    private String telephone;

    @NotNull(message = "{validation.customer.afm.notnull}")
    @Size(min = 9, max = 9, message = "{validation.customer.afm.size}")
    @Pattern(regexp = "\\d+", message = "{validation.customer.afm.pattern}")
    private String afm;

    @NotNull(message = "{validation.customer.wholesale.notnull}")
    private boolean wholesale;

    @Positive(message = "{validation.customer.balance.positive}")
    private double balance;

    private UserDTO user;

    public String getUsername() {
        return user != null ? user.getUsername() : null;
    }

    public String getEmail() {
        return user != null ? user.getEmail() : null;
    }

    public String getPassword() {
        return user != null ? user.getPassword() : null;
    }

    @Override
    public String toString() {
        return "ΠελάτηςDTO {" +
                "ID=" + id +
                ", Όνομα='" + firstName + '\'' +
                ", Επώνυμο='" + lastName + '\'' +
                ", Τηλέφωνο='" + telephone + '\'' +
                ", ΑΦΜ='" + afm + '\'' +
                ", Χονδρική Πώληση=" + (wholesale ? "Ναι" : "Όχι") +
                ", Υπόλοιπο=" + balance +
                ", Χρήστης=" + user +
                '}';
    }
}

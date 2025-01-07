package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SupplierDTO {

    private Integer id;

    @NotNull(message = "{validation.supplier.firstName.notnull}")
    @Size(max = 15, message = "{validation.supplier.firstName.size}")
    private String firstName;

    @NotNull(message = "{validation.supplier.lastName.notnull}")
    @Size(max = 15, message = "{validation.supplier.lastName.size}")
    private String lastName;

    @NotNull(message = "{validation.supplier.telephone.notnull}")
    @Size(max = 13, message = "{validation.supplier.telephone.size}")
    @Pattern(regexp = "\\d+", message = "{validation.supplier.telephone.pattern}")
    private String telephone;

    @NotNull(message = "{validation.supplier.afm.notnull}")
    @Size(max = 9, message = "{validation.supplier.afm.size}")
    @Pattern(regexp = "\\d{9}", message = "{validation.supplier.afm.pattern}")
    private String afm;

    @NotNull(message = "{validation.supplier.location.notnull}")
    @Size(max = 100, message = "{validation.supplier.location.size}")
    private String location;

    @Override
    public String toString() {
        return "Προμηθευτής {" +
                "ID = " + id +
                ", Όνομα = '" + firstName + '\'' +
                ", Επώνυμο = '" + lastName + '\'' +
                ", Τηλέφωνο = '" + telephone + '\'' +
                ", ΑΦΜ = '" + afm + '\'' +
                ", Τοποθεσία = '" + location + '\'' +
                '}';
    }
}

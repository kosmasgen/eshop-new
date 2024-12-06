package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SupplierDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String telephone;
    private String afm;
    private String location;
}

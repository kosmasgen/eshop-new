package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierProductDTO {
    private int id;
    private SupplierDTO supplier;
    private ProductDTO product;
    private int quantity;
}

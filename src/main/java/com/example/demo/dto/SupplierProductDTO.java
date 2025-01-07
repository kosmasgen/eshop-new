package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO για τη σχέση Προμηθευτή-Προϊόντος.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SupplierProductDTO {

    private Integer id;

    @NotNull(message = "{validation.supplierProduct.supplier.notnull}")
    private SupplierDTO supplier;

    @NotNull(message = "{validation.supplierProduct.product.notnull}")
    private ProductDTO product;

    @Positive(message = "{validation.supplierProduct.quantity.positive}")
    private int quantity;
}

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
public class ProductDTO {

    private Integer id;

    @NotNull(message = "{validation.product.productName.notnull}")
    @Size(max = 50, message = "{validation.product.productName.size}")
    private String productName;

    @NotNull(message = "{validation.product.type.notnull}")
    @Size(max = 50, message = "{validation.product.type.size}")
    private String type;

    @NotNull(message = "{validation.product.price.notnull}")
    @Positive(message = "{validation.product.price.positive}")
    private double price;

    @NotNull(message = "{validation.product.supplier.notnull}")
    private SupplierDTO supplier;

    @NotNull(message = "{validation.product.quantity.notnull}")
    @Positive(message = "{validation.product.quantity.positive}")
    private int quantity;

    @NotNull(message = "{validation.product.uuid.notnull}")
    @Pattern(regexp = "^[a-f0-9\\-]{36}$", message = "{validation.product.uuid.pattern}")
    private String uuid;
}

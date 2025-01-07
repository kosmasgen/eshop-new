package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private Integer id;

    @NotNull(message = "{validation.order.supplier.notnull}")
    private SupplierDTO supplier;

    @NotNull(message = "{validation.order.product.notnull}")
    private ProductDTO product;

    @NotNull(message = "{validation.order.quantity.notnull}")
    private Integer quantity;

    @NotNull(message = "{validation.order.price.notnull}")
    private Double price;


    @NotNull(message = "{validation.order.totalPrice.notnull}")
    private Double totalPrice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // Μορφή για την ημερομηνία
    private LocalDateTime createdAt;

    // Επιστρέφει το ID του προϊόντος
    public Integer getProductId() {
        return (product != null) ? product.getId() : null;
    }

    // Επιστρέφει το ID του προμηθευτή
    public Integer getSupplierId() {
        return (supplier != null) ? supplier.getId() : null;
    }

    // Προσθήκη της μεθόδου setProductId
    public void setProductId(Integer productId) {
        if (product == null) {
            product = new ProductDTO();
        }
        product.setId(productId);
    }

    // Προσθήκη της μεθόδου setSupplierId
    public void setSupplierId(Integer supplierId) {
        if (supplier == null) {
            supplier = new SupplierDTO();
        }
        supplier.setId(supplierId);
    }
}


package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
public class OrderDTO {
    private int id;
    private  SupplierDTO supplier;
    private int productId;
    private int quantity;
    private double price;
    private double totalPrice;
    private String createdAt;
}

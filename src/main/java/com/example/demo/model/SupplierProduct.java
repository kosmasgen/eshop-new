package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "suppliers_products")
@Getter
@Setter
@NoArgsConstructor

public class SupplierProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "supplier_id", nullable = false)
    private int supplierId;

    @Column(name = "product_id", nullable = false)
    private int productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Override
    public String toString() {
        return "Προμηθευτής - Προϊόν {" +
                "ID=" + id +
                ", Κωδικός Προμηθευτή=" + supplierId +
                ", Κωδικός Προϊόντος=" + productId +
                ", Ποσότητα=" + quantity +
                '}';
    }
}

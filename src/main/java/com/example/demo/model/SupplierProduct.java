package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "suppliers_products",
        uniqueConstraints = @UniqueConstraint(columnNames = {"supplier_id", "product_id"}))
@Getter
@Setter
@NoArgsConstructor
public class SupplierProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Override
    public String toString() {
        return "Προμηθευτής - Προϊόν {" +
                "ID=" + id +
                ", Προμηθευτής=" + (supplier != null ? supplier.getId() : "null") +
                ", Προϊόν=" + (product != null ? product.getId() : "null") +
                ", Ποσότητα=" + quantity +
                '}';
    }
}

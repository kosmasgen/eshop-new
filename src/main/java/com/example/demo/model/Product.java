package com.example.demo.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import  lombok.Getter;
import  lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "product_name", length = 50, nullable = false)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "uuid", length = 36, nullable = false)
    private String uuid;


    @Override
    public String toString() {
        return "Προϊόν {" +
                "Κωδικός=" + id +
                ", Όνομα προϊόντος ='" + productName + '\'' +
                ", Κατηγορία='" + category + '\'' +
                ", Τιμή=" + price +
                ", Ποσότητα=" + quantity +
                ", Κωδικός Προμηθευτή=" + supplier +
                ", UUID='" + uuid + '\'' +
                '}';
    }
}

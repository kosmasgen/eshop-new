package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    // Συσχέτιση με τον πίνακα suppliers
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    // Συσχέτιση με τον πίνακα products
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Παραγγελία {" +
                "ID=" + id +
                ", Προμηθευτής=" + supplier +
                ", Προϊόν=" + product +
                ", Ποσότητα=" + quantity +
                ", Τιμή=" + price +
                ", Σύνολο=" + totalPrice +
                ", Ημερομηνία Δημιουργίας=" + createdAt +
                '}';
    }
}

package com.example.demo.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", length = 15, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 15, nullable = false)
    private String lastName;

    @Column(name = "telephone", length = 13, nullable = false)
    private String telephone;

    @Column(name = "afm", length = 9, nullable = false, unique = true)
    private String afm;

    @Column(name = "wholesale", nullable = false)
    private boolean wholesale;

    @Column(name = "balance", nullable = false)
    private double balance;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Override
    public String toString() {
        return "Πελάτης {" +
                "ID=" + id +
                ", Όνομα='" + firstName + '\'' +
                ", Επώνυμο='" + lastName + '\'' +
                ", Τηλέφωνο='" + telephone + '\'' +
                ", ΑΦΜ='" + afm + '\'' +
                ", Χονδρική=" + (wholesale ? "Ναι" : "Όχι") +
                ", Υπόλοιπο=" + balance +
                ", Χρήστης='" + (user != null ? user.getUsername() : "null") + '\'' +
                '}';
    }
}

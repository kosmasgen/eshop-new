package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;
import  lombok.Getter;
import  lombok.Setter;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor

public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name", length = 15, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 15, nullable = false)
    private String lastName;

    @Column(name = "telephone", length = 13, nullable = false)
    private String telephone;

    @Column(name = "afm", length = 9, nullable = false, unique = true )
    private String afm;

    @Column(name = "location", length = 100, nullable = false)
    private String location;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;


    @Override
    public String toString() {
        return "Προμηθευτής {" +
                "ID=" + id +
                ", Όνομα='" + firstName + '\'' +
                ", Επώνυμο='" + lastName + '\'' +
                ", Τηλέφωνο='" + telephone + '\'' +
                ", ΑΦΜ='" + afm + '\'' +
                ", Τοποθεσία='" + location + '\'' +
                '}';
    }

}

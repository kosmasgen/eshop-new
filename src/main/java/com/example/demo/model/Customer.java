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

/**
 * Οντότητα για τη διαχείριση των δεδομένων πελατών.
 * Χρησιμοποιείται για τη χαρτογράφηση της βάσης δεδομένων.
 */
@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    /**
     * Το μοναδικό αναγνωριστικό του πελάτη.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Το όνομα του πελάτη.
     */
    @Column(name = "first_name", length = 15, nullable = false)
    private String firstName;

    /**
     * Το επώνυμο του πελάτη.
     */
    @Column(name = "last_name", length = 15, nullable = false)
    private String lastName;

    /**
     * Ο αριθμός τηλεφώνου του πελάτη.
     */
    @Column(name = "telephone", length = 13, nullable = false)
    private String telephone;

    /**
     * Ο αριθμός φορολογικού μητρώου (ΑΦΜ) του πελάτη.
     * Είναι μοναδικό για κάθε πελάτη.
     */
    @Column(name = "afm", length = 9, nullable = false, unique = true)
    private String afm;

    /**
     * Αν ο πελάτης ανήκει σε κατηγορία χονδρικής πώλησης.
     */
    @Column(name = "wholesale", nullable = false)
    private boolean wholesale;

    /**
     * Το υπόλοιπο του πελάτη.
     */
    @Column(name = "balance", nullable = false)
    private double balance;

    /**
     * Επιστρέφει την αναπαράσταση του πελάτη ως συμβολοσειρά.
     *
     * @return το string που περιέχει τις πληροφορίες του πελάτη.
     */
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
                '}';
    }
}

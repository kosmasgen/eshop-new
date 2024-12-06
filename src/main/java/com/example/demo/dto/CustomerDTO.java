package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) για τη μεταφορά δεδομένων πελατών.
 * Χρησιμοποιείται για την ανταλλαγή δεδομένων μεταξύ του API και των υπηρεσιών.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    /**
     * Το μοναδικό αναγνωριστικό του πελάτη.
     */
    private Integer id;

    /**
     * Το όνομα του πελάτη.
     */
    private String firstName;

    /**
     * Το επώνυμο του πελάτη.
     */
    private String lastName;

    /**
     * Ο αριθμός τηλεφώνου του πελάτη.
     */
    private String telephone;

    /**
     * Ο αριθμός φορολογικού μητρώου (ΑΦΜ) του πελάτη.
     */
    private String afm;

    /**
     * Αν ο πελάτης ανήκει σε κατηγορία χονδρικής πώλησης.
     */
    private boolean wholesale;

    /**
     * Το υπόλοιπο του πελάτη.
     */
    private double balance;
}

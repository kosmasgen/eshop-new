package com.example.demo.repository;

import com.example.demo.model.Customer;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository για τη διαχείριση των οντοτήτων Customer στη βάση δεδομένων.
 * Παρέχει έτοιμες CRUD λειτουργίες μέσω του JpaRepository.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    /**
     * Ελέγχει αν υπάρχει πελάτης με το συγκεκριμένο ΑΦΜ στη βάση δεδομένων.
     *
     * @param afm το ΑΦΜ που θα ελεγχθεί.
     * @return true αν υπάρχει πελάτης με το συγκεκριμένο ΑΦΜ, αλλιώς false.
     */
    boolean existsByAfm(String afm);

    /**
     * Επιστρέφει λίστα πελατών που περιέχουν το συγκεκριμένο όνομα ή τμήμα ονόματος,
     * είτε στο πεδίο "firstName" είτε στο πεδίο "lastName", ανεξαρτήτως πεζών/κεφαλαίων.
     *
     * @param firstName το όνομα ή τμήμα ονόματος που θα ελεγχθεί στο πεδίο "firstName".
     * @param lastName το όνομα ή τμήμα ονόματος που θα ελεγχθεί στο πεδίο "lastName".
     * @return λίστα πελατών που ταιριάζουν με τα κριτήρια.
     */
    List<Customer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    /**
     * Επιστρέφει λίστα πελατών που σχετίζονται με έναν συγκεκριμένο χρήστη.
     *
     * @param user ο χρήστης που σχετίζεται με τους πελάτες.
     * @return λίστα πελατών που ανήκουν στον συγκεκριμένο χρήστη.
     */
    List<Customer> findByUser(User user);
}

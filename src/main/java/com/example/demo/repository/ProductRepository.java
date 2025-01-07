package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository για τη διαχείριση των οντοτήτων Product στη βάση δεδομένων.
 * Παρέχει έτοιμες CRUD λειτουργίες μέσω του JpaRepository και custom queries για αναζητήσεις προϊόντων.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Βρίσκει προϊόντα με βάση το όνομα, ανεξαρτήτως πεζών-κεφαλαίων χαρακτήρων.
     *
     * @param productName Το όνομα του προϊόντος ή μέρος αυτού.
     * @return Λίστα από προϊόντα που ταιριάζουν με το όνομα.
     */
    List<Product> findByProductNameContainingIgnoreCase(String productName);

    /**
     * Βρίσκει προϊόντα με βάση την κατηγορία τους.
     *
     * @param categoryId Το ID της κατηγορίας.
     * @return Λίστα από προϊόντα που ανήκουν στην καθορισμένη κατηγορία.
     */
    List<Product> findByCategoryId(int categoryId);



}

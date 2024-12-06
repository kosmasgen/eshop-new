package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository για τη διαχείριση των δεδομένων παραγγελιών.
 * Παρέχει μεθόδους για την αλληλεπίδραση με τη βάση δεδομένων.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    /**
     * Αναζητά παραγγελίες που περιέχουν το όνομα του προϊόντος, ανεξαρτήτως πεζών-κεφαλαίων.
     *
     * @param productName το όνομα ή μέρος του ονόματος του προϊόντος.
     * @return λίστα παραγγελιών που ταιριάζουν με το όνομα.
     */
    List<Order> findByProductProductNameContainingIgnoreCase(String productName);

    /**
     * Επιστρέφει όλες τις παραγγελίες.
     *
     * @return λίστα όλων των παραγγελιών.
     */
    List<Order> findAll();

    /**
     * Αναζητά παραγγελίες που ανήκουν σε έναν συγκεκριμένο προμηθευτή.
     *
     * @param supplierId το ID του προμηθευτή.
     * @return λίστα παραγγελιών που συνδέονται με τον συγκεκριμένο προμηθευτή.
     */
    List<Order> findBySupplierId(Integer supplierId);
}

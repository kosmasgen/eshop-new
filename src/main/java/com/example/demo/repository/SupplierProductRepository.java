package com.example.demo.repository;

import com.example.demo.model.SupplierProduct; // Εισαγωγή της σωστής οντότητας
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository για τη διαχείριση των οντότητων SupplierProduct στη βάση δεδομένων.
 * Παρέχει έτοιμες CRUD λειτουργίες μέσω του JpaRepository.
 */
@Repository
public interface SupplierProductRepository extends JpaRepository<SupplierProduct, Integer> {

    // Μπορείτε να προσθέσετε custom queries αν χρειάζονται
}

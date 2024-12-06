package com.example.demo.repository;

import com.example.demo.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository για τη διαχείριση των οντότητων Supplier στη βάση δεδομένων.
 * Παρέχει έτοιμες CRUD λειτουργίες μέσω του JpaRepository και custom queries για αναζητήσεις.
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    /**
     * Βρίσκει τους προμηθευτές με βάση το όνομα (πρώτο ή τελευταίο).
     *
     * @param name Το όνομα ή τμήμα του ονόματος του προμηθευτή.
     * @return Λίστα με τους προμηθευτές που ταιριάζουν με το όνομα.
     */
    List<Supplier> findByFirstNameContainingIgnoreCase(String name);

    /**
     * Βρίσκει έναν προμηθευτή με βάση το ID.
     *
     * @param id Το ID του προμηθευτή.
     * @return Optional με τον προμηθευτή αν βρεθεί, διαφορετικά κενό.
     */
    Optional<Supplier> findById(Integer id);

    /**
     * Βρίσκει τους προμηθευτές με βάση την τοποθεσία.
     *
     * @param location Η τοποθεσία του προμηθευτή.
     * @return Λίστα με τους προμηθευτές που ταιριάζουν με την τοποθεσία.
     */
    List<Supplier> findByLocationContainingIgnoreCase(String location);
}

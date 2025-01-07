package com.example.demo.service;

import com.example.demo.dto.SupplierDTO;
import java.time.LocalDate;
import java.util.List;

/**
 * Διεπαφή για τη διαχείριση των προμηθευτών.
 */
public interface SupplierService {

    SupplierDTO createSupplier(SupplierDTO supplierDTO); // Δημιουργία προμηθευτή

    SupplierDTO updateSupplier(Integer id, SupplierDTO supplierDTO); // Ενημέρωση προμηθευτή

    void deleteSupplier(Integer id); // Διαγραφή προμηθευτή

    SupplierDTO getSupplierById(Integer id); // Εύρεση προμηθευτή με ID

    List<SupplierDTO> getAllSuppliers(); // Λίστα όλων των προμηθευτών

    List<SupplierDTO> findSuppliersByName(String name); // Αναζήτηση με όνομα ή τμήμα ονόματος

    List<SupplierDTO> findSuppliersByLocation(String location); // Αναζήτηση με βάση την τοποθεσία

    Double calculateTurnover(Integer supplierId, LocalDate startDate, LocalDate endDate); // Υπολογισμός τζίρου
}

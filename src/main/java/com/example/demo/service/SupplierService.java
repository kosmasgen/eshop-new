package com.example.demo.service;
import java.time.LocalDate;
import com.example.demo.dto.SupplierDTO;
import java.util.List;

public interface SupplierService {

    SupplierDTO createSupplier(SupplierDTO supplierDTO); // Δημιουργία προμηθευτή

    SupplierDTO updateSupplier(int id, SupplierDTO supplierDTO); // Ενημέρωση προμηθευτή

    void deleteSupplier(int id); // Διαγραφή προμηθευτή

    SupplierDTO getSupplierById(int id); // Εύρεση προμηθευτή με ID

    List<SupplierDTO> getAllSuppliers(); // Λίστα όλων των προμηθευτών

    List<SupplierDTO> findSuppliersByName(String name); // Αναζήτηση με όνομα ή τμήμα ονόματος

    List<SupplierDTO> findSuppliersByLocation(String location);

    Double calculateTurnover(Integer supplierId, LocalDate startDate, LocalDate endDate);
}

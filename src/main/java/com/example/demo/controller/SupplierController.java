package com.example.demo.controller;

import com.example.demo.dto.SupplierDTO;
import com.example.demo.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller για τη διαχείριση των προμηθευτών.
 * Παρέχει endpoints για τη δημιουργία, ενημέρωση, διαγραφή, αναζήτηση
 * και υπολογισμό του τζίρου των προμηθευτών.
 */
@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    /**
     * Constructor για την εξάρτηση του SupplierService.
     *
     * @param supplierService η υπηρεσία διαχείρισης προμηθευτών.
     */
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    /**
     * Δημιουργεί έναν νέο προμηθευτή.
     *
     * @param supplierDTO τα δεδομένα του προμηθευτή που πρόκειται να δημιουργηθεί.
     * @return το DTO του δημιουργημένου προμηθευτή.
     */
    @PostMapping
    public ResponseEntity<SupplierDTO> createSupplier(@Validated @RequestBody SupplierDTO supplierDTO) {
        SupplierDTO createdSupplier = supplierService.createSupplier(supplierDTO);
        return ResponseEntity.ok(createdSupplier);
    }

    /**
     * Ενημερώνει έναν προμηθευτή βάσει του ID του.
     *
     * @param id          το ID του προμηθευτή που θα ενημερωθεί.
     * @param supplierDTO τα νέα δεδομένα του προμηθευτή.
     * @return το ενημερωμένο DTO του προμηθευτή.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable int id, @Validated @RequestBody SupplierDTO supplierDTO) {
        SupplierDTO updatedSupplier = supplierService.updateSupplier(id, supplierDTO);
        return ResponseEntity.ok(updatedSupplier);
    }

    /**
     * Διαγράφει έναν προμηθευτή βάσει του ID του.
     *
     * @param id το ID του προμηθευτή που θα διαγραφεί.
     * @return μήνυμα επιτυχούς διαγραφής.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable int id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok("Ο προμηθευτής διαγράφηκε επιτυχώς.");
    }

    /**
     * Επιστρέφει έναν προμηθευτή βάσει του ID του.
     *
     * @param id το ID του προμηθευτή.
     * @return το DTO του προμηθευτή.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable int id) {
        SupplierDTO supplierDTO = supplierService.getSupplierById(id);
        return ResponseEntity.ok(supplierDTO);
    }

    /**
     * Επιστρέφει όλους τους προμηθευτές.
     *
     * @return λίστα με DTO όλων των προμηθευτών.
     */
    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    /**
     * Αναζητά προμηθευτές βάσει του ονόματος ή μέρους του ονόματος.
     *
     * @param name το όνομα ή τμήμα του ονόματος του προμηθευτή.
     * @return λίστα με τους προμηθευτές που ταιριάζουν.
     */
    @GetMapping("/search/name")
    public ResponseEntity<List<SupplierDTO>> findSuppliersByName(@RequestParam String name) {
        List<SupplierDTO> suppliers = supplierService.findSuppliersByName(name);
        return ResponseEntity.ok(suppliers);
    }

    /**
     * Αναζητά προμηθευτές βάσει της τοποθεσίας τους.
     *
     * @param location η τοποθεσία του προμηθευτή.
     * @return λίστα με τους προμηθευτές που ταιριάζουν.
     */
    @GetMapping("/search/location")
    public ResponseEntity<List<SupplierDTO>> findSuppliersByLocation(@RequestParam String location) {
        List<SupplierDTO> suppliers = supplierService.findSuppliersByLocation(location);
        return ResponseEntity.ok(suppliers);
    }

    /**
     * Υπολογίζει τον τζίρο ενός προμηθευτή για συγκεκριμένο χρονικό διάστημα.
     *
     * @param supplierId το ID του προμηθευτή.
     * @param startDate  η ημερομηνία έναρξης.
     * @param endDate    η ημερομηνία λήξης.
     * @return ο τζίρος του προμηθευτή για την περίοδο που ορίστηκε.
     */
    @GetMapping("/{supplierId}/turnover")
    public ResponseEntity<Double> calculateTurnover(
            @PathVariable Integer supplierId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        Double turnover = supplierService.calculateTurnover(supplierId, startDate, endDate);
        return ResponseEntity.ok(turnover);
    }
}

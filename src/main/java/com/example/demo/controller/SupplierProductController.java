package com.example.demo.controller;

import com.example.demo.dto.SupplierProductDTO;
import com.example.demo.service.SupplierProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller για τη διαχείριση των σχέσεων προμηθευτών-προϊόντων.
 * Παρέχει endpoints για τη δημιουργία, ανάκτηση και διαγραφή σχέσεων.
 */
@RestController
@RequestMapping("/api/supplier-products")
public class SupplierProductController {

    private final SupplierProductService supplierProductService;

    /**
     * Constructor για την εξάρτηση του SupplierProductService.
     *
     * @param supplierProductService η υπηρεσία διαχείρισης σχέσεων προμηθευτών-προϊόντων.
     */
    @Autowired
    public SupplierProductController(SupplierProductService supplierProductService) {
        this.supplierProductService = supplierProductService;
    }

    /**
     * Δημιουργεί μια νέα σχέση προμηθευτή-προϊόντος.
     *
     * @param supplierProductDTO τα δεδομένα της σχέσης που πρόκειται να δημιουργηθεί.
     * @return το DTO της δημιουργημένης σχέσης με HTTP Status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<SupplierProductDTO> createSupplierProduct(@RequestBody SupplierProductDTO supplierProductDTO) {
        SupplierProductDTO createdSupplierProduct = supplierProductService.createSupplierProduct(supplierProductDTO);
        return new ResponseEntity<>(createdSupplierProduct, HttpStatus.CREATED);
    }

    /**
     * Επιστρέφει όλες τις σχέσεις προμηθευτών-προϊόντων.
     *
     * @return λίστα με DTO όλων των σχέσεων με HTTP Status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<SupplierProductDTO>> getAllSupplierProducts() {
        List<SupplierProductDTO> supplierProducts = supplierProductService.getAllSupplierProducts();
        return new ResponseEntity<>(supplierProducts, HttpStatus.OK);
    }

    /**
     * Επιστρέφει μια σχέση προμηθευτή-προϊόντος με βάση το ID της.
     *
     * @param id το ID της σχέσης.
     * @return το DTO της σχέσης με HTTP Status 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<SupplierProductDTO> getSupplierProductById(@PathVariable("id") int id) {
        SupplierProductDTO supplierProduct = supplierProductService.getSupplierProductById(id);
        return new ResponseEntity<>(supplierProduct, HttpStatus.OK);
    }

    /**
     * Διαγράφει μια σχέση προμηθευτή-προϊόντος με βάση το ID της.
     *
     * @param id το ID της σχέσης που θα διαγραφεί.
     * @return HTTP Status 204 (No Content) αν η διαγραφή ήταν επιτυχής.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplierProduct(@PathVariable("id") int id) {
        supplierProductService.deleteSupplierProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

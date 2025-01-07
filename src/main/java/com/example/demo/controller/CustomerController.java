package com.example.demo.controller;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.service.CustomerService;
import com.example.demo.security.jwt.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller για τη διαχείριση πελατών.
 * Παρέχει endpoints για CRUD λειτουργίες και αναζήτηση πελατών.
 */
@RestController
@RequestMapping("/api/customers") // Η βάση για όλα τα endpoints της κλάσης
public class CustomerController {

    private final CustomerService customerService;
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * Constructor για την εξάρτηση του CustomerService και JwtTokenUtil.
     *
     * @param customerService η υπηρεσία διαχείρισης πελατών.
     * @param jwtTokenUtil εργαλείο διαχείρισης JWT tokens.
     */
    @Autowired
    public CustomerController(CustomerService customerService, JwtTokenUtil jwtTokenUtil) {
        this.customerService = customerService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Δημιουργεί έναν νέο πελάτη.
     *
     * @param customerDTO τα δεδομένα του πελάτη που πρόκειται να δημιουργηθεί.
     * @return το DTO του δημιουργημένου πελάτη.
     */
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    /**
     * Επιστρέφει όλους τους πελάτες που σχετίζονται με τον συνδεδεμένο χρήστη.
     *
     * @return λίστα με τους πελάτες που σχετίζονται με τον χρήστη.
     */
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    /**
     * Επιστρέφει έναν πελάτη με βάση το ID του.
     *
     * @param id το ID του πελάτη.
     * @return το DTO του πελάτη που βρέθηκε.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    /**
     * Ενημερώνει τα δεδομένα ενός πελάτη με βάση το ID του.
     *
     * @param id το ID του πελάτη που θα ενημερωθεί.
     * @param customerDTO τα ενημερωμένα δεδομένα του πελάτη.
     * @return το DTO του ενημερωμένου πελάτη.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Integer id, @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    /**
     * Διαγράφει έναν πελάτη με βάση το ID του.
     *
     * @param id το ID του πελάτη που θα διαγραφεί.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Αναζητά πελάτες με βάση το όνομα ή τμήμα του ονόματος.
     *
     * @param name το όνομα ή το τμήμα του ονόματος που θα αναζητηθεί.
     * @return λίστα με τους πελάτες που πληρούν τα κριτήρια.
     */
    @GetMapping("/search")
    public ResponseEntity<List<CustomerDTO>> searchCustomers(@RequestParam(required = false) String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        List<CustomerDTO> customers = customerService.searchCustomers(name);
        return ResponseEntity.ok(customers);
    }
}

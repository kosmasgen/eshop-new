package com.example.demo.controller;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    /**
     * Constructor για την εξάρτηση του CustomerService.
     *
     * @param customerService η υπηρεσία διαχείρισης πελατών.
     */

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Δημιουργεί έναν νέο πελάτη.
     *
     * @param customerDTO τα δεδομένα του πελάτη που πρόκειται να δημιουργηθεί.
     * @return το DTO του δημιουργημένου πελάτη.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Επιστρέφει HTTP Status 201 όταν δημιουργείται ένας πελάτης
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createCustomer(customerDTO);
    }


    /**
     * Επιστρέφει όλους τους πελάτες.
     *
     * @return λίστα με όλους τους πελάτες.
     */
    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    /**
     * Επιστρέφει έναν πελάτη με βάση το ID του.
     *
     * @param id το ID του πελάτη.
     * @return το DTO του πελάτη που βρέθηκε.
     */
    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }

    /**
     * Ενημερώνει τα δεδομένα ενός πελάτη με βάση το ID του.
     *
     * @param id το ID του πελάτη που θα ενημερωθεί.
     * @param customerDTO τα ενημερωμένα δεδομένα του πελάτη.
     * @return το DTO του ενημερωμένου πελάτη.
     */
    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable int id, @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(id, customerDTO);
    }

    /**
     * Διαγράφει έναν πελάτη με βάση το ID του.
     *
     * @param id το ID του πελάτη που θα διαγραφεί.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Επιστρέφει HTTP Status 204 για επιτυχημένη διαγραφή
    public void deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
    }

    /**
     * Αναζητά πελάτες με βάση το όνομα ή τμήμα του ονόματος.
     *
     * @param name το όνομα ή το τμήμα του ονόματος που θα αναζητηθεί.
     * @return λίστα με τους πελάτες που πληρούν τα κριτήρια.
     */
    @GetMapping("/search")
    public List<CustomerDTO> searchCustomers(@RequestParam String name) {
        return customerService.searchCustomers(name);
    }
}










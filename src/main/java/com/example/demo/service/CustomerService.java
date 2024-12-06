package com.example.demo.service;

import com.example.demo.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO createCustomer(CustomerDTO customerDTO); // Δημιουργία πελάτη
    List<CustomerDTO> getAllCustomers(); // Επιστροφή όλων των πελατών
    CustomerDTO getCustomerById(int id); // Εύρεση πελάτη με βάση το ID
    CustomerDTO updateCustomer(int id, CustomerDTO customerDTO); // Ενημέρωση πελάτη
    void deleteCustomer(int id); // Διαγραφή πελάτη
    List<CustomerDTO> searchCustomers(String name);
}

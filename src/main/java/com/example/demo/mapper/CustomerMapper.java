package com.example.demo.mapper;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.model.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper κλάση για τη χαρτογράφηση μεταξύ των οντοτήτων `Customer` και `CustomerDTO`.
 * Παρέχει μεθόδους για τη μετατροπή μεταξύ του μοντέλου και του DTO, καθώς και για την ενημέρωση μιας υπάρχουσας οντότητας.
 */
@Component
public class CustomerMapper {

    /**
     * Το ModelMapper που χρησιμοποιείται για τη χαρτογράφηση των δεδομένων.
     */
    private final ModelMapper modelMapper;

    /**
     * Κατασκευαστής που αρχικοποιεί το ModelMapper.
     */
    public CustomerMapper() {
        this.modelMapper = new ModelMapper();
    }

    /**
     * Μετατρέπει μια οντότητα `Customer` σε `CustomerDTO`.
     *
     * @param customer η οντότητα `Customer` που πρόκειται να μετατραπεί.
     * @return το αντίστοιχο DTO.
     */
    public CustomerDTO toDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    /**
     * Μετατρέπει ένα `CustomerDTO` σε οντότητα `Customer`.
     *
     * @param customerDTO το DTO που πρόκειται να μετατραπεί.
     * @return η αντίστοιχη οντότητα `Customer`.
     */
    public Customer toEntity(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }

    /**
     * Ενημερώνει μια υπάρχουσα οντότητα `Customer` με τα δεδομένα από ένα `CustomerDTO`.
     *
     * @param customerDTO το DTO που περιέχει τα νέα δεδομένα.
     * @param customer    η υπάρχουσα οντότητα που θα ενημερωθεί.
     * @throws IllegalArgumentException αν επιχειρηθεί αλλαγή του ID.
     */
    public void updateEntityFromDTO(CustomerDTO customerDTO, Customer customer) {
        if (customerDTO.getId() != null && !customerDTO.getId().equals(customer.getId())) {
            throw new IllegalArgumentException("Δεν επιτρέπεται αλλαγή του ID.");
        }
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setTelephone(customerDTO.getTelephone());
        customer.setAfm(customerDTO.getAfm());
        customer.setWholesale(customerDTO.isWholesale());
        customer.setBalance(customerDTO.getBalance());
    }
}

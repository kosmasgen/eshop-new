package com.example.demo.mapper;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.model.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper για τη χαρτογράφηση μεταξύ της οντότητας Customer και του DTO CustomerDTO.
 */
@Component
public class CustomerMapper {

    private final ModelMapper modelMapper;

    /**
     * Constructor που δέχεται ένα instance του ModelMapper.
     *
     * @param modelMapper το ModelMapper που θα χρησιμοποιηθεί.
     */
    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Μετατρέπει μια οντότητα Customer σε DTO CustomerDTO.
     *
     * @param customer η οντότητα Customer.
     * @return το αντίστοιχο CustomerDTO.
     */
    public CustomerDTO toDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    /**
     * Μετατρέπει ένα DTO CustomerDTO σε οντότητα Customer.
     *
     * @param customerDTO το DTO CustomerDTO.
     * @return η αντίστοιχη οντότητα Customer.
     */
    public Customer toEntity(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }

    /**
     * Ενημερώνει μια υπάρχουσα οντότητα Customer με δεδομένα από ένα CustomerDTO.
     *
     * @param customerDTO το DTO με τα νέα δεδομένα.
     * @param customer    η οντότητα που θα ενημερωθεί.
     */
    public void updateEntityFromDTO(CustomerDTO customerDTO, Customer customer) {
        if (customerDTO.getId() != null && !customerDTO.getId().equals(customer.getId())) {
            throw new IllegalArgumentException("Δεν επιτρέπεται αλλαγή του ID.");
        }
        modelMapper.map(customerDTO, customer);
    }
}

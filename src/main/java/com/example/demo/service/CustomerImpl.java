package com.example.demo.service;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Υλοποίηση της διεπαφής CustomerService.
 * Διαχειρίζεται τις λειτουργίες CRUD για τους πελάτες.
 */
@Service
public class CustomerImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerImpl.class);

    private final CustomerRepository customerRepository; // Το repository για την πρόσβαση στη βάση
    private final CustomerMapper customerMapper; // Το mapper για μετατροπές DTO <-> Entity

    /**
     * Κατασκευαστής με dependency injection.
     *
     * @param customerRepository το repository των πελατών.
     * @param customerMapper το mapper για μετατροπές DTO <-> Entity.
     */
    @Autowired
    public CustomerImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Δημιουργεί έναν νέο πελάτη.
     *
     * @param customerDTO τα δεδομένα του πελάτη.
     * @return το DTO του δημιουργημένου πελάτη.
     */
    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        logger.info("Ξεκινάει η δημιουργία πελάτη με AFM: {}", customerDTO.getAfm());
        if (customerRepository.existsByAfm(customerDTO.getAfm())) {
            logger.error("Το AFM {} υπάρχει ήδη στη βάση.", customerDTO.getAfm());
            throw new RuntimeException("Το AFM υπάρχει ήδη στη βάση.");
        }

        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);

        logger.info("Ο πελάτης δημιουργήθηκε με επιτυχία: {}", savedCustomer);
        return customerMapper.toDTO(savedCustomer);
    }

    /**
     * Επιστρέφει όλους τους πελάτες.
     *
     * @return λίστα με όλους τους πελάτες.
     */
    @Override
    public List<CustomerDTO> getAllCustomers() {
        logger.info("Αναζητούνται όλοι οι πελάτες...");
        List<Customer> customers = customerRepository.findAll();
        logger.debug("Βρέθηκαν {} πελάτες.", customers.size());
        return customers.stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Επιστρέφει έναν πελάτη με βάση το ID του.
     *
     * @param id το ID του πελάτη.
     * @return το DTO του πελάτη.
     */
    @Override
    public CustomerDTO getCustomerById(int id) {
        logger.info("Αναζήτηση πελάτη με ID: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Ο πελάτης με ID {} δεν βρέθηκε.", id);
                    return new RuntimeException("Ο πελάτης δεν βρέθηκε με το ID: " + id);
                });
        logger.debug("Βρέθηκε πελάτης: {}", customer);
        return customerMapper.toDTO(customer);
    }

    /**
     * Ενημερώνει έναν πελάτη με βάση το ID του.
     *
     * @param id το ID του πελάτη.
     * @param customerDTO τα νέα δεδομένα του πελάτη.
     * @return το ενημερωμένο DTO του πελάτη.
     */
    @Override
    public CustomerDTO updateCustomer(int id, CustomerDTO customerDTO) {
        logger.info("Αίτημα ενημέρωσης πελάτη με ID: {}", id);

        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Ο πελάτης με ID {} δεν βρέθηκε.", id);
                    return new RuntimeException("Ο πελάτης δεν βρέθηκε με το ID: " + id);
                });

        logger.debug("Πελάτης πριν την ενημέρωση: {}", existingCustomer);

        existingCustomer.setId(id); // Ασφαλίζουμε ότι το ID παραμένει το ίδιο
        customerMapper.updateEntityFromDTO(customerDTO, existingCustomer);

        Customer updatedCustomer = customerRepository.save(existingCustomer);

        logger.info("Ο πελάτης με ID {} ενημερώθηκε με επιτυχία.", id);
        return customerMapper.toDTO(updatedCustomer);
    }

    /**
     * Διαγράφει έναν πελάτη με βάση το ID του.
     *
     * @param id το ID του πελάτη.
     */
    @Override
    public void deleteCustomer(int id) {
        logger.info("Αίτημα διαγραφής πελάτη με ID: {}", id);
        if (!customerRepository.existsById(id)) {
            logger.error("Ο πελάτης με ID {} δεν βρέθηκε.", id);
            throw new RuntimeException("Ο πελάτης δεν βρέθηκε με το ID: " + id);
        }
        customerRepository.deleteById(id);
        logger.info("Ο πελάτης με ID {} διαγράφηκε με επιτυχία.", id);
    }

    /**
     * Αναζητά πελάτες με βάση το όνομα ή τμήμα του ονόματός τους.
     *
     * @param name το όνομα ή τμήμα του ονόματος.
     * @return λίστα πελατών που ταιριάζουν.
     */
    @Override
    public List<CustomerDTO> searchCustomers(String name) {
        logger.info("Αναζήτηση πελατών με όνομα ή τμήμα ονόματος: {}", name);
        List<Customer> customers = customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);

        if (customers.isEmpty()) {
            logger.error("Δεν βρέθηκαν πελάτες με το όνομα ή το τμήμα ονόματος: {}", name);
            throw new RuntimeException("Δεν βρέθηκαν πελάτες με το όνομα ή το τμήμα του ονόματος: " + name);
        }

        logger.debug("Βρέθηκαν {} πελάτες για το όνομα: {}", customers.size(), name);
        return customers.stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }
}

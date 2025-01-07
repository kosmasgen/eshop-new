package com.example.demo.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.model.Customer;
import com.example.demo.model.User;
import com.example.demo.model.Role;
import com.example.demo.model.ERole;
import com.example.demo.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Υλοποίηση της διεπαφής CustomerService.
 * Διαχειρίζεται τις λειτουργίες CRUD για τους πελάτες.
 */
@Service
public class CustomerImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerImpl.class);

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public CustomerImpl(CustomerRepository customerRepository, CustomerMapper customerMapper,
                        UserService userService, RoleService roleService) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        logger.info("Ξεκινάει η δημιουργία πελάτη με AFM: {}", customerDTO.getAfm());

        if (customerRepository.existsByAfm(customerDTO.getAfm())) {
            logger.error("Το AFM {} υπάρχει ήδη στη βάση.", customerDTO.getAfm());
            throw new RuntimeException("Το AFM υπάρχει ήδη στη βάση.");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role userRole = roleService.getRoleByName(ERole.ROLE_USER);
            user.setRoles(Set.of(userRole));
            userService.saveUser(user);
            logger.info("Ο ρόλος ROLE_USER προστέθηκε στον χρήστη: {}", username);
        }

        Customer customer = customerMapper.toEntity(customerDTO);
        customer.setUser(user);

        Customer savedCustomer = customerRepository.save(customer);

        logger.info("Ο πελάτης δημιουργήθηκε με επιτυχία: {}", savedCustomer);
        return customerMapper.toDTO(savedCustomer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        logger.info("Αναζητούνται όλοι οι πελάτες...");
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(int id) {
        logger.info("Αναζήτηση πελάτη με ID: {}", id);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Ο πελάτης με ID {} δεν βρέθηκε.", id);
                    return new ResourceNotFoundException(ErrorCode.CUSTOMER_NOT_FOUND, id);
                });

        return customerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(int id, CustomerDTO customerDTO) {
        logger.info("Αίτημα ενημέρωσης πελάτη με ID: {}", id);

        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Ο πελάτης με ID {} δεν βρέθηκε.", id);
                    return new ResourceNotFoundException(ErrorCode.CUSTOMER_NOT_FOUND, id);
                });

        customerMapper.updateEntityFromDTO(customerDTO, existingCustomer);

        Customer updatedCustomer = customerRepository.save(existingCustomer);

        logger.info("Ο πελάτης με ID {} ενημερώθηκε με επιτυχία.", id);
        return customerMapper.toDTO(updatedCustomer);
    }

    @Override
    public void deleteCustomer(int id) {
        logger.info("Αίτημα διαγραφής πελάτη με ID: {}", id);
        if (!customerRepository.existsById(id)) {
            logger.error("Ο πελάτης με ID {} δεν βρέθηκε.", id);
            throw new ResourceNotFoundException(ErrorCode.CUSTOMER_NOT_FOUND, id);
        }
        customerRepository.deleteById(id);
        logger.info("Ο πελάτης με ID {} διαγράφηκε με επιτυχία.", id);
    }

    @Override
    public List<CustomerDTO> searchCustomers(String name) {
        logger.info("Αναζήτηση πελατών με όνομα ή τμήμα ονόματος: {}", name);
        List<Customer> customers = customerRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);

        if (customers.isEmpty()) {
            logger.warn("Δεν βρέθηκαν πελάτες με το όνομα ή το τμήμα ονόματος: {}", name);
            return List.of();
        }

        return customers.stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> getCustomersByUser(String username) {
        logger.info("Αναζήτηση πελατών για τον χρήστη: {}", username);

        User user = userService.getUserByUsername(username);

        List<Customer> customers = customerRepository.findByUser(user);

        if (customers.isEmpty()) {
            logger.warn("Δεν βρέθηκαν πελάτες για τον χρήστη: {}", username);
            return List.of();
        }

        return customers.stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }
}

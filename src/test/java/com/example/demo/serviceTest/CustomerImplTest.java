package com.example.demo.serviceTest;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.model.Customer;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.ERole;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CustomerImpl;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerImplTest {

    @InjectMocks
    private CustomerImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void createCustomer_ShouldReturnCreatedCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setAfm("123456789");
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");

        Customer customer = new Customer();
        customer.setAfm("123456789");
        customer.setFirstName("John");
        customer.setLastName("Doe");

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1);

        User user = new User();
        user.setUsername("testuser");

        Role role = new Role();
        role.setName(ERole.ROLE_USER);

        when(authentication.getName()).thenReturn("testuser");
        when(userService.getUserByUsername("testuser")).thenReturn(user);
        when(roleService.getRoleByName(ERole.ROLE_USER)).thenReturn(role);
        when(customerRepository.existsByAfm("123456789")).thenReturn(false);
        when(customerMapper.toEntity(customerDTO)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(savedCustomer);
        when(customerMapper.toDTO(savedCustomer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.createCustomer(customerDTO);

        assertNotNull(result);
        verify(userService).getUserByUsername("testuser");
        verify(roleService).getRoleByName(ERole.ROLE_USER);
        verify(customerRepository).save(customer);
    }

    @Test
    void getAllCustomers_ShouldReturnCustomerList() {
        Customer customer = new Customer();
        customer.setId(1);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setAfm("123456789");

        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        List<CustomerDTO> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        verify(customerRepository).findAll();
    }

    @Test
    void getCustomerById_ShouldReturnCustomer() {
        Customer customer = new Customer();
        customer.setId(1);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setAfm("123456789");

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.getCustomerById(1);

        assertNotNull(result);
        verify(customerRepository).findById(1);
    }

    @Test
    void updateCustomer_ShouldUpdateCustomer() {
        Customer existingCustomer = new Customer();
        existingCustomer.setId(1);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John Updated");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1);
        updatedCustomer.setFirstName("John Updated");

        when(customerRepository.findById(1)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(existingCustomer)).thenReturn(updatedCustomer);
        when(customerMapper.toDTO(updatedCustomer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.updateCustomer(1, customerDTO);

        assertEquals("John Updated", result.getFirstName());
        verify(customerRepository).save(existingCustomer);
    }

    @Test
    void deleteCustomer_ShouldDeleteCustomer() {
        when(customerRepository.existsById(1)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(1);

        customerService.deleteCustomer(1);

        verify(customerRepository).deleteById(1);
    }

    @Test
    void searchCustomers_ShouldReturnMatchingCustomers() {
        Customer customer = new Customer();
        customer.setFirstName("John");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John");

        when(customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("John", "John"))
                .thenReturn(List.of(customer));
        when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        List<CustomerDTO> result = customerService.searchCustomers("John");

        assertEquals(1, result.size());
        verify(customerRepository).findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("John", "John");
    }

    @Test
    void getCustomersByUser_ShouldReturnCustomersForUser() {
        User user = new User();
        user.setUsername("testuser");

        Customer customer = new Customer();
        customer.setFirstName("John");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John");

        when(userService.getUserByUsername("testuser")).thenReturn(user);
        when(customerRepository.findByUser(user)).thenReturn(List.of(customer));
        when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        List<CustomerDTO> result = customerService.getCustomersByUser("testuser");

        assertEquals(1, result.size());
        verify(userService).getUserByUsername("testuser");
        verify(customerRepository).findByUser(user);
    }
}

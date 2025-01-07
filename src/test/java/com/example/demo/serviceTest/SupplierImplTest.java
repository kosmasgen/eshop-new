package com.example.demo.serviceTest;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.SupplierDTO;
import com.example.demo.mapper.SupplierMapper;
import com.example.demo.model.Supplier;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.service.OrderSupplierCommonService;
import com.example.demo.service.SupplierImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupplierImplTest {

    @InjectMocks
    private SupplierImpl supplierService;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private SupplierMapper supplierMapper;

    @Mock
    private OrderSupplierCommonService orderSupplierCommonService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void createSupplier_ShouldReturnCreatedSupplier() {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setFirstName("John");
        supplierDTO.setLastName("Doe");

        Supplier supplier = new Supplier();
        supplier.setFirstName("John");
        supplier.setLastName("Doe");

        Supplier savedSupplier = new Supplier();
        savedSupplier.setId(1);
        savedSupplier.setFirstName("John");
        savedSupplier.setLastName("Doe");

        when(supplierMapper.toEntity(supplierDTO)).thenReturn(supplier);
        when(supplierRepository.save(supplier)).thenReturn(savedSupplier);
        when(supplierMapper.toDTO(savedSupplier)).thenReturn(supplierDTO);

        SupplierDTO result = supplierService.createSupplier(supplierDTO);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        verify(supplierRepository).save(supplier);
    }

    @Test
    void updateSupplier_ShouldReturnUpdatedSupplier() {
        Integer id = 1;
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setFirstName("Updated John");

        Supplier existingSupplier = new Supplier();
        existingSupplier.setId(id);
        existingSupplier.setFirstName("John");

        Supplier updatedSupplier = new Supplier();
        updatedSupplier.setId(id);
        updatedSupplier.setFirstName("Updated John");

        when(supplierRepository.findById(id)).thenReturn(Optional.of(existingSupplier));
        when(supplierRepository.save(existingSupplier)).thenReturn(updatedSupplier);
        when(supplierMapper.toDTO(updatedSupplier)).thenReturn(supplierDTO);

        SupplierDTO result = supplierService.updateSupplier(id, supplierDTO);

        assertNotNull(result);
        assertEquals("Updated John", result.getFirstName());
        verify(supplierRepository).save(existingSupplier);
    }

    @Test
    void deleteSupplier_ShouldDeleteSupplier() {
        Integer id = 1;

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1);

        when(orderSupplierCommonService.searchOrdersBySupplierId(id)).thenReturn(List.of(orderDTO));
        doNothing().when(orderSupplierCommonService).deleteOrder(orderDTO.getId());
        doNothing().when(supplierRepository).deleteById(id);

        supplierService.deleteSupplier(id);

        verify(orderSupplierCommonService).searchOrdersBySupplierId(id);
        verify(orderSupplierCommonService).deleteOrder(orderDTO.getId());
        verify(supplierRepository).deleteById(id);
    }

    @Test
    void getSupplierById_ShouldReturnSupplier() {
        Integer id = 1;
        Supplier supplier = new Supplier();
        supplier.setId(id);
        supplier.setFirstName("John");

        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setFirstName("John");

        when(supplierRepository.findById(id)).thenReturn(Optional.of(supplier));
        when(supplierMapper.toDTO(supplier)).thenReturn(supplierDTO);

        SupplierDTO result = supplierService.getSupplierById(id);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(supplierRepository).findById(id);
    }

    @Test
    void getAllSuppliers_ShouldReturnSupplierList() {
        Supplier supplier = new Supplier();
        supplier.setFirstName("John");

        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setFirstName("John");

        when(supplierRepository.findAll()).thenReturn(List.of(supplier));
        when(supplierMapper.toDTO(supplier)).thenReturn(supplierDTO);

        List<SupplierDTO> result = supplierService.getAllSuppliers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        verify(supplierRepository).findAll();
    }

    @Test
    void findSuppliersByName_ShouldReturnSuppliers() {
        String name = "John";
        Supplier supplier = new Supplier();
        supplier.setFirstName("John");

        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setFirstName("John");

        when(supplierRepository.findByFirstNameContainingIgnoreCase(name)).thenReturn(List.of(supplier));
        when(supplierMapper.toDTO(supplier)).thenReturn(supplierDTO);

        List<SupplierDTO> result = supplierService.findSuppliersByName(name);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        verify(supplierRepository).findByFirstNameContainingIgnoreCase(name);
    }

    @Test
    void findSuppliersByLocation_ShouldReturnSuppliers() {
        String location = "Athens";
        Supplier supplier = new Supplier();
        supplier.setLocation("Athens");

        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setLocation("Athens");

        when(supplierRepository.findByLocationContainingIgnoreCase(location)).thenReturn(List.of(supplier));
        when(supplierMapper.toDTO(supplier)).thenReturn(supplierDTO);

        List<SupplierDTO> result = supplierService.findSuppliersByLocation(location);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Athens", result.get(0).getLocation());
        verify(supplierRepository).findByLocationContainingIgnoreCase(location);
    }

    @Test
    void calculateTurnover_ShouldReturnTotalTurnover() {
        Integer supplierId = 1;
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTotalPrice(100.0);

        when(orderSupplierCommonService.getOrdersBySupplierAndDate(supplierId, startDate, endDate))
                .thenReturn(List.of(orderDTO));

        Double turnover = supplierService.calculateTurnover(supplierId, startDate, endDate);

        assertNotNull(turnover);
        assertEquals(100.0, turnover);
        verify(orderSupplierCommonService).getOrdersBySupplierAndDate(supplierId, startDate, endDate);
    }
}

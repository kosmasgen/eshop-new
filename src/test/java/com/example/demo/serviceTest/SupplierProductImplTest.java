package com.example.demo.serviceTest;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.SupplierDTO;
import com.example.demo.dto.SupplierProductDTO;
import com.example.demo.mapper.SupplierProductMapper;
import com.example.demo.model.SupplierProduct;
import com.example.demo.repository.SupplierProductRepository;
import com.example.demo.service.SupplierProductImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupplierProductImplTest {

    @InjectMocks
    private SupplierProductImpl supplierProductService;

    @Mock
    private SupplierProductRepository supplierProductRepository;

    @Mock
    private SupplierProductMapper supplierProductMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSupplierProduct_ShouldReturnCreatedSupplierProduct() {
        // Δεδομένα
        SupplierDTO supplierDTO = new SupplierDTO(1, "John", "Doe", "123456789", "123456789", "Athens");

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(2);
        productDTO.setProductName("Product A");
        productDTO.setType("Category A");
        productDTO.setPrice(10.0);
        productDTO.setSupplier(supplierDTO);
        productDTO.setQuantity(100);
        productDTO.setUuid("uuid-12345");

        SupplierProductDTO supplierProductDTO = new SupplierProductDTO();
        supplierProductDTO.setSupplier(supplierDTO);
        supplierProductDTO.setProduct(productDTO);
        supplierProductDTO.setQuantity(100);

        SupplierProduct supplierProduct = new SupplierProduct();
        supplierProduct.setId(1);
        supplierProduct.setQuantity(100);

        SupplierProduct savedSupplierProduct = new SupplierProduct();
        savedSupplierProduct.setId(1);
        savedSupplierProduct.setQuantity(100);

        // Mock συμπεριφορές
        when(supplierProductMapper.toEntity(supplierProductDTO)).thenReturn(supplierProduct);
        when(supplierProductRepository.save(supplierProduct)).thenReturn(savedSupplierProduct);
        when(supplierProductMapper.toDTO(savedSupplierProduct)).thenReturn(supplierProductDTO);

        // Εκτέλεση
        SupplierProductDTO result = supplierProductService.createSupplierProduct(supplierProductDTO);

        // Έλεγχοι
        assertNotNull(result);
        assertEquals(supplierDTO.getId(), result.getSupplier().getId());
        assertEquals(productDTO.getId(), result.getProduct().getId());
        assertEquals(100, result.getQuantity());
        verify(supplierProductRepository).save(supplierProduct);
    }

    @Test
    void getAllSupplierProducts_ShouldReturnAllSupplierProducts() {
        // Δεδομένα
        SupplierProduct supplierProduct = new SupplierProduct();
        supplierProduct.setId(1);
        supplierProduct.setQuantity(50);

        SupplierProductDTO supplierProductDTO = new SupplierProductDTO();
        supplierProductDTO.setQuantity(50);

        // Mock συμπεριφορές
        when(supplierProductRepository.findAll()).thenReturn(List.of(supplierProduct));
        when(supplierProductMapper.toDTO(supplierProduct)).thenReturn(supplierProductDTO);

        // Εκτέλεση
        List<SupplierProductDTO> result = supplierProductService.getAllSupplierProducts();

        // Έλεγχοι
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(50, result.get(0).getQuantity());
        verify(supplierProductRepository).findAll();
    }

    @Test
    void getSupplierProductById_ShouldReturnSupplierProduct() {
        // Δεδομένα
        int supplierProductId = 1;
        SupplierProduct supplierProduct = new SupplierProduct();
        supplierProduct.setId(supplierProductId);
        supplierProduct.setQuantity(100);

        SupplierProductDTO supplierProductDTO = new SupplierProductDTO();
        supplierProductDTO.setQuantity(100);

        // Mock συμπεριφορές
        when(supplierProductRepository.findById(supplierProductId)).thenReturn(Optional.of(supplierProduct));
        when(supplierProductMapper.toDTO(supplierProduct)).thenReturn(supplierProductDTO);

        // Εκτέλεση
        SupplierProductDTO result = supplierProductService.getSupplierProductById(supplierProductId);

        // Έλεγχοι
        assertNotNull(result);
        assertEquals(100, result.getQuantity());
        verify(supplierProductRepository).findById(supplierProductId);
    }

    @Test
    void updateSupplierProduct_ShouldReturnUpdatedSupplierProduct() {
        // Δεδομένα
        int supplierProductId = 1;

        SupplierDTO supplierDTO = new SupplierDTO(1, "John", "Doe", "123456789", "123456789", "Athens");

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(2);
        productDTO.setProductName("Updated Product");
        productDTO.setType("Updated Category");
        productDTO.setPrice(20.0);
        productDTO.setSupplier(supplierDTO);
        productDTO.setQuantity(200);
        productDTO.setUuid("uuid-54321");

        SupplierProductDTO supplierProductDTO = new SupplierProductDTO();
        supplierProductDTO.setSupplier(supplierDTO);
        supplierProductDTO.setProduct(productDTO);
        supplierProductDTO.setQuantity(200);

        SupplierProduct existingSupplierProduct = new SupplierProduct();
        existingSupplierProduct.setId(supplierProductId);
        existingSupplierProduct.setQuantity(100);

        SupplierProduct updatedSupplierProduct = new SupplierProduct();
        updatedSupplierProduct.setId(supplierProductId);
        updatedSupplierProduct.setQuantity(200);

        // Mock συμπεριφορές
        when(supplierProductRepository.findById(supplierProductId)).thenReturn(Optional.of(existingSupplierProduct));
        when(supplierProductRepository.save(existingSupplierProduct)).thenReturn(updatedSupplierProduct);
        when(supplierProductMapper.toDTO(updatedSupplierProduct)).thenReturn(supplierProductDTO);

        // Εκτέλεση
        SupplierProductDTO result = supplierProductService.updateSupplierProduct(supplierProductId, supplierProductDTO);

        // Έλεγχοι
        assertNotNull(result);
        assertEquals(200, result.getQuantity());
        verify(supplierProductRepository).save(existingSupplierProduct);
    }

    @Test
    void deleteSupplierProduct_ShouldDeleteSupplierProduct() {
        // Δεδομένα
        int supplierProductId = 1;

        // Mock συμπεριφορές
        when(supplierProductRepository.existsById(supplierProductId)).thenReturn(true);
        doNothing().when(supplierProductRepository).deleteById(supplierProductId);

        // Εκτέλεση
        supplierProductService.deleteSupplierProduct(supplierProductId);

        // Έλεγχοι
        verify(supplierProductRepository).deleteById(supplierProductId);
    }
}

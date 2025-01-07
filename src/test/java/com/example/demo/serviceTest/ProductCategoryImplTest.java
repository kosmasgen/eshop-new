package com.example.demo.serviceTest;

import com.example.demo.dto.ProductCategoryDTO;
import com.example.demo.mapper.ProductCategoryMapper;
import com.example.demo.model.ProductCategory;
import com.example.demo.repository.ProductCategoryRepository;
import com.example.demo.service.ProductCategoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductCategoryImplTest {

    @InjectMocks
    private ProductCategoryImpl productCategoryService;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private ProductCategoryMapper productCategoryMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProductCategory_ShouldReturnCreatedProductCategory() {
        // Δεδομένα
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        productCategoryDTO.setName("Electronics");

        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(1);
        productCategory.setName("Electronics");

        ProductCategoryDTO savedProductCategoryDTO = new ProductCategoryDTO();
        savedProductCategoryDTO.setId(1);
        savedProductCategoryDTO.setName("Electronics");

        // Mock συμπεριφορές
        when(productCategoryMapper.toEntity(productCategoryDTO)).thenReturn(productCategory);
        when(productCategoryRepository.save(productCategory)).thenReturn(productCategory);
        when(productCategoryMapper.toDTO(productCategory)).thenReturn(savedProductCategoryDTO);

        // Εκτέλεση
        ProductCategoryDTO result = productCategoryService.createProductCategory(productCategoryDTO);

        // Έλεγχοι
        assertNotNull(result);
        assertEquals("Electronics", result.getName());
        verify(productCategoryRepository).save(any(ProductCategory.class));
    }

    @Test
    void getAllProductCategories_ShouldReturnAllCategories() {
        // Δεδομένα
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setId(1);
        productCategory1.setName("Electronics");

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setId(2);
        productCategory2.setName("Clothing");

        // Mock συμπεριφορές
        when(productCategoryRepository.findAll()).thenReturn(List.of(productCategory1, productCategory2));
        when(productCategoryMapper.toDTO(any(ProductCategory.class))).thenReturn(new ProductCategoryDTO());

        // Εκτέλεση
        var result = productCategoryService.getAllProductCategories();

        // Έλεγχοι
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productCategoryRepository).findAll();
    }

    @Test
    void getProductCategoryById_ShouldReturnProductCategory() {
        // Δεδομένα
        int id = 1;
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(id);
        productCategory.setName("Electronics");

        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        productCategoryDTO.setId(id);
        productCategoryDTO.setName("Electronics");

        // Mock συμπεριφορές
        when(productCategoryRepository.findById(id)).thenReturn(Optional.of(productCategory));
        when(productCategoryMapper.toDTO(productCategory)).thenReturn(productCategoryDTO);

        // Εκτέλεση
        ProductCategoryDTO result = productCategoryService.getProductCategoryById(id);

        // Έλεγχοι
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Electronics", result.getName());
        verify(productCategoryRepository).findById(id);
    }

    @Test
    void updateProductCategory_ShouldReturnUpdatedProductCategory() {
        // Δεδομένα
        int id = 1;
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        productCategoryDTO.setName("Updated Category");

        ProductCategory existingCategory = new ProductCategory();
        existingCategory.setId(id);
        existingCategory.setName("Electronics");

        ProductCategory updatedCategory = new ProductCategory();
        updatedCategory.setId(id);
        updatedCategory.setName("Updated Category");

        ProductCategoryDTO updatedCategoryDTO = new ProductCategoryDTO();
        updatedCategoryDTO.setId(id);
        updatedCategoryDTO.setName("Updated Category");

        // Mock συμπεριφορές
        when(productCategoryRepository.findById(id)).thenReturn(Optional.of(existingCategory));
        when(productCategoryRepository.save(existingCategory)).thenReturn(updatedCategory);
        when(productCategoryMapper.toDTO(updatedCategory)).thenReturn(updatedCategoryDTO);

        // Εκτέλεση
        ProductCategoryDTO result = productCategoryService.updateProductCategory(id, productCategoryDTO);

        // Έλεγχοι
        assertNotNull(result);
        assertEquals("Updated Category", result.getName());
        verify(productCategoryRepository).save(existingCategory);
    }

    @Test
    void deleteProductCategory_ShouldDeleteProductCategory() {
        // Δεδομένα
        int id = 1;
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(id);

        // Mock συμπεριφορές
        when(productCategoryRepository.existsById(id)).thenReturn(true);
        doNothing().when(productCategoryRepository).deleteById(id);

        // Εκτέλεση
        productCategoryService.deleteProductCategory(id);

        // Έλεγχοι
        verify(productCategoryRepository).deleteById(id);
    }
}

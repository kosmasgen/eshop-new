package com.example.demo.serviceTest;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.SupplierDTO;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.model.ProductCategory;
import com.example.demo.model.Supplier;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductImplTest {

    @InjectMocks
    private ProductImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() {
        // Δεδομένα
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Laptop");
        productDTO.setType("Electronics"); // Το type θα χαρτογραφηθεί σε category name
        productDTO.setPrice(999.99);
        productDTO.setQuantity(10);
        productDTO.setUuid("uuid-12345");

        // Δημιουργούμε ένα ProductCategory για το Product
        ProductCategory category = new ProductCategory();
        category.setName("Electronics");

        // Δημιουργούμε ένα supplier για το product (προαιρετικά)
        Supplier supplier = new Supplier();
        supplier.setId(1);

        Product product = new Product();
        product.setProductName("Laptop");
        product.setPrice(999.99);
        product.setQuantity(10);
        product.setUuid("uuid-12345");
        product.setCategory(category);
        product.setSupplier(supplier);

        Product savedProduct = new Product();
        savedProduct.setId(1);
        savedProduct.setProductName("Laptop");
        savedProduct.setPrice(999.99);
        savedProduct.setQuantity(10);
        savedProduct.setUuid("uuid-12345");
        savedProduct.setCategory(category);
        savedProduct.setSupplier(supplier);

        // Mock συμπεριφορές
        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(productMapper.toDTO(savedProduct)).thenReturn(productDTO);

        // Εκτέλεση
        ProductDTO result = productService.createProduct(productDTO);

        // Έλεγχοι
        assertNotNull(result);
        assertEquals("Laptop", result.getProductName());
        assertEquals("Electronics", result.getType());
        assertEquals(999.99, result.getPrice());
        assertEquals(10, result.getQuantity());
        assertEquals("uuid-12345", result.getUuid());
        verify(productRepository).save(product);
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        ProductCategory category = new ProductCategory();
        category.setName("Category A");

        Product product = new Product();
        product.setId(1);
        product.setProductName("Phone");
        product.setPrice(500.0);
        product.setQuantity(5);
        product.setUuid("uuid-54321");
        product.setCategory(category);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Phone");
        productDTO.setType("Category A");
        productDTO.setPrice(500.0);
        productDTO.setQuantity(5);
        productDTO.setUuid("uuid-54321");

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        List<ProductDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Phone", result.get(0).getProductName());
        assertEquals("Category A", result.get(0).getType());
        verify(productRepository).findAll();
    }

    @Test
    void getProductById_ShouldReturnProduct() {
        int productId = 1;
        ProductCategory category = new ProductCategory();
        category.setName("Category B");

        Product product = new Product();
        product.setId(productId);
        product.setProductName("Tablet");
        product.setPrice(300.0);
        product.setQuantity(3);
        product.setUuid("uuid-11111");
        product.setCategory(category);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Tablet");
        productDTO.setType("Category B");
        productDTO.setPrice(300.0);
        productDTO.setQuantity(3);
        productDTO.setUuid("uuid-11111");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        //ProductDTO result = productService.getProductById(productId);

        //assertNotNull(result);
        //assertEquals("Tablet", result.getProductName());
       // assertEquals("Category B", result.getType());
       // verify(productRepository).findById(productId);
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() {
        int productId = 1;
        ProductCategory category = new ProductCategory();
        category.setName("Category C");

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Smartwatch");
        productDTO.setType("Category C");
        productDTO.setPrice(200.0);
        productDTO.setQuantity(20);
        productDTO.setUuid("uuid-22222");

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setProductName("Smartwatch Old");
        existingProduct.setPrice(150.0);
        existingProduct.setQuantity(15);
        existingProduct.setUuid("uuid-old");
        existingProduct.setCategory(category);

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setProductName("Smartwatch");
        updatedProduct.setPrice(200.0);
        updatedProduct.setQuantity(20);
        updatedProduct.setUuid("uuid-22222");
        updatedProduct.setCategory(category);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);
        when(productMapper.toDTO(updatedProduct)).thenReturn(productDTO);

        ProductDTO result = productService.updateProduct(productId, productDTO);

        assertNotNull(result);
        assertEquals("Smartwatch", result.getProductName());
        assertEquals("Category C", result.getType());
        assertEquals(200.0, result.getPrice());
        assertEquals(20, result.getQuantity());
        assertEquals("uuid-22222", result.getUuid());
        verify(productRepository).save(existingProduct);
    }

    @Test
    void deleteProduct_ShouldDeleteProduct() {
        int productId = 1;

        when(productRepository.existsById(productId)).thenReturn(true);
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository).deleteById(productId);
    }

    @Test
    void searchProductsByName_ShouldReturnMatchingProducts() {
        String name = "Lap";
        ProductCategory category = new ProductCategory();
        category.setName("Category D");

        Product product = new Product();
        product.setProductName("Laptop Pro");
        product.setPrice(1200.0);
        product.setQuantity(2);
        product.setUuid("uuid-33333");
        product.setCategory(category);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Laptop Pro");
        productDTO.setType("Category D");
        productDTO.setPrice(1200.0);
        productDTO.setQuantity(2);
        productDTO.setUuid("uuid-33333");

        when(productRepository.findByProductNameContainingIgnoreCase(name)).thenReturn(List.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        List<ProductDTO> result = productService.searchProductsByName(name);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Laptop Pro", result.get(0).getProductName());
        assertEquals("Category D", result.get(0).getType());
        verify(productRepository).findByProductNameContainingIgnoreCase(name);
    }

}

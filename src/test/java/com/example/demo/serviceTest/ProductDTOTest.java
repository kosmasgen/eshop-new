package com.example.demo.serviceTest;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.SupplierDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductDTOTest {

    private static final Logger logger = Logger.getLogger(ProductDTOTest.class.getName());
    private Validator validator;

    @BeforeEach
    public void setUp() {
        Locale.setDefault(new Locale("el"));
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        logger.info("Validator initialized with locale: " + Locale.getDefault());
    }

    @Test
    public void whenAllFieldsAreValid_thenValidationPasses() {
        logger.info("Executing: whenAllFieldsAreValid_thenValidationPasses");
        ProductDTO productDTO = createValidProductDTO();
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
        violations.forEach(v -> logger.warning("Validation error: " + v.getMessage()));
        assertTrue(violations.isEmpty(), "Expected no validation errors, but found: " + violations);
        logger.info("Test passed: whenAllFieldsAreValid_thenValidationPasses");
    }

    @Test
    public void whenProductNameIsNull_thenValidationFails() {
        logger.info("Executing: whenProductNameIsNull_thenValidationFails");
        ProductDTO productDTO = createValidProductDTO();
        productDTO.setProductName(null);

        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
        violations.forEach(v -> logger.warning("Validation error: " + v.getMessage()));

        assertEquals(1, violations.size());
        ConstraintViolation<ProductDTO> violation = violations.iterator().next();
        logger.info("Validation message: " + violation.getMessage());
        assertEquals("Το όνομα του προϊόντος δεν μπορεί να είναι κενό.", violation.getMessage());
    }

    @Test
    public void whenPriceIsNegative_thenValidationFails() {
        logger.info("Executing: whenPriceIsNegative_thenValidationFails");
        ProductDTO productDTO = createValidProductDTO();
        productDTO.setPrice(-10.0);

        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
        violations.forEach(v -> logger.warning("Validation error: " + v.getMessage()));

        assertEquals(1, violations.size());
        ConstraintViolation<ProductDTO> violation = violations.iterator().next();
        logger.info("Validation message: " + violation.getMessage());
        assertEquals("Η τιμή του προϊόντος πρέπει να είναι θετικός αριθμός.", violation.getMessage());
    }

    @Test
    public void whenUuidIsInvalid_thenValidationFails() {
        logger.info("Executing: whenUuidIsInvalid_thenValidationFails");
        ProductDTO productDTO = createValidProductDTO();
        productDTO.setUuid("invalid-uuid");

        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
        violations.forEach(v -> logger.warning("Validation error: " + v.getMessage()));

        assertEquals(1, violations.size());
        ConstraintViolation<ProductDTO> violation = violations.iterator().next();
        logger.info("Validation message: " + violation.getMessage());
        assertEquals("Το UUID του προϊόντος πρέπει να έχει έγκυρη μορφή UUID (36 χαρακτήρες).", violation.getMessage());
    }

    @Test
    public void whenSupplierIsNull_thenValidationFails() {
        logger.info("Executing: whenSupplierIsNull_thenValidationFails");
        ProductDTO productDTO = createValidProductDTO();
        productDTO.setSupplier(null);

        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
        violations.forEach(v -> logger.warning("Validation error: " + v.getMessage()));

        assertEquals(1, violations.size());
        ConstraintViolation<ProductDTO> violation = violations.iterator().next();
        logger.info("Validation message: " + violation.getMessage());
        assertEquals("Ο προμηθευτής του προϊόντος δεν μπορεί να είναι κενός.", violation.getMessage());
    }

    private ProductDTO createValidProductDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Valid Product");
        productDTO.setType("Valid Type");
        productDTO.setPrice(100.0);
        productDTO.setQuantity(10);
        productDTO.setUuid("123e4567-e89b-12d3-a456-426614174000");

        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setId(1);
        supplierDTO.setFirstName("John");
        supplierDTO.setLastName("Doe");
        supplierDTO.setTelephone("1234567890");
        supplierDTO.setAfm("123456789");
        supplierDTO.setLocation("Athens");
        productDTO.setSupplier(supplierDTO);

        return productDTO;
    }
}

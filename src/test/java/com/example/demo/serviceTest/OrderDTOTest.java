package com.example.demo.serviceTest;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.SupplierDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class OrderDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        Locale.setDefault(Locale.ENGLISH); // Ορισμός Locale στα Αγγλικά
        try (ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ResourceBundleMessageInterpolator())
                .buildValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void whenAllFieldsAreValid_thenValidationSucceeds() {
        SupplierDTO supplier = new SupplierDTO(1, "John", "Doe", "1234567890", "123456789", "New York");
        ProductDTO product = new ProductDTO(1, "Product", "Type", 100.0, supplier, 10, "123e4567-e89b-12d3-a456-426614174000");

        OrderDTO order = new OrderDTO();
        order.setId(1);
        order.setSupplier(supplier);
        order.setProduct(product);
        order.setQuantity(10);
        order.setPrice(100.0);
        order.setTotalPrice(1000.0);
        order.setCreatedAt(LocalDateTime.now());

        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(order);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenSupplierIsNull_thenValidationFails() {
        ProductDTO product = new ProductDTO(1, "Product", "Type", 100.0, new SupplierDTO(1, "John", "Doe", "1234567890", "123456789", "New York"), 10, "123e4567-e89b-12d3-a456-426614174000");

        OrderDTO order = new OrderDTO();
        order.setProduct(product);
        order.setQuantity(10);
        order.setPrice(100.0);
        order.setTotalPrice(1000.0);
        order.setCreatedAt(LocalDateTime.now());

        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(order);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The order's supplier cannot be null.");
    }

    @Test
    void whenProductIsNull_thenValidationFails() {
        SupplierDTO supplier = new SupplierDTO(1, "John", "Doe", "1234567890", "123456789", "New York");

        OrderDTO order = new OrderDTO();
        order.setSupplier(supplier);
        order.setQuantity(10);
        order.setPrice(100.0);
        order.setTotalPrice(1000.0);
        order.setCreatedAt(LocalDateTime.now());

        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(order);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The order's product cannot be null.");
    }

    @Test
    void whenQuantityIsNull_thenValidationFails() {
        SupplierDTO supplier = new SupplierDTO(1, "John", "Doe", "1234567890", "123456789", "New York");
        ProductDTO product = new ProductDTO(1, "Product", "Type", 100.0, supplier, 10, "123e4567-e89b-12d3-a456-426614174000");

        OrderDTO order = new OrderDTO();
        order.setSupplier(supplier);
        order.setProduct(product);
        order.setPrice(100.0);
        order.setTotalPrice(1000.0);
        order.setCreatedAt(LocalDateTime.now());

        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(order);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The order's quantity cannot be null.");
    }

    @Test
    void whenPriceIsNull_thenValidationFails() {
        SupplierDTO supplier = new SupplierDTO(1, "John", "Doe", "1234567890", "123456789", "New York");
        ProductDTO product = new ProductDTO(1, "Product", "Type", 100.0, supplier, 10, "123e4567-e89b-12d3-a456-426614174000");

        OrderDTO order = new OrderDTO();
        order.setSupplier(supplier);
        order.setProduct(product);
        order.setQuantity(10);
        order.setTotalPrice(1000.0);
        order.setCreatedAt(LocalDateTime.now());

        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(order);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The order's price cannot be null.");
    }

    @Test
    void whenTotalPriceIsNull_thenValidationFails() {
        SupplierDTO supplier = new SupplierDTO(1, "John", "Doe", "1234567890", "123456789", "New York");
        ProductDTO product = new ProductDTO(1, "Product", "Type", 100.0, supplier, 10, "123e4567-e89b-12d3-a456-426614174000");

        OrderDTO order = new OrderDTO();
        order.setSupplier(supplier);
        order.setProduct(product);
        order.setQuantity(10);
        order.setPrice(100.0);
        order.setCreatedAt(LocalDateTime.now());

        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(order);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The order's total price cannot be null.");
    }

    @Test
    void whenSetProductId_thenProductIsCreatedWithId() {
        OrderDTO order = new OrderDTO();
        order.setProductId(1);

        assertThat(order.getProduct()).isNotNull();
        assertThat(order.getProduct().getId()).isEqualTo(1);
    }

    @Test
    void whenSetSupplierId_thenSupplierIsCreatedWithId() {
        OrderDTO order = new OrderDTO();
        order.setSupplierId(1);

        assertThat(order.getSupplier()).isNotNull();
        assertThat(order.getSupplier().getId()).isEqualTo(1);
    }
}

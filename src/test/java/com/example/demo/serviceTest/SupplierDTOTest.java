package com.example.demo.serviceTest;

import com.example.demo.dto.SupplierDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SupplierDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        Locale.setDefault(Locale.ENGLISH); // Ορισμός γλώσσας σε Αγγλικά
        try (ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ResourceBundleMessageInterpolator())
                .buildValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void whenFirstNameIsNull_thenValidationFails() {
        SupplierDTO supplier = new SupplierDTO();
        supplier.setLastName("Doe");
        supplier.setTelephone("123456789");
        supplier.setAfm("123456789");
        supplier.setLocation("Athens");

        Set<ConstraintViolation<SupplierDTO>> violations = validator.validate(supplier);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The first name cannot be null.");
    }

    @Test
    void whenLastNameIsTooLong_thenValidationFails() {
        SupplierDTO supplier = new SupplierDTO();
        supplier.setFirstName("John");
        supplier.setLastName("ThisLastNameIsWayTooLong");
        supplier.setTelephone("123456789");
        supplier.setAfm("123456789");
        supplier.setLocation("Athens");

        Set<ConstraintViolation<SupplierDTO>> violations = validator.validate(supplier);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The last name must have at most 15 characters.");
    }

    @Test
    void whenTelephoneIsInvalid_thenValidationFails() {
        SupplierDTO supplier = new SupplierDTO();
        supplier.setFirstName("John");
        supplier.setLastName("Doe");
        supplier.setTelephone("InvalidPhone123"); // Μη έγκυρο τηλέφωνο
        supplier.setAfm("123456789");
        supplier.setLocation("Athens");

        Set<ConstraintViolation<SupplierDTO>> violations = validator.validate(supplier);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The telephone must contain only numbers.");
    }

    @Test
    void whenAfmIsInvalid_thenValidationFails() {
        SupplierDTO supplier = new SupplierDTO();
        supplier.setFirstName("John");
        supplier.setLastName("Doe");
        supplier.setTelephone("123456789");
        supplier.setAfm("1234ABCD9"); // Μη αριθμητικό ΑΦΜ
        supplier.setLocation("Athens");

        Set<ConstraintViolation<SupplierDTO>> violations = validator.validate(supplier);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The VAT number must contain exactly 9 digits.");
    }

    @Test
    void whenLocationIsTooLong_thenValidationFails() {
        SupplierDTO supplier = new SupplierDTO();
        supplier.setFirstName("John");
        supplier.setLastName("Doe");
        supplier.setTelephone("123456789");
        supplier.setAfm("123456789");
        supplier.setLocation("ThisLocationIsWisLocationIsWayTooLongAndExceedsTheMaximumAllowedCharactayTooLongAndExceedsTheMaximumAllowedCharactersLengthForTestingPurpose");

        Set<ConstraintViolation<SupplierDTO>> violations = validator.validate(supplier);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The location must have at most 100 characters.");
    }

    @Test
    void whenAllFieldsAreValid_thenValidationSucceeds() {
        SupplierDTO supplier = new SupplierDTO();
        supplier.setFirstName("John");
        supplier.setLastName("Doe");
        supplier.setTelephone("123456789");
        supplier.setAfm("123456789");
        supplier.setLocation("Athens");

        Set<ConstraintViolation<SupplierDTO>> violations = validator.validate(supplier);

        assertThat(violations).isEmpty();
    }
}

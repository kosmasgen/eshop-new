package com.example.demo.serviceTest;

import com.example.demo.dto.CustomerDTO;
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

public class CustomerDTOTest {

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
        CustomerDTO customer = new CustomerDTO();
        customer.setLastName("Doe");
        customer.setTelephone("123456789");
        customer.setAfm("123456789");
        customer.setWholesale(true);
        customer.setBalance(100.0);

        Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(customer);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The first name cannot be null.");
    }

    @Test
    void whenLastNameIsTooLong_thenValidationFails() {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("John");
        customer.setLastName("ThisLastNameIsWayTooLong");
        customer.setTelephone("123456789");
        customer.setAfm("123456789");
        customer.setWholesale(true);
        customer.setBalance(100.0);

        Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(customer);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The last name must have at most 15 characters.");
    }

    @Test
    void whenTelephoneIsInvalid_thenValidationFails() {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setTelephone("123456789012345"); // Τηλέφωνο μεγαλύτερο από 13 χαρακτήρες
        customer.setAfm("123456789");
        customer.setWholesale(true);
        customer.setBalance(100.0);

        Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(customer);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The telephone must have at most 13 characters.");
    }

    @Test
    void whenAfmIsInvalid_thenValidationFails() {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setTelephone("123456789");
        customer.setAfm("1234ABCD9"); // ΑΦΜ με μη αριθμητικούς χαρακτήρες
        customer.setWholesale(true);
        customer.setBalance(100.0);

        Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(customer);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The VAT number must contain only numbers.");
    }

    @Test
    void whenAllFieldsAreValid_thenValidationSucceeds() {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setTelephone("123456789");
        customer.setAfm("123456789");
        customer.setWholesale(true);
        customer.setBalance(100.0);

        Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(customer);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenBalanceIsNegative_thenValidationFails() {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setTelephone("123456789");
        customer.setAfm("123456789");
        customer.setWholesale(true);
        customer.setBalance(-100.0); // Αρνητικό υπόλοιπο

        Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(customer);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The balance must be a positive number."); // Ενημέρωση για το μήνυμα
    }
}

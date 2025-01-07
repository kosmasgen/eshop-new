package com.example.demo.serviceTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.demo.dto.UserDTO;

import java.util.Set;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        Locale.setDefault(Locale.ENGLISH); // Ορισμός γλώσσας επικύρωσης σε Αγγλικά
        try (ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("ValidationMessages")))
                .buildValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void whenPasswordIsNull_thenValidationFails() {
        UserDTO user = new UserDTO();
        user.setUsername("validUsername");
        user.setEmail("valid@example.com");
        user.setPassword(null);

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The password cannot be null.");
    }

    @Test
    void whenPasswordIsTooShort_thenValidationFails() {
        UserDTO user = new UserDTO();
        user.setUsername("validUsername");
        user.setEmail("valid@example.com");
        user.setPassword("12345");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The password must be between 8 and 20 characters long.");
    }

    @Test
    void whenPasswordDoesNotMeetPattern_thenValidationFails() {
        UserDTO user = new UserDTO();
        user.setUsername("validUsername");
        user.setEmail("valid@example.com");
        user.setPassword("password123");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The password must contain at least one number, one special character, and be between 8 and 20 characters long.");
    }

    @Test
    void whenUsernameIsNull_thenValidationFails() {
        UserDTO user = new UserDTO();
        user.setUsername(null);
        user.setEmail("valid@example.com");
        user.setPassword("ValidPass123!");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The username cannot be null.");
    }

    @Test
    void whenUsernameExceedsMaxLength_thenValidationFails() {
        UserDTO user = new UserDTO();
        user.setUsername("a".repeat(51));
        user.setEmail("valid@example.com");
        user.setPassword("ValidPass123!");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The username must be between 5 and 50 characters long.");
    }

    @Test
    void whenUsernamePatternIsInvalid_thenValidationFails() {
        UserDTO user = new UserDTO();
        user.setUsername("invalid@username");
        user.setEmail("valid@example.com");
        user.setPassword("ValidPass123!");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The username can only contain letters, numbers, dots, hyphens, and underscores.");
    }

    @Test
    void whenEmailIsNull_thenValidationFails() {
        UserDTO user = new UserDTO();
        user.setUsername("validUsername");
        user.setEmail(null);
        user.setPassword("ValidPass123!");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The email cannot be null.");
    }

    @Test
    void whenEmailIsInvalid_thenValidationFails() {
        UserDTO user = new UserDTO();
        user.setUsername("validUsername");
        user.setEmail("invalid-email");
        user.setPassword("ValidPass123!");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The email format is invalid.");
    }

    @Test
    void whenEmailExceedsMaxLength_thenValidationFails() {
        UserDTO user = new UserDTO();
        user.setUsername("validUsername");
        user.setEmail("a".repeat(51) + "@example.com");
        user.setPassword("ValidPass123!");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("The email must have at most 50 characters.");
    }

    @Test
    void whenAllFieldsAreValid_thenValidationSucceeds() {
        UserDTO user = new UserDTO();
        user.setUsername("validUsername");
        user.setEmail("valid@example.com");
        user.setPassword("ValidPass123!");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertThat(violations).isEmpty();
    }
}

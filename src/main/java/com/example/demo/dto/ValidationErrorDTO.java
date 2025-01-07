package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO για την αναπαράσταση ενός σφάλματος επικύρωσης.
 */
@Getter
@AllArgsConstructor
public class ValidationErrorDTO {

    /**
     * Το όνομα του πεδίου που προκάλεσε το σφάλμα.
     */
    private final String fieldName;

    /**
     * Η αιτία του σφάλματος.
     */
    private final String cause;
}

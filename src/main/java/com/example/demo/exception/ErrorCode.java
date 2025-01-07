package com.example.demo.exception;

import lombok.Getter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "IND-001"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "IND-002"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "IND-003"),
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "IND-004"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "IND-005"),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "IND-006"),
    PRODUCT_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "IND-007"),
    SUPPLIER_NOT_FOUND(HttpStatus.NOT_FOUND, "IND-008"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "IND-009"),
    USER_NOT_FOUND_BY_USERNAME(HttpStatus.NOT_FOUND, "IND-010"),
    COLUMN_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "IND-011"),
    COLUMN_MUST_BE_UNIQUE(HttpStatus.CONFLICT, "IND-012"),
    RECORD_CANNOT_BE_DELETED(HttpStatus.CONFLICT, "IND-013"),
    VALUE_TOO_LONG(HttpStatus.BAD_REQUEST, "IND-014"),
    USER_ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "IND-015");

    private final HttpStatus status;
    private final String code;
    private final List<String> messages; // Λίστα μηνυμάτων για κάθε ErrorCode

    ErrorCode(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
        this.messages = new ArrayList<>(); // Αρχικοποίηση της λίστας
    }

    // Επιστρέφει το μήνυμα με βάση τη διεθνοποίηση
    public String getMessage(MessageSource messageSource) {
        return messageSource.getMessage(this.code, null, LocaleContextHolder.getLocale());
    }
}

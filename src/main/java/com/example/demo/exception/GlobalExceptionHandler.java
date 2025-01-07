package com.example.demo.exception;

import com.example.demo.dto.ErrorResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                            HttpServletRequest request) {
        String localizedMessage = messageSource.getMessage(
                ex.getErrorCode().getCode(),
                ex.getParams(),
                LocaleContextHolder.getLocale()
        );
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ex.getErrorCode().getCode(),
                localizedMessage,
                ex.getCause() != null ? ex.getCause().getMessage() : null,
                LocalDateTime.now(),
                ex.getErrorCode().getStatus().value(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                                  HttpServletRequest request) {
        String errorKey = "IND-013"; // Default for generic DB constraint violations
        if (ex.getMessage().contains("users.username")) {
            errorKey = "IND-011";
        } else if (ex.getMessage().contains("users.email")) {
            errorKey = "IND-012";
        }

        String localizedMessage = messageSource.getMessage(errorKey, null, LocaleContextHolder.getLocale());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                errorKey,
                localizedMessage,
                ex.getMostSpecificCause().getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                       HttpServletRequest request) {
        // Δημιουργία χάρτη που υποστηρίζει λίστα σφαλμάτων για κάθε πεδίο
        Map<String, List<String>> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                fieldError -> messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()),
                                Collectors.toList()
                        )
                ));

        // Δημιουργία του ErrorResponseDTO
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "IND-016",
                messageSource.getMessage("validation.failed", null, LocaleContextHolder.getLocale()),
                fieldErrors,
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }





    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex, HttpServletRequest request) {
        String localizedMessage = messageSource.getMessage("IND-001", null, LocaleContextHolder.getLocale());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "IND-001",
                localizedMessage,
                ex.getMessage(),
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI()
        );

        logger.error("Unhandled exception: ", ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
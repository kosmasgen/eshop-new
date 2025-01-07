package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class ErrorResponseDTO {

    private String errorCode;

    private String errorMessage;

    private Object innerMessage; // Το innerMessage πρέπει να είναι Object

    private LocalDateTime timestamp;

    private int status;

    private String path;

    // Constructor
    public ErrorResponseDTO(String errorCode, String errorMessage, Object innerMessage,
                            LocalDateTime timestamp, int status, String path) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.innerMessage = innerMessage;
        this.timestamp = timestamp;
        this.status = status;
        this.path = path;
    }
}

package com.example.demo.exception;

import lombok.Getter;

/**
 * Εξαίρεση που πετιέται όταν δε βρεθεί ένας πόρος.
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {

    private ErrorCode errorCode;
    private Object[] params;

    // Κατασκευαστής με ErrorCode και παραμέτρους
    public ResourceNotFoundException(ErrorCode errorCode, Object... params) {
        super(errorCode.name());  // Κλήση του super για να δώσει το μήνυμα
        this.errorCode = errorCode;
        this.params = params;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Object[] getParams() {
        return params;
    }
}

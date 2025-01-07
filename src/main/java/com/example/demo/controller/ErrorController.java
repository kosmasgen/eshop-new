package com.example.demo.controller;

import com.example.demo.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller για την επιστροφή όλων των μηνυμάτων ανά κωδικό σφάλματος.
 */
@RestController
@RequestMapping("/api/errors")
public class ErrorController {

    /**
     * Endpoint για επιστροφή μηνυμάτων ανά κωδικό σφάλματος.
     *
     * @return Map με τα μηνύματα ομαδοποιημένα ανά ErrorCode.
     */
    @GetMapping
    public ResponseEntity<Map<String, List<String>>> getAllErrorMessages() {
        Map<String, List<String>> errorMessages = new HashMap<>();
        for (ErrorCode code : ErrorCode.values()) {
            errorMessages.put(code.getCode(), code.getMessages());
        }
        return ResponseEntity.ok(errorMessages);
    }
}

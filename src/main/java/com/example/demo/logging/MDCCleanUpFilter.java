package com.example.demo.logging;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MDCCleanUpFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // Καθαρισμός στην αρχή του request
            MDC.clear();

            // Αν το αίτημα είναι HTTP, αποθηκεύουμε πληροφορίες
            if (request instanceof HttpServletRequest) {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                String method = httpRequest.getMethod();
                String path = httpRequest.getRequestURI();
                MDC.put("method", method);
                MDC.put("path", path);
            }

            chain.doFilter(request, response);
        } finally {
            // Καθαρισμός στο τέλος του request
            MDC.clear();
        }
    }
}

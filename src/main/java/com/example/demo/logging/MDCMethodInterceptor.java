package com.example.demo.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MDCMethodInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(MDCMethodInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.clear();

        // Αποθήκευση HTTP Method και Path
        MDC.put("method", request.getMethod());
        MDC.put("path", request.getRequestURI());

        // Debug για το handler
        logger.debug("Handler class: {}", handler.getClass().getName());

        // Ελέγχουμε αν ο handler είναι Controller method
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String methodName = handlerMethod.getMethod().getName();
            MDC.put("controllerMethod", methodName);

            // Προσθέτουμε debug log για έλεγχο
            logger.debug("Controller method identified: {}", methodName);
        } else {
            logger.debug("Handler is not a Controller method.");
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.clear();
    }
}

package com.example.demo.config;

import com.example.demo.logging.MDCCleanUpFilter;
import com.example.demo.logging.MDCMethodInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private MDCMethodInterceptor mdcMethodInterceptor;

    // Καταχώριση του Interceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mdcMethodInterceptor)
                .addPathPatterns("/api/**"); // Εφαρμογή στα API endpoints
    }

    // Καταχώριση του Filter
    @Bean
    public FilterRegistrationBean<MDCCleanUpFilter> mdcFilter() {
        FilterRegistrationBean<MDCCleanUpFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MDCCleanUpFilter());
        registrationBean.setOrder(Integer.MIN_VALUE); // Εξασφαλίζει ότι εκτελείται πρώτο
        return registrationBean;
    }
}

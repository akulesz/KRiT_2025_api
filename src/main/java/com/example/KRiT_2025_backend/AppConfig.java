package com.example.KRiT_2025_backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig  implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Umożliwia dostęp do wszystkich endpointów
                .allowedOrigins("http://localhost:3000")  // Zastąp URL, jeśli Twój Flutter działa na innym porcie
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Zezwala na te metody
                .allowCredentials(true)
                .allowedHeaders("*");
    }
}

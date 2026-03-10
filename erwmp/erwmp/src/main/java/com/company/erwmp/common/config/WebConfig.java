package com.company.erwmp.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration    //This class contains Spring configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {   //This method defines who can call your APIs from the browser.

        registry.addMapping("/api/**")    //Applies CORS rules to all APIs starting with /api/
                .allowedOrigins("http://localhost:3000")    //the front end react runs on and Only requests coming from this frontend are allowed.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  //Browser can use only these HTTP methods.
        .allowedHeaders(String.valueOf(List.of("Authorization", "Content-Type")))
        .allowedHeaders(String.valueOf(List.of("Authorization")))
                .allowedHeaders("*")  //Frontend can send any headers.
                .allowCredentials(true);  //Allows: Cookies, Authorization headers (JWT), Session-based auth

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


}

/*
* What is API Security Hardening?

Security hardening =
👉 Adding extra protective layers around your API so it behaves safely even when someone tries to misuse it.

Your API may already work correctly — hardening is about preventing abuse, attacks, and accidents.

Think of your API as a company building.
*
*
* 1️⃣ CORS — Who is allowed to enter from outside?
What problem does CORS solve?

Browsers block requests coming from unknown websites to protect users.
*
*
* 1️⃣ CORS — Who is allowed to enter from outside?
What problem does CORS solve?

Browsers block requests coming from unknown websites to protect users.
*
* 3️⃣ Rate Limiting — How many times can someone knock?
What problem does rate limiting solve?

Without limits:

Attackers can brute-force passwords

Bots can spam your API

Server can crash from too many requests
*
* */
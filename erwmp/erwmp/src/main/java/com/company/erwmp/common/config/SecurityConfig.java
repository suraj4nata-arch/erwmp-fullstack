package com.company.erwmp.common.config;

import com.company.erwmp.Security.AuditorAwareImpl;
import com.company.erwmp.Security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration   //This class defines security rules
@RequiredArgsConstructor   //Injects required dependencies
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;  //Injects your custom JWT guard

    @Bean
    public PasswordEncoder passwordEncoder() {  //encodes the password
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); //Used during login, “Yes, this username/password is valid”
    }

    @Bean
    public AuditorAware<String> auditorAware() {  //Spring now knows where to get “createdBy” from
        return new AuditorAwareImpl();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // 🔓 MUST be public
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 🔓 Public APIs FIRST
                        .requestMatchers("/api/users/login", "/api/users").permitAll()

                        // 🔐 Role-based
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 🔐 Protected APIs
                        .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/**").authenticated()

                        // 🔐 Everything else
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers
                        .contentTypeOptions(withDefaults())
                        .frameOptions(frame -> frame.deny())
                        .contentSecurityPolicy(csp ->
                                csp.policyDirectives("default-src 'self'")
                        )
                );

        http.addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}

/*
* SecurityConfig is the rule book for your application’s security(who can access, how authentication works n all)
*
authenticationManager()
* Used during login
* Delegates authentication to Spring Security

Central authority that decides:
“Yes, this username/password is valid”
*
PasswordEncoder
👉 Tool that hashes passwords
*
* CLIENT
  |
  |  (1) Login (username + password)
  v
SecurityConfig  --> AuthenticationManager
                      |
                      v
            CustomUserDetailsService
                      |
                      v
                  JwtUtils
               (Generate JWT)
                      |
                      v
                  JWT Token
-------------------------------------------------
CLIENT
  |
  |  (2) Request with JWT
  |  Authorization: Bearer <JWT>
  v
SecurityConfig
  |
  v
JwtAuthenticationFilter
  |
  |--> JwtUtils (validate + extract username)
  |
  |--> CustomUserDetailsService (load user)
  |
  v
SecurityContextHolder
  |
  v
Controller (API Access Granted ✅)

*
------------------------------------------------------------------------
 🔧 SecurityConfig

“These are the rules of the building.”

🛂 JwtAuthenticationFilter

“Show me your ID before entering.”

🔐 JwtUtils

“I create and verify ID cards.”

🗃 CustomUserDetailsService

“I fetch user info from the database.”
-------------------------------------------------------------------------------
 */


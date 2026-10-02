package com.finsightai.config;

import com.finsightai.security.JwtAuthFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration c = new CorsConfiguration();

        c.setAllowedOriginPatterns(List.of(
                "http://localhost:5173",
                "http://localhost:3000",
                "https://*.vercel.app"
        ));

        c.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        c.setAllowedHeaders(List.of("*"));

        c.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource s =
                new UrlBasedCorsConfigurationSource();

        s.registerCorsConfiguration("/**", c);

        return s;
    }

    private final JwtAuthFilter filter;

    public SecurityConfig(JwtAuthFilter filter) {
        this.filter = filter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration c) throws Exception {

        return c.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain chain(
            org.springframework.security.config.annotation.web.builders.HttpSecurity http)
            throws Exception {

        return http
                .csrf(c -> c.disable())
                .cors(c -> {})
                .sessionManagement(s ->
                        s.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a ->
                        a.requestMatchers(
                                "/api/auth/**",
                                "/api/health",
                                "/actuator/health",
                                "/h2-console/**",
                                "/api/reports/export/**"
                        ).permitAll()
                        .anyRequest().authenticated())
                .headers(h ->
                        h.frameOptions(f -> f.sameOrigin()))
                .addFilterBefore(
                        filter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

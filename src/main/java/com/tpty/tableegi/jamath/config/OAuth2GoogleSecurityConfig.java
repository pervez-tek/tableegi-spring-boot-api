package com.tpty.tableegi.jamath.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Slf4j
public class OAuth2GoogleSecurityConfig {

    @Value("${front.end.react.api.url}")
    private String FRONT_END_REACT_API_URL;

    @Bean
    @Order(1) // higher priority
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Oauth2 Google filter added");
        http.cors(withDefaults()) // 👈 enable CORS
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers( "/",
                                        "/login**",
                                        "/oauth2/**",
                                        "/api/tableegi/**",
                                        "/profiles/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**")
                                .permitAll().anyRequest().authenticated()).
                oauth2Login(oauth -> oauth
                        .defaultSuccessUrl(FRONT_END_REACT_API_URL, true))
                         .logout(logout -> logout
                .logoutSuccessUrl(FRONT_END_REACT_API_URL)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        );


        http.headers(headers ->
                headers.contentSecurityPolicy(csp ->
                                csp.policyDirectives("default-src 'self'; script-src 'self'; img-src 'self' https: data:")).
                        frameOptions(HeadersConfigurer.FrameOptionsConfig::deny).
                        httpStrictTransportSecurity(hsts -> hsts.includeSubDomains(true).
                                preload(true)).xssProtection(HeadersConfigurer.XXssConfig::disable).contentTypeOptions(withDefaults()));


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        log.info("Oauth2 Google  Cors added");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(FRONT_END_REACT_API_URL));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

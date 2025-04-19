package com.springAuthServerDemo.spring_auth_server_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class SecurityConfig {
    @Bean
    @Order(1) //Initialise this first
    public SecurityFilterChain authorisationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults()); // Enable OpenID Connect 1.0

        // Redirect to the login page when not authenticated from the authorisation end point
        http.exceptionHandling((exceptions)
                -> exceptions.authenticationEntryPoint(
                new LoginUrlAuthenticationEntryPoint("/login"))
        ).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())); // Accept access tokens for User Info and/or Client Registration
        return http.build();
    }

}

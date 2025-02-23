package com.example.jwt_project.security;

import com.example.jwt_project.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    public static final String ADMIN = "admin";
    public static final String USER = "user";
    private final JwtAuthEntryPoint authEntryPoint;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (authz) -> authz.requestMatchers("/api/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET)
                        .permitAll()
                        .requestMatchers(HttpMethod.DELETE)
                        .hasRole(ADMIN)
                        .requestMatchers(HttpMethod.POST)
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT)
                        .hasRole(USER)
                        .anyRequest()
                        .authenticated());
        http.userDetailsService(userDetailsService);
        http.exceptionHandling((exceptions) -> exceptions.authenticationEntryPoint(authEntryPoint));
        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}

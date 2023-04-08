package com.example.proyectoIntegradorE8.security;

import com.example.proyectoIntegradorE8.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/usuario/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/reserva/**").hasAnyAuthority("USER","ADMIN")
                .requestMatchers(HttpMethod.PUT, "/reserva/**").hasAnyAuthority("USER","ADMIN")
                .requestMatchers(HttpMethod.PUT, "/usuario/**").hasAnyAuthority("USER","ADMIN")
                .requestMatchers(HttpMethod.POST, "/favorito/**").hasAnyAuthority("USER","ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/favorito/**").hasAnyAuthority("USER","ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/reserva/**").hasAnyAuthority("USER","ADMIN")
                .requestMatchers(HttpMethod.POST).hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT).hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE).hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/reserva/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/usuario/**").hasAnyAuthority("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/favorito/**").hasAnyAuthority("USER","ADMIN")
                .requestMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}

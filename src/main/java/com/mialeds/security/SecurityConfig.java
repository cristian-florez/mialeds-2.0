package com.mialeds.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mialeds.services.UserDetailsServiceImpl;

// Esta clase se utiliza para configurar la seguridad de la aplicación
@Configuration
@EnableWebSecurity // Esta anotación habilita la seguridad web en nuestra aplicación
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // Método que configura la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        
        // Configuramos la cadena de seguridad
        return httpSecurity
            // Desactivar CSRF para permitir peticiones POST desde Postman
            .csrf(csrf -> csrf.disable())
            // Desactivar form login porque usaremos autenticación por API
            .formLogin(form -> form.disable())
            // Desactivar la redirección a páginas HTML
            .httpBasic(httpBasic -> httpBasic.disable())
            // Configuración de sesión sin estado (stateless) para API REST
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Configuración de rutas permitidas
            .authorizeHttpRequests(http -> {
                    // Definimos qué solicitudes HTTP son públicas y cuáles requieren autenticación
                    http.requestMatchers("/login").permitAll(); 
                    http.requestMatchers("/usuario/nuevo").permitAll();
                    http.requestMatchers("/usuario/restaurar-clave").permitAll();
                    
                    // Aquí definimos las rutas que requieren autenticación
                    http.requestMatchers("/usuario/editar").hasRole("ADMIN");
                    http.requestMatchers("/producto/nuevo").hasRole("ADMIN");
                    http.requestMatchers("/producto/editar").hasRole("ADMIN");
                    http.requestMatchers("/producto/eliminar").hasRole("ADMIN");
                    http.requestMatchers("/producto/movimiento-producto").hasRole("ADMIN");
                    http.requestMatchers("/venta/editar").hasRole("ADMIN");
                    http.requestMatchers("/venta/eliminar").hasRole("ADMIN");
                    http.requestMatchers("/proveedor/**").hasRole("ADMIN");

                http.anyRequest().authenticated();
            })
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    // Método que crea un codificador de contraseñas utilizando BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
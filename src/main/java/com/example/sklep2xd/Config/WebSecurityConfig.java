package com.example.sklep2xd.Config;

import com.example.sklep2xd.Service.MyUserDetailsService;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/pracownik/**").hasRole("PRACOWNIK")
                        .requestMatchers("/klient/**").hasRole("KLIENT")
                        .requestMatchers("/miniwidok.html", "/css/**", "/Grafiki/**", "/js/**").permitAll()  // Zawsze zezwalaj na zasoby statyczne
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/logowanie")
                        .permitAll()
                        .loginProcessingUrl("/logowanie")
                        .usernameParameter("login")
                        .passwordParameter("haslo")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/logowanie?error=true")
                )
                .logout((logout) -> logout
                        .permitAll()
                )
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())); // Ensures CSRF token is included in the form
        http.headers().frameOptions().sameOrigin();

        return http.build();
    }
    @Bean
    public HttpSessionListener httpSessionListener(){
        return new SessionListener();
    }
}
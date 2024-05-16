package com.example.sklep2xd.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PracownikDetailsService pracownikDetailsService;  // Dodano wstrzyknięcie PracownikDetailsService

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(authz -> authz
////                        .requestMatchers("/logowanie", "/rejestracja").permitAll()
////                        .requestMatchers("/klienci/**","/home").hasRole("KLIENT")
////                        .requestMatchers("/Pracownik/**").hasRole("employee")
//                        .requestMatchers("/Pracownik/**", "/admin/**", "/klienci/**", "/Produkt/**", "/home/**", "/logowanie", "/koszyk/**", "/Kategoria/**", "/Recenzja/**", "Zamowienie/**", "/rejestracja", "/dodajDoKoszyka/**").permitAll()
////                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/miniwidok.html","/css/**", "/Grafiki/**", "/js/**").permitAll()  // Zawsze zezwalaj na zasoby statyczne
//                        .anyRequest().authenticated()
//                )
//                .headers().frameOptions().sameOrigin() // Ustawienie nagłówka X-Frame-Options
//                .and()
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/Pracownik/**", "/admin/**", "/klienci/**", "/Produkt/**", "/home/**", "/logowanie", "/koszyk/**", "/Kategoria/**", "/Recenzja/**", "/Zamowienie/**", "/rejestracja", "/dodajDoKoszyka/**", "/**").permitAll()
                        .requestMatchers("/miniwidok.html", "/css/**", "/Grafiki/**", "/js/**").permitAll()  // Zawsze zezwalaj na zasoby statyczne
                        .anyRequest().authenticated()
                )
                .headers().frameOptions().sameOrigin() // Ustawienie nagłówka X-Frame-Options
                .and()
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }




    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(pracownikDetailsService) // Zaktualizowano, aby używać pracownikDetailsService
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.SistemaApiCrud.SistemaCrud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/openapi.yaml").permitAll()
                        .requestMatchers("/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/casos/**").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.POST, "/casos/**").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.PUT, "/casos/**").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.PATCH, "/casos/**").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.DELETE, "/casos/**").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers("/perguntas/**").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.POST, "/alunos/*/casos/*/responder").hasAnyRole("ADMIN", "ALUNO")
                        .requestMatchers(HttpMethod.GET, "/alunos/*/casos/*/completo").hasAnyRole("ADMIN", "ALUNO")
                        .requestMatchers(HttpMethod.GET, "/alunos/*/casos-disponiveis").hasAnyRole("ADMIN", "ALUNO")
                        .requestMatchers(HttpMethod.GET, "/alunos/*/historico").hasAnyRole("ADMIN", "ALUNO")
                        .requestMatchers(HttpMethod.GET, "/alunos/*/desempenho").hasAnyRole("ADMIN", "ALUNO")
                        .requestMatchers("/alunos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/professores/*/casos").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/professores/*/relatorio-desempenho").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers("/professores/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

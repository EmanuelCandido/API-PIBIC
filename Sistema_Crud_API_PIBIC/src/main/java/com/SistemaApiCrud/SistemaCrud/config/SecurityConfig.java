package com.SistemaApiCrud.SistemaCrud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${app.security.admin.username:admin}")
    private String adminUsername;

    @Value("${app.security.admin.password:admin123}")
    private String adminPassword;

    @Value("${app.security.professor.username:professor}")
    private String professorUsername;

    @Value("${app.security.professor.password:professor123}")
    private String professorPassword;

    @Value("${app.security.aluno.username:aluno}")
    private String alunoUsername;

    @Value("${app.security.aluno.password:aluno123}")
    private String alunoPassword;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/casos/**").hasAnyRole("ADMIN", "PROFESSOR", "ALUNO")
                        .requestMatchers(HttpMethod.POST, "/casos/**").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.PUT, "/casos/**").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.PATCH, "/casos/**").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.DELETE, "/casos/**").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers("/perguntas/**").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.POST, "/alunos/*/casos/*/responder").hasAnyRole("ADMIN", "ALUNO")
                        .requestMatchers(HttpMethod.GET, "/alunos/*/casos-disponiveis").hasAnyRole("ADMIN", "ALUNO")
                        .requestMatchers(HttpMethod.GET, "/alunos/*/historico").hasAnyRole("ADMIN", "ALUNO")
                        .requestMatchers(HttpMethod.GET, "/alunos/*/desempenho").hasAnyRole("ADMIN", "ALUNO")
                        .requestMatchers("/alunos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/professores/*/casos").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/professores/*/relatorio-desempenho").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers("/professores/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername(adminUsername)
                        .password("{noop}" + adminPassword)
                        .roles("ADMIN")
                        .build(),
                User.withUsername(professorUsername)
                        .password("{noop}" + professorPassword)
                        .roles("PROFESSOR")
                        .build(),
                User.withUsername(alunoUsername)
                        .password("{noop}" + alunoPassword)
                        .roles("ALUNO")
                        .build());
    }
}

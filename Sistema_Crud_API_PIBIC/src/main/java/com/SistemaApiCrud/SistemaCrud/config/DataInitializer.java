package com.SistemaApiCrud.SistemaCrud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.SistemaApiCrud.SistemaCrud.entity.Usuario;
import com.SistemaApiCrud.SistemaCrud.entity.enums.PapelUsuario;
import com.SistemaApiCrud.SistemaCrud.repository.usuario_repository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final usuario_repository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

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

    public DataInitializer(usuario_repository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        criarUsuarioInicial(adminUsername, adminPassword, PapelUsuario.ADMIN);
        criarUsuarioInicial(professorUsername, professorPassword, PapelUsuario.PROFESSOR);
        criarUsuarioInicial(alunoUsername, alunoPassword, PapelUsuario.ALUNO);
    }

    private void criarUsuarioInicial(String username, String senha, PapelUsuario role) {
        if (usuarioRepository.existsByUsername(username)) {
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setRole(role);
        usuario.setAtivo(true);

        usuarioRepository.save(usuario);
    }
}

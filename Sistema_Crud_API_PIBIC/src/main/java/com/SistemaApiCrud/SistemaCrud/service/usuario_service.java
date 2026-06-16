package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.DTO.usuario_request_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.usuario_response_DTO;
import com.SistemaApiCrud.SistemaCrud.entity.Usuario;
import com.SistemaApiCrud.SistemaCrud.exception.BusinessException;
import com.SistemaApiCrud.SistemaCrud.exception.RecursoNaoEncontradoException;
import com.SistemaApiCrud.SistemaCrud.repository.usuario_repository;

@Service
public class usuario_service {

    private final usuario_repository repository;
    private final PasswordEncoder passwordEncoder;

    public usuario_service(usuario_repository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<usuario_response_DTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::paraDTO)
                .toList();
    }

    public usuario_response_DTO buscarPorId(Long id) {
        return paraDTO(buscarEntityPorId(id));
    }

    public usuario_response_DTO salvar(usuario_request_DTO dto) {
        if (repository.existsByUsername(dto.getUsername())) {
            throw new BusinessException("Ja existe usuario cadastrado com esse username");
        }

        Usuario usuario = paraEntity(dto, new Usuario());
        return paraDTO(repository.save(usuario));
    }

    public usuario_response_DTO atualizar(Long id, usuario_request_DTO dto) {
        Usuario usuario = buscarEntityPorId(id);

        repository.findByUsername(dto.getUsername())
                .filter(usuarioEncontrado -> !usuarioEncontrado.getId().equals(id))
                .ifPresent(usuarioEncontrado -> {
                    throw new BusinessException("Ja existe usuario cadastrado com esse username");
                });

        return paraDTO(repository.save(paraEntity(dto, usuario)));
    }

    public usuario_response_DTO ativar(Long id) {
        Usuario usuario = buscarEntityPorId(id);
        usuario.setAtivo(true);
        return paraDTO(repository.save(usuario));
    }

    public usuario_response_DTO desativar(Long id) {
        Usuario usuario = buscarEntityPorId(id);
        usuario.setAtivo(false);
        return paraDTO(repository.save(usuario));
    }

    private Usuario paraEntity(usuario_request_DTO dto, Usuario usuario) {
        usuario.setUsername(dto.getUsername());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setRole(dto.getRole());
        usuario.setAtivo(dto.getAtivo() == null || dto.getAtivo());
        return usuario;
    }

    private usuario_response_DTO paraDTO(Usuario usuario) {
        return new usuario_response_DTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getAtivo());
    }

    private Usuario buscarEntityPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario nao encontrado"));
    }
}

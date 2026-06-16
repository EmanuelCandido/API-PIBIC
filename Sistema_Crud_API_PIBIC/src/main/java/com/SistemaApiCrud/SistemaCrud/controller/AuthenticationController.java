package com.SistemaApiCrud.SistemaCrud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SistemaApiCrud.SistemaCrud.DTO.LoginRequestDTO;
import com.SistemaApiCrud.SistemaCrud.DTO.LoginResponseDTO;
import com.SistemaApiCrud.SistemaCrud.entity.Usuario;
import com.SistemaApiCrud.SistemaCrud.mapper.UsuarioMapper;
import com.SistemaApiCrud.SistemaCrud.service.JwtService;
import com.SistemaApiCrud.SistemaCrud.service.usuario_service;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final usuario_service usuarioService;
    private final UsuarioMapper usuarioMapper;

    public AuthenticationController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            usuario_service usuarioService,
            UsuarioMapper usuarioMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String token = jwtService.gerarToken(authentication);
        Usuario usuario = usuarioService.buscarPorUsername(authentication.getName());
        return ResponseEntity.ok(usuarioMapper.toLoginResponse(token, jwtService.getExpiraEm(token), usuario));
    }
}

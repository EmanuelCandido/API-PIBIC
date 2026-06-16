package com.SistemaApiCrud.SistemaCrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SistemaApiCrud.SistemaCrud.DTO.conteudo_clinico_DTO;
import com.SistemaApiCrud.SistemaCrud.service.conteudo_clinico_service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/conteudos")
public class conteudo_clinico_controller {

    @Autowired
    private conteudo_clinico_service service;

    @GetMapping
    public List<conteudo_clinico_DTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<conteudo_clinico_DTO> buscarPorId(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<conteudo_clinico_DTO> salvar(@RequestBody @Valid conteudo_clinico_DTO conteudo) {
        conteudo_clinico_DTO conteudoSalvo = service.salvar(conteudo);
        return ResponseEntity.status(HttpStatus.CREATED).body(conteudoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<conteudo_clinico_DTO> atualizar(@PathVariable @Min(1) Long id,
                                                          @RequestBody @Valid conteudo_clinico_DTO conteudo) {
        return ResponseEntity.ok(service.atualizar(id, conteudo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @Min(1) Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

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

import com.SistemaApiCrud.SistemaCrud.DTO.pergunta_DTO;
import com.SistemaApiCrud.SistemaCrud.service.pergunta_service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/perguntas")
public class pergunta_controller {

    @Autowired
    private pergunta_service service;

    @GetMapping
    public List<pergunta_DTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<pergunta_DTO> buscarPorId(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<pergunta_DTO> salvar(@RequestBody @Valid pergunta_DTO pergunta) {
        pergunta_DTO perguntaSalva = service.salvar(pergunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(perguntaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<pergunta_DTO> atualizar(@PathVariable @Min(1) Long id,
                                                  @RequestBody @Valid pergunta_DTO pergunta) {
        return ResponseEntity.ok(service.atualizar(id, pergunta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @Min(1) Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

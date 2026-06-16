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

import com.SistemaApiCrud.SistemaCrud.DTO.aluno_DTO;
import com.SistemaApiCrud.SistemaCrud.service.aluno_service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/alunos")
public class aluno_controller {

    @Autowired
    private aluno_service service;

    @GetMapping
    public List<aluno_DTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<aluno_DTO> buscarPorId(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<aluno_DTO> salvar(@RequestBody @Valid aluno_DTO aluno) {
        aluno_DTO alunoSalvo = service.salvar(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<aluno_DTO> atualizar(@PathVariable @Min(1) Long id,
                                               @RequestBody @Valid aluno_DTO aluno) {
        return ResponseEntity.ok(service.atualizar(id, aluno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @Min(1) Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

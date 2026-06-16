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

import com.SistemaApiCrud.SistemaCrud.DTO.casos_clinicos_DTO;
import com.SistemaApiCrud.SistemaCrud.service.caso_clinico_service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/casos")
public class caso_clinico_controller {

    @Autowired
    private caso_clinico_service service;

    @GetMapping
    public List<casos_clinicos_DTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<casos_clinicos_DTO> buscarPorId(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<casos_clinicos_DTO> salvar(@RequestBody @Valid casos_clinicos_DTO caso) {
        casos_clinicos_DTO casoSalvo = service.salvar(caso);
        return ResponseEntity.status(HttpStatus.CREATED).body(casoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<casos_clinicos_DTO> atualizar(@PathVariable @Min(1) Long id,
                                                        @RequestBody @Valid casos_clinicos_DTO caso) {
        return ResponseEntity.ok(service.atualizar(id, caso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @Min(1) Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

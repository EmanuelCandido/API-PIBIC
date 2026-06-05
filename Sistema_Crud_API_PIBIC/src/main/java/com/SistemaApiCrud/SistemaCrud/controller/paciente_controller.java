package com.SistemaApiCrud.SistemaCrud.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SistemaApiCrud.SistemaCrud.DTO.paciente_DTO;
import com.SistemaApiCrud.SistemaCrud.service.paciente_service;

@Validated
@RestController
@RequestMapping("/pacientes")
public class paciente_controller {

	@Autowired
    private paciente_service service;

    @GetMapping
    public List<paciente_DTO> listar() {
        return service.listar();
    }

    @PostMapping
    public paciente_DTO salvar(@RequestBody @Valid paciente_DTO paciente) {
        return service.salvar(paciente);
    }

    @PutMapping("/{id}")
    public paciente_DTO atualizar(@PathVariable Long id,
                              @RequestBody @Valid paciente_DTO paciente) {

        return service.atualizar(id, paciente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
	
	
}

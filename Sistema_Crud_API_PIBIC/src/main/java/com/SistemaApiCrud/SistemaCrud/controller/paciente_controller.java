package com.SistemaApiCrud.SistemaCrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SistemaApiCrud.SistemaCrud.entity.paciente;
import com.SistemaApiCrud.SistemaCrud.service.paciente_service;

@RestController
@RequestMapping("/pacientes")
public class paciente_controller {

	@Autowired
    private paciente_service service;

    @GetMapping
    public List<paciente> listar() {
        return service.listar();
    }

    @PostMapping
    public paciente salvar(@RequestBody paciente paciente) {
        return service.salvar(paciente);
    }

    @PutMapping("/{id}")
    public paciente atualizar(@PathVariable Long id,
                              @RequestBody paciente paciente) {

        return service.atualizar(id, paciente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
	
	
}

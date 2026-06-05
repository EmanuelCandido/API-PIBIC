package com.SistemaApiCrud.SistemaCrud.controller;

import java.util.List;

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
import com.SistemaApiCrud.SistemaCrud.DTO.aluno_DTO;
import com.SistemaApiCrud.SistemaCrud.service.aluno_service;

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

    @PostMapping
    public aluno_DTO salvar(@RequestBody aluno_DTO aluno) {
        return service.salvar(aluno);
    }

    @PutMapping("/{id}")
    public aluno_DTO atualizar(@PathVariable Long id, @RequestBody aluno_DTO aluno) {
    	return service.atualizar(id, aluno);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
	
	
}

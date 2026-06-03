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
import com.SistemaApiCrud.SistemaCrud.entity.Aluno;
import com.SistemaApiCrud.SistemaCrud.service.aluno_service;

@RestController
@RequestMapping("/alunos")
public class aluno_controller {

	@Autowired
    private aluno_service service;

    @GetMapping
    public List<Aluno> listar() {
        return service.listar();
    }

    @PostMapping
    public Aluno salvar(@RequestBody Aluno aluno) {
        return service.salvar(aluno);
    }

    @PutMapping("/{id}")
    public Aluno atualizar(@PathVariable Long id, @RequestBody Aluno aluno) {
    	return service.atualizar(id, aluno);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
	
	
}

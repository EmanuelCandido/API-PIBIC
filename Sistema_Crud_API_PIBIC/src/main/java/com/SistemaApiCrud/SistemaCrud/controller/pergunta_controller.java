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

import com.SistemaApiCrud.SistemaCrud.entity.pergunta;
import com.SistemaApiCrud.SistemaCrud.service.pergunta_service;

@RestController
@RequestMapping("/perguntas")
public class pergunta_controller {

	 	@Autowired
	    private pergunta_service service;

	    @GetMapping
	    public List<pergunta> listar() {
	        return service.listar();
	    }

	    @PostMapping
	    public pergunta salvar(@RequestBody pergunta pergunta) {
	        return service.salvar(pergunta);
	    }

	    @PutMapping("/{id}")
	    public pergunta atualizar(@PathVariable Long id,
	                              @RequestBody pergunta pergunta) {

	        return service.atualizar(id, pergunta);
	    }

	    @DeleteMapping("/{id}")
	    public void deletar(@PathVariable Long id) {
	        service.deletar(id);
	    }
	
	
}

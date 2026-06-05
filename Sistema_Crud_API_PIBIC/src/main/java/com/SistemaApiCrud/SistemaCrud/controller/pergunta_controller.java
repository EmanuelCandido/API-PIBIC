package com.SistemaApiCrud.SistemaCrud.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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

import com.SistemaApiCrud.SistemaCrud.DTO.pergunta_DTO;
import com.SistemaApiCrud.SistemaCrud.service.pergunta_service;

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

	    @PostMapping
	    public pergunta_DTO salvar(@RequestBody @Valid @Min(1) pergunta_DTO pergunta) {
	        return service.salvar(pergunta);
	    }

	    @PutMapping("/{id}")
	    public pergunta_DTO atualizar(@PathVariable Long id,
	                                  @RequestBody @Valid pergunta_DTO pergunta) {

	        return service.atualizar(id, pergunta);
	    }

	    @DeleteMapping("/{id}")
	    public void deletar(@PathVariable Long id) {
	        service.deletar(id);
	    }
	
	
}

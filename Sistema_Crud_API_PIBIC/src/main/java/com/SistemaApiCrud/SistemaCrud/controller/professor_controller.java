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
import com.SistemaApiCrud.SistemaCrud.service.professor_service;
import com.SistemaApiCrud.SistemaCrud.DTO.Professor_DTO;

@Validated
@RestController
@RequestMapping("/professores")
public class professor_controller {

	  @Autowired
	    private professor_service service;
	  
	    @GetMapping
	    public List<Professor_DTO> listar() {
	        return service.listar();
	    }

	    @PostMapping
	    public Professor_DTO salvar(@RequestBody @Valid @Min(1) Professor_DTO professor) {
	        return service.salvar(professor);
	    }

	    @PutMapping("/{id}")
	    public Professor_DTO atualizar(@PathVariable Long id,
	                                   @RequestBody @Valid Professor_DTO professor) {

	        return service.atualizar(id, professor);
	    }

	    @DeleteMapping("/{id}")
	    public void deletar(@PathVariable Long id) {
	        service.deletar(id);
	    }
	
	
	
}

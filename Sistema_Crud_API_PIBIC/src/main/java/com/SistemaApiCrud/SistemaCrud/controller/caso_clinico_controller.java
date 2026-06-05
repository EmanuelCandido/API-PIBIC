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
import com.SistemaApiCrud.SistemaCrud.DTO.casos_clinicos_DTO;
import com.SistemaApiCrud.SistemaCrud.service.caso_clinico_service;

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

	    @PostMapping
	    public casos_clinicos_DTO salvar(@RequestBody @Valid @Min(1) casos_clinicos_DTO caso) {
	        return service.salvar(caso);
	    }

	    @PutMapping("/{id}")
	    public casos_clinicos_DTO atualizar(@PathVariable Long id,
	                                    @RequestBody @Valid casos_clinicos_DTO caso) {

	        return service.atualizar(id, caso);
	    }

	    @DeleteMapping("/{id}")
	    public void deletar(@PathVariable Long id) {
	        service.deletar(id);
	    }
	
}

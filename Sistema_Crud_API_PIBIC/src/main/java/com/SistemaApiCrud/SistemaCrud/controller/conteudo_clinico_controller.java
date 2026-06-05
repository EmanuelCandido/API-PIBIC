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

import com.SistemaApiCrud.SistemaCrud.DTO.conteudo_clinico_DTO;
import com.SistemaApiCrud.SistemaCrud.service.conteudo_clinico_service;

@Validated
@RestController
@RequestMapping("/conteudos")
public class conteudo_clinico_controller {

	@Autowired
    private conteudo_clinico_service service;

    @GetMapping
    public List<conteudo_clinico_DTO> listar() {
        return service.listar();
    }

    @PostMapping
    public conteudo_clinico_DTO salvar(@RequestBody @Valid @Min(1) conteudo_clinico_DTO conteudo) {
        return service.salvar(conteudo);
    }

    @PutMapping("/{id}")
    public conteudo_clinico_DTO atualizar(@PathVariable Long id,
                                      @RequestBody @Valid conteudo_clinico_DTO conteudo) {

        return service.atualizar(id, conteudo);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
	
	
	
	
	
}

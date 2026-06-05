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

import com.SistemaApiCrud.SistemaCrud.entity.conteudo_clinico;
import com.SistemaApiCrud.SistemaCrud.service.conteudo_clinico_service;

@RestController
@RequestMapping("/conteudos")
public class conteudo_clinico_controller {

	@Autowired
    private conteudo_clinico_service service;

    @GetMapping
    public List<conteudo_clinico> listar() {
        return service.listar();
    }

    @PostMapping
    public conteudo_clinico salvar(@RequestBody conteudo_clinico conteudo) {
        return service.salvar(conteudo);
    }

    @PutMapping("/{id}")
    public conteudo_clinico atualizar(@PathVariable Long id,
                                     @RequestBody conteudo_clinico conteudo) {

        return service.atualizar(id, conteudo);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
	
	
	
	
	
}

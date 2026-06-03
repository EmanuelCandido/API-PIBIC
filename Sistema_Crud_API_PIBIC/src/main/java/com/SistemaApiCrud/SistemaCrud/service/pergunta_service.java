package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.entity.pergunta;
import com.SistemaApiCrud.SistemaCrud.repository.pergunta_repository;

@Service
public class pergunta_service {

	@Autowired
    private pergunta_repository repository;
	
    public List<pergunta> listar() {
        return repository.findAll();
    }

    public pergunta salvar(pergunta pergunta) {
        return repository.save(pergunta);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public pergunta atualizar(Long id, pergunta pergunta) {

        pergunta.setId(id);

        return repository.save(pergunta);
    }
	
}

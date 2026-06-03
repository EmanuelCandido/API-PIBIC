package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.entity.Professor;
import com.SistemaApiCrud.SistemaCrud.repository.professor_repository;

@Service
public class professor_service {

	@Autowired
    private professor_repository repository;
	
	public List<Professor> listar() {
        return repository.findAll();
    }

	public Professor salvar(Professor professor) {
	        return repository.save(professor);
	}

	public void deletar(Long id) {
        repository.deleteById(id);
    }

	public Professor atualizar(Long id, Professor professor) {

        professor.setId(id);
        
        return repository.save(professor);
    }
}

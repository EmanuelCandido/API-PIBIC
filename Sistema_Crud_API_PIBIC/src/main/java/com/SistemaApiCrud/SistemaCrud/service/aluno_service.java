package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.entity.Aluno;
import com.SistemaApiCrud.SistemaCrud.repository.aluno_repository;

@Service
public class aluno_service {

	@Autowired
	private aluno_repository repository;
	
	public List<Aluno> listar() {
        return repository.findAll();
    }

    public Aluno salvar(Aluno aluno) {
        return repository.save(aluno);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Aluno atualizar(Long id, Aluno aluno) {

        aluno.setIdAluno(id);

        return repository.save(aluno);
    }
}

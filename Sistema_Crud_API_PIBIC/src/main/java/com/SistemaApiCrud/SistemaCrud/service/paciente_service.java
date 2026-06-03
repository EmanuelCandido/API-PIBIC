package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.entity.paciente;
import com.SistemaApiCrud.SistemaCrud.repository.paciente_repository;

@Service
public class paciente_service {

	 @Autowired
	 private paciente_repository repository;
	
	 public List<paciente> listar() {
	        return repository.findAll();
	    }

	    public paciente salvar(paciente paciente) {
	        return repository.save(paciente);
	    }

	    public void deletar(Long id) {
	        repository.deleteById(id);
	    }

	    public paciente atualizar(Long id, paciente paciente) {

	        paciente.setIdPaciente(id);

	        return repository.save(paciente);
	    }
	 
}

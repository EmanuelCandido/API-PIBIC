package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.entity.casos_clinicos;
import com.SistemaApiCrud.SistemaCrud.repository.caso_clinico_repository;

@Service
public class caso_clinico_service {

	
	@Autowired
	private caso_clinico_repository repository;
	
	 public List<casos_clinicos> listar() {
	        return repository.findAll();
	    }

	    public casos_clinicos salvar(casos_clinicos caso) {
	        return repository.save(caso);
	    }

	    public void deletar(Long id) {
	        repository.deleteById(id);
	    }

	    public casos_clinicos atualizar(Long id,casos_clinicos caso) {

	        caso.setIdCaso(id);

	        return repository.save(caso);
	    }
}

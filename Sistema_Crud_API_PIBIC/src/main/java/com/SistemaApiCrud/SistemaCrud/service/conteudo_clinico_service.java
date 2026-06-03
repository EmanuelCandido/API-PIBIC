package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.entity.conteudo_clinico;
import com.SistemaApiCrud.SistemaCrud.repository.conteudo_clinico_repository;

@Service
public class conteudo_clinico_service {

	@Autowired
	private conteudo_clinico_repository repository;
	
	 public List<conteudo_clinico> listar() {
	        return repository.findAll();
	    }

	    public conteudo_clinico salvar(conteudo_clinico conteudo) {
	        return repository.save(conteudo);
	    }

	    public void deletar(Long id) {
	        repository.deleteById(id);
	    }

	    public conteudo_clinico atualizar(Long id, conteudo_clinico conteudo) {

	        conteudo.setIdConteudo(id);

	        return repository.save(conteudo);
	    }
}

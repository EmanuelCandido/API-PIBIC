package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.DTO.conteudo_clinico_DTO;
import com.SistemaApiCrud.SistemaCrud.entity.casos_clinicos;
import com.SistemaApiCrud.SistemaCrud.entity.conteudo_clinico;
import com.SistemaApiCrud.SistemaCrud.repository.caso_clinico_repository;
import com.SistemaApiCrud.SistemaCrud.repository.conteudo_clinico_repository;

@Service
public class conteudo_clinico_service {

	@Autowired
	private conteudo_clinico_repository repository;

	@Autowired
	private caso_clinico_repository casoRepository;

	public List<conteudo_clinico_DTO> listar() {
		return repository.findAll()
				.stream()
				.map(this::paraDTO)
				.toList();
	}

	public conteudo_clinico_DTO salvar(conteudo_clinico_DTO dto) {
		conteudo_clinico conteudo = paraEntity(dto);
		conteudo_clinico conteudoSalvo = repository.save(conteudo);
		return paraDTO(conteudoSalvo);
	}

	public void deletar(Long id) {
		repository.deleteById(id);
	}

	public conteudo_clinico_DTO atualizar(Long id, conteudo_clinico_DTO dto) {
		conteudo_clinico conteudo = paraEntity(dto);
		conteudo.setIdConteudo(id);

		conteudo_clinico conteudoAtualizado = repository.save(conteudo);
		return paraDTO(conteudoAtualizado);
	}

	private conteudo_clinico_DTO paraDTO(conteudo_clinico conteudo) {
		conteudo_clinico_DTO dto = new conteudo_clinico_DTO();

		dto.setIdConteudo(conteudo.getIdConteudo());

		if (conteudo.getCasoClinico() != null) {
			dto.setIdCaso(conteudo.getCasoClinico().getIdCaso());
		}

		dto.setSintomas(conteudo.getSintomas());
		dto.setContexto(conteudo.getContexto());
		dto.setExamClinico(conteudo.getExamClinico());
		dto.setAntecClinico(conteudo.getAntecClinico());
		dto.setDiagEsperado(conteudo.getDiagEsperado());

		return dto;
	}

	private conteudo_clinico paraEntity(conteudo_clinico_DTO dto) {
		conteudo_clinico conteudo = new conteudo_clinico();

		conteudo.setIdConteudo(dto.getIdConteudo());

		if (dto.getIdCaso() != null) {
			casos_clinicos caso = casoRepository.findById(dto.getIdCaso())
					.orElseThrow(() -> new RuntimeException("Caso clínico não encontrado"));
			conteudo.setCasoClinico(caso);
		}

		conteudo.setSintomas(dto.getSintomas());
		conteudo.setContexto(dto.getContexto());
		conteudo.setExamClinico(dto.getExamClinico());
		conteudo.setAntecClinico(dto.getAntecClinico());
		conteudo.setDiagEsperado(dto.getDiagEsperado());

		return conteudo;
	}
}
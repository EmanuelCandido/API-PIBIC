package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.DTO.casos_clinicos_DTO;
import com.SistemaApiCrud.SistemaCrud.entity.Professor;
import com.SistemaApiCrud.SistemaCrud.entity.casos_clinicos;
import com.SistemaApiCrud.SistemaCrud.repository.caso_clinico_repository;
import com.SistemaApiCrud.SistemaCrud.repository.professor_repository;

@Service
public class caso_clinico_service {

	@Autowired
	private caso_clinico_repository repository;

	@Autowired
	private professor_repository professorRepository;

	public List<casos_clinicos_DTO> listar() {
		return repository.findAll()
				.stream()
				.map(this::paraDTO)
				.toList();
	}

	public casos_clinicos_DTO salvar(casos_clinicos_DTO dto) {
		casos_clinicos caso = paraEntity(dto);
		casos_clinicos casoSalvo = repository.save(caso);
		return paraDTO(casoSalvo);
	}

	public void deletar(Long id) {
		repository.deleteById(id);
	}

	public casos_clinicos_DTO atualizar(Long id, casos_clinicos_DTO dto) {
		casos_clinicos caso = paraEntity(dto);
		caso.setIdCaso(id);

		casos_clinicos casoAtualizado = repository.save(caso);
		return paraDTO(casoAtualizado);
	}

	private casos_clinicos_DTO paraDTO(casos_clinicos caso) {
		casos_clinicos_DTO dto = new casos_clinicos_DTO();

		dto.setIdCaso(caso.getIdCaso());

		if (caso.getProfessor() != null) {
			dto.setIdProfessor(caso.getProfessor().getId());
		}

		dto.setTitulo(caso.getTitulo());
		dto.setDificuldade(caso.getDificuldade());
		dto.setDisciplina(caso.getDisciplina());
		dto.setAreaSaude(caso.getAreaSaude());
		dto.setEstilo(caso.getEstilo());
		dto.setEspecialidade(caso.getEspecialidade());

		return dto;
	}

	private casos_clinicos paraEntity(casos_clinicos_DTO dto) {
		casos_clinicos caso = new casos_clinicos();

		caso.setIdCaso(dto.getIdCaso());

		if (dto.getIdProfessor() != null) {
			Professor professor = professorRepository.findById(dto.getIdProfessor())
					.orElseThrow(() -> new RuntimeException("Professor não encontrado"));
			caso.setProfessor(professor);
		}

		caso.setTitulo(dto.getTitulo());
		caso.setDificuldade(dto.getDificuldade());
		caso.setDisciplina(dto.getDisciplina());
		caso.setAreaSaude(dto.getAreaSaude());
		caso.setEstilo(dto.getEstilo());
		caso.setEspecialidade(dto.getEspecialidade());

		return caso;
	}
}
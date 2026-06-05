package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.DTO.paciente_DTO;
import com.SistemaApiCrud.SistemaCrud.entity.casos_clinicos;
import com.SistemaApiCrud.SistemaCrud.entity.paciente;
import com.SistemaApiCrud.SistemaCrud.repository.caso_clinico_repository;
import com.SistemaApiCrud.SistemaCrud.repository.paciente_repository;

@Service
public class paciente_service {

	@Autowired
	private paciente_repository repository;

	@Autowired
	private caso_clinico_repository casoRepository;

	public List<paciente_DTO> listar() {
		return repository.findAll()
				.stream()
				.map(this::paraDTO)
				.toList();
	}

	public paciente_DTO salvar(paciente_DTO dto) {
		paciente paciente = paraEntity(dto);
		paciente pacienteSalvo = repository.save(paciente);
		return paraDTO(pacienteSalvo);
	}

	public void deletar(Long id) {
		repository.deleteById(id);
	}

	public paciente_DTO atualizar(Long id, paciente_DTO dto) {
		paciente paciente = paraEntity(dto);
		paciente.setIdPaciente(id);

		paciente pacienteAtualizado = repository.save(paciente);
		return paraDTO(pacienteAtualizado);
	}

	private paciente_DTO paraDTO(paciente paciente) {
		paciente_DTO dto = new paciente_DTO();

		dto.setIdPaciente(paciente.getIdPaciente());

		if (paciente.getCasoClinico() != null) {
			dto.setIdCaso(paciente.getCasoClinico().getIdCaso());
		}

		dto.setNome(paciente.getNome());
		dto.setProfissao(paciente.getProfissao());
		dto.setSexo(paciente.getSexo());
		dto.setIdade(paciente.getIdade());
		dto.setEstadoCivil(paciente.getEstadoCivil());
		dto.setAltura(paciente.getAltura());
		dto.setPeso(paciente.getPeso());

		return dto;
	}

	private paciente paraEntity(paciente_DTO dto) {
		paciente paciente = new paciente();

		paciente.setIdPaciente(dto.getIdPaciente());

		if (dto.getIdCaso() != null) {
			casos_clinicos caso = casoRepository.findById(dto.getIdCaso())
					.orElseThrow(() -> new RuntimeException("Caso clínico não encontrado"));
			paciente.setCasoClinico(caso);
		}

		paciente.setNome(dto.getNome());
		paciente.setProfissao(dto.getProfissao());
		paciente.setSexo(dto.getSexo());
		paciente.setIdade(dto.getIdade());
		paciente.setEstadoCivil(dto.getEstadoCivil());
		paciente.setAltura(dto.getAltura());
		paciente.setPeso(dto.getPeso());

		return paciente;
	}
}
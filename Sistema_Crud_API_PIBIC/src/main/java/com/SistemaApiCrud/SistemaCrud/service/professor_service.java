package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.DTO.professor_request_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.professor_response_DTO;
import com.SistemaApiCrud.SistemaCrud.entity.Professor;
import com.SistemaApiCrud.SistemaCrud.exception.RecursoNaoEncontradoException;
import com.SistemaApiCrud.SistemaCrud.mapper.ProfessorMapper;
import com.SistemaApiCrud.SistemaCrud.repository.professor_repository;

@Service
public class professor_service {

    private final professor_repository repository;
    private final ProfessorMapper mapper;

    public professor_service(professor_repository repository, ProfessorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<professor_response_DTO> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public professor_response_DTO buscarPorId(Long id) {
        return mapper.toResponse(buscarEntityPorId(id));
    }

    public professor_response_DTO salvar(professor_request_DTO dto) {
        Professor professor = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(professor));
    }

    public professor_response_DTO atualizar(Long id, professor_request_DTO dto) {
        Professor professor = buscarEntityPorId(id);
        mapper.updateEntity(dto, professor);
        return mapper.toResponse(repository.save(professor));
    }

    public void deletar(Long id) {
        buscarEntityPorId(id);
        repository.deleteById(id);
    }

    private Professor buscarEntityPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Professor nao encontrado"));
    }
}

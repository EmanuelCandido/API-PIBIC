package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.DTO.Professor_DTO;
import com.SistemaApiCrud.SistemaCrud.entity.Professor;
import com.SistemaApiCrud.SistemaCrud.exception.RecursoNaoEncontradoException;
import com.SistemaApiCrud.SistemaCrud.repository.professor_repository;

@Service
public class professor_service {

    @Autowired
    private professor_repository repository;

    public List<Professor_DTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::paraDTO)
                .toList();
    }

    public Professor_DTO buscarPorId(Long id) {
        return paraDTO(buscarEntityPorId(id));
    }

    public Professor_DTO salvar(Professor_DTO dto) {
        Professor professor = paraEntity(dto);
        Professor professorSalvo = repository.save(professor);
        return paraDTO(professorSalvo);
    }

    public Professor_DTO atualizar(Long id, Professor_DTO dto) {
        buscarEntityPorId(id);

        Professor professor = paraEntity(dto);
        professor.setId(id);

        Professor professorAtualizado = repository.save(professor);
        return paraDTO(professorAtualizado);
    }

    public void deletar(Long id) {
        buscarEntityPorId(id);
        repository.deleteById(id);
    }

    private Professor_DTO paraDTO(Professor professor) {
        Professor_DTO dto = new Professor_DTO();

        dto.setId(professor.getId());
        dto.setNome(professor.getNome());
        dto.setEmail(professor.getEmail());
        dto.setMateria(professor.getMateria());

        return dto;
    }

    private Professor paraEntity(Professor_DTO dto) {
        Professor professor = new Professor();

        professor.setId(dto.getId());
        professor.setNome(dto.getNome());
        professor.setEmail(dto.getEmail());
        professor.setMateria(dto.getMateria());

        return professor;
    }

    private Professor buscarEntityPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Professor nao encontrado"));
    }
}

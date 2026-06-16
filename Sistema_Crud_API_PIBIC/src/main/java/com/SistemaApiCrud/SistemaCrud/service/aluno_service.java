package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.DTO.aluno_DTO;
import com.SistemaApiCrud.SistemaCrud.entity.Aluno;
import com.SistemaApiCrud.SistemaCrud.exception.RecursoNaoEncontradoException;
import com.SistemaApiCrud.SistemaCrud.repository.aluno_repository;

@Service
public class aluno_service {

    @Autowired
    private aluno_repository repository;

    public List<aluno_DTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::paraDTO)
                .toList();
    }

    public aluno_DTO buscarPorId(Long id) {
        return paraDTO(buscarEntityPorId(id));
    }

    public aluno_DTO salvar(aluno_DTO dto) {
        Aluno aluno = paraEntity(dto);
        Aluno alunoSalvo = repository.save(aluno);
        return paraDTO(alunoSalvo);
    }

    public aluno_DTO atualizar(Long id, aluno_DTO dto) {
        buscarEntityPorId(id);

        Aluno aluno = paraEntity(dto);
        aluno.setIdAluno(id);

        Aluno alunoAtualizado = repository.save(aluno);
        return paraDTO(alunoAtualizado);
    }

    public void deletar(Long id) {
        buscarEntityPorId(id);
        repository.deleteById(id);
    }

    private aluno_DTO paraDTO(Aluno aluno) {
        aluno_DTO dto = new aluno_DTO();

        dto.setIdAluno(aluno.getIdAluno());
        dto.setNome(aluno.getNome());
        dto.setEmail(aluno.getEmail());
        dto.setCurso(aluno.getCurso());
        dto.setPeriodo(aluno.getPeriodo());

        return dto;
    }

    private Aluno paraEntity(aluno_DTO dto) {
        Aluno aluno = new Aluno();

        aluno.setIdAluno(dto.getIdAluno());
        aluno.setNome(dto.getNome());
        aluno.setEmail(dto.getEmail());
        aluno.setCurso(dto.getCurso());
        aluno.setPeriodo(dto.getPeriodo());

        return aluno;
    }

    private Aluno buscarEntityPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno nao encontrado"));
    }
}

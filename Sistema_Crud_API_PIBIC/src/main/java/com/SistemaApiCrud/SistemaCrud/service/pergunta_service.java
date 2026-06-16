package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.DTO.pergunta_DTO;
import com.SistemaApiCrud.SistemaCrud.entity.casos_clinicos;
import com.SistemaApiCrud.SistemaCrud.entity.pergunta;
import com.SistemaApiCrud.SistemaCrud.exception.RecursoNaoEncontradoException;
import com.SistemaApiCrud.SistemaCrud.repository.caso_clinico_repository;
import com.SistemaApiCrud.SistemaCrud.repository.pergunta_repository;

@Service
public class pergunta_service {

    @Autowired
    private pergunta_repository repository;

    @Autowired
    private caso_clinico_repository casoRepository;

    public List<pergunta_DTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::paraDTO)
                .toList();
    }

    public pergunta_DTO buscarPorId(Long id) {
        return paraDTO(buscarEntityPorId(id));
    }

    public List<pergunta_DTO> listarPorCaso(Long idCaso) {
        if (!casoRepository.existsById(idCaso)) {
            throw new RecursoNaoEncontradoException("Caso clinico nao encontrado");
        }

        return repository.findByCasoClinicoIdCaso(idCaso)
                .stream()
                .map(this::paraDTO)
                .toList();
    }

    public pergunta_DTO salvar(pergunta_DTO dto) {
        pergunta pergunta = paraEntity(dto);
        pergunta perguntaSalva = repository.save(pergunta);
        return paraDTO(perguntaSalva);
    }

    public pergunta_DTO salvarEmCaso(Long idCaso, pergunta_DTO dto) {
        dto.setIdCaso(idCaso);
        return salvar(dto);
    }

    public pergunta_DTO atualizar(Long id, pergunta_DTO dto) {
        buscarEntityPorId(id);

        pergunta pergunta = paraEntity(dto);
        pergunta.setId(id);

        pergunta perguntaAtualizada = repository.save(pergunta);
        return paraDTO(perguntaAtualizada);
    }

    public void deletar(Long id) {
        buscarEntityPorId(id);
        repository.deleteById(id);
    }

    private pergunta_DTO paraDTO(pergunta pergunta) {
        pergunta_DTO dto = new pergunta_DTO();

        dto.setId(pergunta.getId());

        if (pergunta.getCasoClinico() != null) {
            dto.setIdCaso(pergunta.getCasoClinico().getIdCaso());
        }

        dto.setTexto(pergunta.getTexto());
        dto.setAlternativaA(pergunta.getAlternativaA());
        dto.setAlternativaB(pergunta.getAlternativaB());
        dto.setAlternativaC(pergunta.getAlternativaC());
        dto.setAlternativaD(pergunta.getAlternativaD());
        dto.setAlternativaE(pergunta.getAlternativaE());
        dto.setResposta(pergunta.getResposta());
        dto.setTipo(pergunta.getTipo());
        dto.setGabarito(pergunta.getGabarito());
        dto.setTempoEsperado(pergunta.getTempoEsperado());

        return dto;
    }

    private pergunta paraEntity(pergunta_DTO dto) {
        pergunta pergunta = new pergunta();

        pergunta.setId(dto.getId());

        if (dto.getIdCaso() != null) {
            casos_clinicos caso = casoRepository.findById(dto.getIdCaso())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Caso clinico nao encontrado"));
            pergunta.setCasoClinico(caso);
        }

        pergunta.setTexto(dto.getTexto());
        pergunta.setAlternativaA(dto.getAlternativaA());
        pergunta.setAlternativaB(dto.getAlternativaB());
        pergunta.setAlternativaC(dto.getAlternativaC());
        pergunta.setAlternativaD(dto.getAlternativaD());
        pergunta.setAlternativaE(dto.getAlternativaE());
        pergunta.setResposta(dto.getResposta());
        pergunta.setTipo(dto.getTipo());
        pergunta.setGabarito(dto.getGabarito());
        pergunta.setTempoEsperado(dto.getTempoEsperado());

        return pergunta;
    }

    private pergunta buscarEntityPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pergunta nao encontrada"));
    }
}

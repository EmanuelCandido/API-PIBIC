package com.SistemaApiCrud.SistemaCrud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SistemaApiCrud.SistemaCrud.DTO.alternativa_pergunta_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.pergunta_DTO;
import com.SistemaApiCrud.SistemaCrud.entity.AlternativaPergunta;
import com.SistemaApiCrud.SistemaCrud.entity.casos_clinicos;
import com.SistemaApiCrud.SistemaCrud.entity.pergunta;
import com.SistemaApiCrud.SistemaCrud.exception.BadRequestException;
import com.SistemaApiCrud.SistemaCrud.exception.RecursoNaoEncontradoException;
import com.SistemaApiCrud.SistemaCrud.repository.alternativa_pergunta_repository;
import com.SistemaApiCrud.SistemaCrud.repository.caso_clinico_repository;
import com.SistemaApiCrud.SistemaCrud.repository.pergunta_repository;

@Service
public class pergunta_service {

    @Autowired
    private pergunta_repository repository;

    @Autowired
    private caso_clinico_repository casoRepository;

    @Autowired
    private alternativa_pergunta_repository alternativaRepository;

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

    @Transactional
    public pergunta_DTO salvar(pergunta_DTO dto) {
        validarPergunta(dto);
        pergunta pergunta = paraEntity(dto);
        pergunta perguntaSalva = repository.save(pergunta);
        salvarAlternativas(perguntaSalva, dto);
        return paraDTO(perguntaSalva);
    }

    public pergunta_DTO salvarEmCaso(Long idCaso, pergunta_DTO dto) {
        dto.setIdCaso(idCaso);
        return salvar(dto);
    }

    @Transactional
    public pergunta_DTO atualizar(Long id, pergunta_DTO dto) {
        buscarEntityPorId(id);
        validarPergunta(dto);

        pergunta pergunta = paraEntity(dto);
        pergunta.setId(id);

        pergunta perguntaAtualizada = repository.save(pergunta);
        alternativaRepository.deleteByPerguntaId(id);
        alternativaRepository.flush();
        salvarAlternativas(perguntaAtualizada, dto);
        return paraDTO(perguntaAtualizada);
    }

    @Transactional
    public void deletar(Long id) {
        buscarEntityPorId(id);
        alternativaRepository.deleteByPerguntaId(id);
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
        dto.setAlternativas(buscarAlternativasDTO(pergunta));

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

    private void validarPergunta(pergunta_DTO dto) {
        if ("MULTIPLA_ESCOLHA".equalsIgnoreCase(dto.getTipo()) && montarAlternativasDTO(dto).isEmpty()) {
            throw new BadRequestException("Perguntas de multipla escolha precisam ter alternativas");
        }
    }

    private void salvarAlternativas(pergunta pergunta, pergunta_DTO dto) {
        List<AlternativaPergunta> alternativas = montarAlternativasDTO(dto).stream()
                .map(alternativaDTO -> paraAlternativaEntity(pergunta, alternativaDTO, dto))
                .toList();

        if (!alternativas.isEmpty()) {
            alternativaRepository.saveAll(alternativas);
        }
    }

    private List<alternativa_pergunta_DTO> buscarAlternativasDTO(pergunta pergunta) {
        if (pergunta.getId() == null) {
            return List.of();
        }

        List<alternativa_pergunta_DTO> alternativas = alternativaRepository.findByPerguntaIdOrderByLetra(pergunta.getId())
                .stream()
                .map(this::paraAlternativaDTO)
                .toList();

        if (!alternativas.isEmpty()) {
            return alternativas;
        }

        pergunta_DTO dto = new pergunta_DTO();
        dto.setAlternativaA(pergunta.getAlternativaA());
        dto.setAlternativaB(pergunta.getAlternativaB());
        dto.setAlternativaC(pergunta.getAlternativaC());
        dto.setAlternativaD(pergunta.getAlternativaD());
        dto.setAlternativaE(pergunta.getAlternativaE());
        dto.setGabarito(pergunta.getGabarito());
        dto.setResposta(pergunta.getResposta());

        return montarAlternativasLegadas(dto);
    }

    private AlternativaPergunta paraAlternativaEntity(
            pergunta pergunta,
            alternativa_pergunta_DTO dto,
            pergunta_DTO perguntaDTO) {
        AlternativaPergunta alternativa = new AlternativaPergunta();
        alternativa.setPergunta(pergunta);
        alternativa.setLetra(dto.getLetra().trim().toUpperCase());
        alternativa.setTexto(dto.getTexto());
        alternativa.setCorreta(dto.getCorreta() != null
                ? dto.getCorreta()
                : correspondeGabarito(dto.getLetra(), perguntaDTO));

        return alternativa;
    }

    private alternativa_pergunta_DTO paraAlternativaDTO(AlternativaPergunta alternativa) {
        return new alternativa_pergunta_DTO(
                alternativa.getId(),
                alternativa.getLetra(),
                alternativa.getTexto(),
                alternativa.getCorreta());
    }

    private List<alternativa_pergunta_DTO> montarAlternativasDTO(pergunta_DTO dto) {
        if (dto.getAlternativas() != null && !dto.getAlternativas().isEmpty()) {
            return dto.getAlternativas();
        }

        return montarAlternativasLegadas(dto);
    }

    private List<alternativa_pergunta_DTO> montarAlternativasLegadas(pergunta_DTO dto) {
        List<alternativa_pergunta_DTO> alternativas = new ArrayList<>();

        adicionarAlternativaLegada(alternativas, "A", dto.getAlternativaA(), dto);
        adicionarAlternativaLegada(alternativas, "B", dto.getAlternativaB(), dto);
        adicionarAlternativaLegada(alternativas, "C", dto.getAlternativaC(), dto);
        adicionarAlternativaLegada(alternativas, "D", dto.getAlternativaD(), dto);
        adicionarAlternativaLegada(alternativas, "E", dto.getAlternativaE(), dto);

        return alternativas;
    }

    private void adicionarAlternativaLegada(
            List<alternativa_pergunta_DTO> alternativas,
            String letra,
            String texto,
            pergunta_DTO perguntaDTO) {
        if (texto == null || texto.isBlank()) {
            return;
        }

        alternativas.add(new alternativa_pergunta_DTO(null, letra, texto, correspondeGabarito(letra, perguntaDTO)));
    }

    private boolean correspondeGabarito(String letra, pergunta_DTO perguntaDTO) {
        return corresponde(letra, perguntaDTO.getGabarito()) || corresponde(letra, perguntaDTO.getResposta());
    }

    private boolean corresponde(String valor, String referencia) {
        return valor != null && referencia != null && valor.trim().equalsIgnoreCase(referencia.trim());
    }
}

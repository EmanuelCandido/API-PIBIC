package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.DTO.desempenho_aluno_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.historico_aluno_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.relatorio_desempenho_professor_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.responder_caso_request_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.resposta_aluno_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.resposta_pergunta_request_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.resultado_caso_DTO;
import com.SistemaApiCrud.SistemaCrud.entity.Aluno;
import com.SistemaApiCrud.SistemaCrud.entity.RespostaAluno;
import com.SistemaApiCrud.SistemaCrud.entity.casos_clinicos;
import com.SistemaApiCrud.SistemaCrud.entity.enums.StatusCasoClinico;
import com.SistemaApiCrud.SistemaCrud.entity.pergunta;
import com.SistemaApiCrud.SistemaCrud.exception.BadRequestException;
import com.SistemaApiCrud.SistemaCrud.exception.BusinessException;
import com.SistemaApiCrud.SistemaCrud.exception.RecursoNaoEncontradoException;
import com.SistemaApiCrud.SistemaCrud.repository.aluno_repository;
import com.SistemaApiCrud.SistemaCrud.repository.caso_clinico_repository;
import com.SistemaApiCrud.SistemaCrud.repository.pergunta_repository;
import com.SistemaApiCrud.SistemaCrud.repository.professor_repository;
import com.SistemaApiCrud.SistemaCrud.repository.resposta_aluno_repository;

@Service
public class resposta_aluno_service {

    @Autowired
    private resposta_aluno_repository repository;

    @Autowired
    private aluno_repository alunoRepository;

    @Autowired
    private caso_clinico_repository casoRepository;

    @Autowired
    private pergunta_repository perguntaRepository;

    @Autowired
    private professor_repository professorRepository;

    public resultado_caso_DTO responderCaso(Long idAluno, Long idCaso, responder_caso_request_DTO request) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno nao encontrado"));

        casos_clinicos caso = casoRepository.findById(idCaso)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Caso clinico nao encontrado"));

        if (caso.getStatus() != StatusCasoClinico.PUBLICADO) {
            throw new BusinessException("O caso clinico ainda nao esta publicado");
        }

        List<RespostaAluno> respostas = request.getRespostas()
                .stream()
                .map(resposta -> criarResposta(aluno, caso, resposta))
                .toList();

        List<RespostaAluno> respostasSalvas = repository.saveAll(respostas);
        return montarResultado(idAluno, idCaso, respostasSalvas);
    }

    public historico_aluno_DTO buscarHistorico(Long idAluno) {
        if (!alunoRepository.existsById(idAluno)) {
            throw new RecursoNaoEncontradoException("Aluno nao encontrado");
        }

        List<resposta_aluno_DTO> respostas = repository.findByAlunoIdAlunoOrderByDataRespostaDesc(idAluno)
                .stream()
                .map(this::paraDTO)
                .toList();

        return new historico_aluno_DTO(idAluno, respostas);
    }

    public desempenho_aluno_DTO buscarDesempenho(Long idAluno) {
        if (!alunoRepository.existsById(idAluno)) {
            throw new RecursoNaoEncontradoException("Aluno nao encontrado");
        }

        List<RespostaAluno> respostas = repository.findByAlunoIdAlunoOrderByDataRespostaDesc(idAluno);
        long total = respostas.size();
        long corretas = respostas.stream().filter(resposta -> Boolean.TRUE.equals(resposta.getCorreta())).count();

        return new desempenho_aluno_DTO(idAluno, total, corretas, calcularAproveitamento(total, corretas));
    }

    public relatorio_desempenho_professor_DTO gerarRelatorioProfessor(Long idProfessor) {
        if (!professorRepository.existsById(idProfessor)) {
            throw new RecursoNaoEncontradoException("Professor nao encontrado");
        }

        List<RespostaAluno> respostas = repository.findByCasoClinicoProfessorId(idProfessor);
        long total = respostas.size();
        long corretas = respostas.stream().filter(resposta -> Boolean.TRUE.equals(resposta.getCorreta())).count();

        return new relatorio_desempenho_professor_DTO(idProfessor, total, corretas, calcularAproveitamento(total, corretas));
    }

    private RespostaAluno criarResposta(Aluno aluno, casos_clinicos caso, resposta_pergunta_request_DTO respostaRequest) {
        pergunta pergunta = perguntaRepository.findById(respostaRequest.getIdPergunta())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pergunta nao encontrada"));

        if (pergunta.getCasoClinico() == null || !pergunta.getCasoClinico().getIdCaso().equals(caso.getIdCaso())) {
            throw new BadRequestException("A pergunta informada nao pertence ao caso clinico");
        }

        RespostaAluno respostaAluno = new RespostaAluno();
        respostaAluno.setAluno(aluno);
        respostaAluno.setCasoClinico(caso);
        respostaAluno.setPergunta(pergunta);
        respostaAluno.setRespostaMarcada(respostaRequest.getRespostaMarcada());
        respostaAluno.setCorreta(compararResposta(pergunta, respostaRequest.getRespostaMarcada()));

        return respostaAluno;
    }

    private boolean compararResposta(pergunta pergunta, String respostaMarcada) {
        String gabarito = pergunta.getGabarito();

        if (gabarito == null || gabarito.isBlank()) {
            gabarito = pergunta.getResposta();
        }

        return gabarito != null && gabarito.trim().equalsIgnoreCase(respostaMarcada.trim());
    }

    private resultado_caso_DTO montarResultado(Long idAluno, Long idCaso, List<RespostaAluno> respostas) {
        int total = respostas.size();
        int corretas = (int) respostas.stream().filter(resposta -> Boolean.TRUE.equals(resposta.getCorreta())).count();

        List<resposta_aluno_DTO> respostasDTO = respostas.stream()
                .map(this::paraDTO)
                .toList();

        return new resultado_caso_DTO(idAluno, idCaso, total, corretas, calcularAproveitamento(total, corretas), respostasDTO);
    }

    private Double calcularAproveitamento(long total, long corretas) {
        if (total == 0) {
            return 0.0;
        }

        return (corretas * 100.0) / total;
    }

    private resposta_aluno_DTO paraDTO(RespostaAluno resposta) {
        resposta_aluno_DTO dto = new resposta_aluno_DTO();
        dto.setId(resposta.getId());

        if (resposta.getAluno() != null) {
            dto.setIdAluno(resposta.getAluno().getIdAluno());
        }

        if (resposta.getCasoClinico() != null) {
            dto.setIdCaso(resposta.getCasoClinico().getIdCaso());
        }

        if (resposta.getPergunta() != null) {
            dto.setIdPergunta(resposta.getPergunta().getId());
        }

        dto.setRespostaMarcada(resposta.getRespostaMarcada());
        dto.setCorreta(resposta.getCorreta());
        dto.setDataResposta(resposta.getDataResposta());

        return dto;
    }
}

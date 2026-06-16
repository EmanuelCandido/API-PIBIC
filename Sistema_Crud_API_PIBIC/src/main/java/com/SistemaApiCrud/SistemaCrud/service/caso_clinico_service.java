package com.SistemaApiCrud.SistemaCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaApiCrud.SistemaCrud.DTO.caso_clinico_completo_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.casos_clinicos_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.conteudo_clinico_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.paciente_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.pergunta_DTO;
import com.SistemaApiCrud.SistemaCrud.entity.Professor;
import com.SistemaApiCrud.SistemaCrud.entity.casos_clinicos;
import com.SistemaApiCrud.SistemaCrud.entity.conteudo_clinico;
import com.SistemaApiCrud.SistemaCrud.entity.enums.StatusCasoClinico;
import com.SistemaApiCrud.SistemaCrud.entity.paciente;
import com.SistemaApiCrud.SistemaCrud.entity.pergunta;
import com.SistemaApiCrud.SistemaCrud.exception.RecursoNaoEncontradoException;
import com.SistemaApiCrud.SistemaCrud.repository.caso_clinico_repository;
import com.SistemaApiCrud.SistemaCrud.repository.conteudo_clinico_repository;
import com.SistemaApiCrud.SistemaCrud.repository.paciente_repository;
import com.SistemaApiCrud.SistemaCrud.repository.pergunta_repository;
import com.SistemaApiCrud.SistemaCrud.repository.professor_repository;

@Service
public class caso_clinico_service {

    @Autowired
    private caso_clinico_repository repository;

    @Autowired
    private professor_repository professorRepository;

    @Autowired
    private paciente_repository pacienteRepository;

    @Autowired
    private conteudo_clinico_repository conteudoRepository;

    @Autowired
    private pergunta_repository perguntaRepository;

    public List<casos_clinicos_DTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::paraDTO)
                .toList();
    }

    public List<casos_clinicos_DTO> listarPublicados() {
        return repository.findByStatus(StatusCasoClinico.PUBLICADO)
                .stream()
                .map(this::paraDTO)
                .toList();
    }

    public List<casos_clinicos_DTO> listarPorProfessor(Long idProfessor) {
        if (!professorRepository.existsById(idProfessor)) {
            throw new RecursoNaoEncontradoException("Professor nao encontrado");
        }

        return repository.findByProfessorId(idProfessor)
                .stream()
                .map(this::paraDTO)
                .toList();
    }

    public casos_clinicos_DTO buscarPorId(Long id) {
        return paraDTO(buscarEntityPorId(id));
    }

    public caso_clinico_completo_DTO buscarCompletoPorId(Long id) {
        casos_clinicos caso = buscarEntityPorId(id);

        List<paciente_DTO> pacientes = pacienteRepository.findByCasoClinicoIdCaso(id)
                .stream()
                .map(this::paraPacienteDTO)
                .toList();

        List<conteudo_clinico_DTO> conteudos = conteudoRepository.findByCasoClinicoIdCaso(id)
                .stream()
                .map(this::paraConteudoDTO)
                .toList();

        List<pergunta_DTO> perguntas = perguntaRepository.findByCasoClinicoIdCaso(id)
                .stream()
                .map(this::paraPerguntaDTO)
                .toList();

        return new caso_clinico_completo_DTO(paraDTO(caso), pacientes, conteudos, perguntas);
    }

    public casos_clinicos_DTO salvar(casos_clinicos_DTO dto) {
        casos_clinicos caso = paraEntity(dto);
        casos_clinicos casoSalvo = repository.save(caso);
        return paraDTO(casoSalvo);
    }

    public casos_clinicos_DTO atualizar(Long id, casos_clinicos_DTO dto) {
        buscarEntityPorId(id);

        casos_clinicos caso = paraEntity(dto);
        caso.setIdCaso(id);

        casos_clinicos casoAtualizado = repository.save(caso);
        return paraDTO(casoAtualizado);
    }

    public casos_clinicos_DTO publicar(Long id) {
        casos_clinicos caso = buscarEntityPorId(id);
        caso.setStatus(StatusCasoClinico.PUBLICADO);
        return paraDTO(repository.save(caso));
    }

    public void deletar(Long id) {
        buscarEntityPorId(id);
        repository.deleteById(id);
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
        dto.setStatus(caso.getStatus());
        dto.setDataCriacao(caso.getDataCriacao());
        dto.setDataAtualizacao(caso.getDataAtualizacao());
        dto.setObjetivoAprendizagem(caso.getObjetivoAprendizagem());
        dto.setNivelDificuldade(caso.getNivelDificuldade());

        return dto;
    }

    private casos_clinicos paraEntity(casos_clinicos_DTO dto) {
        casos_clinicos caso = new casos_clinicos();

        caso.setIdCaso(dto.getIdCaso());

        if (dto.getIdProfessor() != null) {
            Professor professor = professorRepository.findById(dto.getIdProfessor())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Professor nao encontrado"));
            caso.setProfessor(professor);
        }

        caso.setTitulo(dto.getTitulo());
        caso.setDificuldade(dto.getDificuldade());
        caso.setDisciplina(dto.getDisciplina());
        caso.setAreaSaude(dto.getAreaSaude());
        caso.setEstilo(dto.getEstilo());
        caso.setEspecialidade(dto.getEspecialidade());
        caso.setStatus(dto.getStatus());
        caso.setObjetivoAprendizagem(dto.getObjetivoAprendizagem());
        caso.setNivelDificuldade(dto.getNivelDificuldade());

        return caso;
    }

    private paciente_DTO paraPacienteDTO(paciente paciente) {
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

    private conteudo_clinico_DTO paraConteudoDTO(conteudo_clinico conteudo) {
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

    private pergunta_DTO paraPerguntaDTO(pergunta pergunta) {
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

    private casos_clinicos buscarEntityPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Caso clinico nao encontrado"));
    }
}

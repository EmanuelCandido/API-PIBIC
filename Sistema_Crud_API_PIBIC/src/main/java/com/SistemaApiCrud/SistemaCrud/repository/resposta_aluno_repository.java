package com.SistemaApiCrud.SistemaCrud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SistemaApiCrud.SistemaCrud.entity.RespostaAluno;

public interface resposta_aluno_repository extends JpaRepository<RespostaAluno, Long> {

    List<RespostaAluno> findByAlunoIdAlunoOrderByDataRespostaDesc(Long idAluno);

    List<RespostaAluno> findByAlunoIdAlunoAndCasoClinicoIdCaso(Long idAluno, Long idCaso);

    List<RespostaAluno> findByCasoClinicoProfessorId(Long idProfessor);
}

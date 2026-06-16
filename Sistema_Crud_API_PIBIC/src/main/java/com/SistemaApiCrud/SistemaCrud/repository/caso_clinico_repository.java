package com.SistemaApiCrud.SistemaCrud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SistemaApiCrud.SistemaCrud.entity.casos_clinicos;
import com.SistemaApiCrud.SistemaCrud.entity.enums.StatusCasoClinico;

public interface caso_clinico_repository extends JpaRepository<casos_clinicos, Long> {

    List<casos_clinicos> findByProfessorId(Long idProfessor);

    List<casos_clinicos> findByStatus(StatusCasoClinico status);
}

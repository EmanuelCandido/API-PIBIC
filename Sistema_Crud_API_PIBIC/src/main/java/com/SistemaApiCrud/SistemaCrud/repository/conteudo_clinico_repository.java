package com.SistemaApiCrud.SistemaCrud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SistemaApiCrud.SistemaCrud.entity.conteudo_clinico;

public interface conteudo_clinico_repository extends JpaRepository<conteudo_clinico, Long> {

    List<conteudo_clinico> findByCasoClinicoIdCaso(Long idCaso);
}

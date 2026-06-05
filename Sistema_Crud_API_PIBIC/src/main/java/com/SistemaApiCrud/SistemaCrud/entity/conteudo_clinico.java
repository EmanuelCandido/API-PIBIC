package com.SistemaApiCrud.SistemaCrud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "conteudo_clinico")
public class conteudo_clinico {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConteudo;

    @ManyToOne
    @JoinColumn(name = "fk_id_caso")
    private casos_clinicos casoClinico;

    private String sintomas;

    private String contexto;

    private String examClinico;

    private String antecClinico;

    private String diagEsperado;
	
	
	
}

package com.SistemaApiCrud.SistemaCrud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
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
    @Column(name = "id_conteudo")
    private Long idConteudo;

    @ManyToOne
    @JoinColumn(name = "fk_id_caso")
    private casos_clinicos casoClinico;

    private String sintomas;

    private String contexto;

    @Column(name = "exam_clinico")
    private String examClinico;

    @Column(name = "antec_clinico")
    private String antecClinico;

    @Column(name = "diag_esperado")
    private String diagEsperado;
	
	
	
}

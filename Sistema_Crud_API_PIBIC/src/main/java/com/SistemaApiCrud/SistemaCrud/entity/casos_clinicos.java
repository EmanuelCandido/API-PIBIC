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
@Table(name = "casos_clinicos")
public class casos_clinicos {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long idCaso;

	    @ManyToOne
	    @JoinColumn(name = "fk_id_professor")
	    private Professor professor;

	    private String titulo;

	    private String dificuldade;

	    private String disciplina;

	    private String areaSaude;

	    private String estilo;

	    private String especialidade;
	
	
}

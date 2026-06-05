package com.SistemaApiCrud.SistemaCrud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "aluno")
public class Aluno {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long idAluno;

	    private String nome;

	    private String email;

	    private String curso;

	    private String periodo;
	
	    
	
	
}

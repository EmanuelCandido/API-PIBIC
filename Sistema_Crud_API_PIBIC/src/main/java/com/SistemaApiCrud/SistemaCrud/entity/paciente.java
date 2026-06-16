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
@Table(name = "paciente")
public class paciente {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id_paciente")
	    private Long idPaciente;

	    @ManyToOne
	    @JoinColumn(name = "fk_id_caso")
	    private casos_clinicos casoClinico;

	    private String nome;

	    private String profissao;

	    private String sexo;

	    private Integer idade;

	    @Column(name = "estado_civil")
	    private String estadoCivil;

	    private String altura;

	    private String peso;
	
	
	
	
}

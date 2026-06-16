package com.SistemaApiCrud.SistemaCrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.SistemaApiCrud.SistemaCrud.entity.enums.EstadoCivil;
import com.SistemaApiCrud.SistemaCrud.entity.enums.Sexo;

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

        @Enumerated(EnumType.STRING)
	    private Sexo sexo;

	    private Integer idade;

        @Enumerated(EnumType.STRING)
	    @Column(name = "estado_civil")
	    private EstadoCivil estadoCivil;

	    private String altura;

	    private String peso;
	
	
	
	
}

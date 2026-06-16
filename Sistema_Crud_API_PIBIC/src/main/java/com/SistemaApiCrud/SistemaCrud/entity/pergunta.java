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
@Table(name = "pergunta")
public class pergunta {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "fk_id_caso")
	    private casos_clinicos casoClinico;

	    private String texto;

	    @Column(name = "alternativa_a")
	    private String alternativaA;

	    @Column(name = "alternativa_b")
	    private String alternativaB;

	    @Column(name = "alternativa_c")
	    private String alternativaC;

	    @Column(name = "alternativa_d")
	    private String alternativaD;

	    @Column(name = "alternativa_e")
	    private String alternativaE;
	    
	    private String resposta;

	    private String tipo;

	    private String gabarito;

	    @Column(name = "tempo_esperado")
	    private String tempoEsperado;
	    
	    
	
	
	
}

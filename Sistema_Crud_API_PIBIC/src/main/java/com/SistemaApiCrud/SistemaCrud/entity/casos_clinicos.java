package com.SistemaApiCrud.SistemaCrud.entity;

import java.time.LocalDateTime;

import com.SistemaApiCrud.SistemaCrud.entity.enums.NivelDificuldade;
import com.SistemaApiCrud.SistemaCrud.entity.enums.StatusCasoClinico;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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

    @Enumerated(EnumType.STRING)
    private StatusCasoClinico status;

    @Column(updatable = false)
    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    private String objetivoAprendizagem;

    @Enumerated(EnumType.STRING)
    private NivelDificuldade nivelDificuldade;

    @PrePersist
    public void antesDeCriar() {
        LocalDateTime agora = LocalDateTime.now();
        dataCriacao = agora;
        dataAtualizacao = agora;

        if (status == null) {
            status = StatusCasoClinico.RASCUNHO;
        }
    }

    @PreUpdate
    public void antesDeAtualizar() {
        dataAtualizacao = LocalDateTime.now();
    }
}

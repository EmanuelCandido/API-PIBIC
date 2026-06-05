package com.SistemaApiCrud.SistemaCrud.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class casos_clinicos_DTO {

    private Long idCaso;

    private Long idProfessor;

    private String titulo;
    private String dificuldade;
    private String disciplina;
    private String areaSaude;
    private String estilo;
    private String especialidade;
}
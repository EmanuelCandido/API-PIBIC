package com.SistemaApiCrud.SistemaCrud.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "O professor é obrigatório")
    private Long idProfessor;

    @NotBlank(message = "O título do caso clínico é obrigatório")
    private String titulo;

    @NotBlank(message = "A dificuldade é obrigatória")
    private String dificuldade;

    @NotBlank(message = "A disciplina é obrigatória")
    private String disciplina;

    @NotBlank(message = "A área da saúde é obrigatória")
    private String areaSaude;

    @NotBlank(message = "O estilo é obrigatório")
    private String estilo;

    @NotBlank(message = "A especialidade é obrigatória")
    private String especialidade;
}
package com.SistemaApiCrud.SistemaCrud.DTO;

import com.SistemaApiCrud.SistemaCrud.entity.enums.NivelDificuldade;
import com.SistemaApiCrud.SistemaCrud.entity.enums.StatusCasoClinico;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class caso_clinico_request_DTO {

    @Min(value = 1, message = "O professor informado e invalido")
    private Long idProfessor;

    @NotBlank(message = "O titulo do caso clinico e obrigatorio")
    private String titulo;

    @NotBlank(message = "A dificuldade e obrigatoria")
    private String dificuldade;

    @NotBlank(message = "A disciplina e obrigatoria")
    private String disciplina;

    @NotBlank(message = "A area da saude e obrigatoria")
    private String areaSaude;

    @NotBlank(message = "O estilo e obrigatorio")
    private String estilo;

    @NotBlank(message = "A especialidade e obrigatoria")
    private String especialidade;

    private StatusCasoClinico status;

    private String objetivoAprendizagem;

    private NivelDificuldade nivelDificuldade;
}

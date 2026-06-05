package com.SistemaApiCrud.SistemaCrud.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class paciente_DTO {

    private Long idPaciente;

    private Long idCaso;

    private String nome;
    private String profissao;
    private String sexo;

    @Min(value = 0, message = "Idade invalida")
    @Max(value = 130, message = "Idade invalida")
    private Integer idade;
    private String estadoCivil;
    private String altura;
    private String peso;
}
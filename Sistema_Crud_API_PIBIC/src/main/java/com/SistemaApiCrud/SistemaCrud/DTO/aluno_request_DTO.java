package com.SistemaApiCrud.SistemaCrud.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class aluno_request_DTO {

    @NotBlank(message = "O nome do aluno e obrigatorio")
    private String nome;

    @NotBlank(message = "E-mail obrigatorio")
    @Email(message = "E-mail invalido")
    private String email;

    @NotBlank(message = "O curso e obrigatorio")
    private String curso;

    @NotBlank(message = "Periodo obrigatorio")
    private String periodo;
}

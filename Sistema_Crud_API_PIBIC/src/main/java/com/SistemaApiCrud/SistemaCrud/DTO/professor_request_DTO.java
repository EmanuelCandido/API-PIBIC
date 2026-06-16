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
public class professor_request_DTO {

    @NotBlank(message = "O nome do professor e obrigatorio")
    private String nome;

    @NotBlank(message = "E-mail obrigatorio")
    @Email(message = "E-mail invalido")
    private String email;

    @NotBlank(message = "A materia e obrigatoria")
    private String materia;
}

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
public class Professor_DTO {

    private Long id;

    @NotBlank(message = "O nome do professor é obrigatório")
    private String nome;

    @NotBlank(message = "E-mail obrigatório")
    @Email(message = "E-mail válido")
    private String email;

    @NotBlank(message = "A matéria é obrigatória")
    private String materia;
}
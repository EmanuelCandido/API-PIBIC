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

public class aluno_DTO {
    private Long idAluno;

    @NotBlank(message = "O nome do aluno é obrigatório")
    private String nome;

    @NotBlank(message = "E-mail obrigatório")
    @Email(message = "E-mail válido")
    private String email;

    @NotBlank(message = "A curso é obrigatória")
    private String curso;

    @NotBlank(message = "Periodo obrigatório")
    private String periodo;

}

package com.SistemaApiCrud.SistemaCrud.DTO;

import jakarta.validation.constraints.Min;
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
public class pergunta_DTO {

    private Long id;

    @NotNull(message = "O caso clinico e obrigatorio")
    @Min(value = 1, message = "O caso clinico informado e invalido")
    private Long idCaso;

    @NotBlank(message = "Pergunta vazia")
    private String texto;

    @NotBlank(message = "A alternativa A e obrigatoria")
    private String alternativaA;

    @NotBlank(message = "A alternativa B e obrigatoria")
    private String alternativaB;

    @NotBlank(message = "A alternativa C e obrigatoria")
    private String alternativaC;

    @NotBlank(message = "A alternativa D e obrigatoria")
    private String alternativaD;

    @NotBlank(message = "A alternativa E e obrigatoria")
    private String alternativaE;

    @NotBlank(message = "A resposta e obrigatoria")
    private String resposta;

    @NotBlank(message = "O tipo da pergunta e obrigatorio")
    private String tipo;

    @NotBlank(message = "O gabarito e obrigatorio")
    private String gabarito;

    @NotBlank(message = "O tempo esperado e obrigatorio")
    private String tempoEsperado;
}

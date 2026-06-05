package com.SistemaApiCrud.SistemaCrud.DTO;

import jakarta.validation.constraints.NotBlank;
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

    private Long idCaso;

    @NotBlank(message = "Pergunta vazia")
    private String texto;

    @NotBlank(message = "A alternativa A é obrigatória")
    private String alternativaA;

    @NotBlank(message = "A alternativa B é obrigatória")
    private String alternativaB;

    @NotBlank(message = "A alternativa C é obrigatória")
    private String alternativaC;

    @NotBlank(message = "A alternativa D é obrigatória")
    private String alternativaD;

    @NotBlank(message = "A alternativa E é obrigatória")
    private String alternativaE;

    private String resposta;

    @NotBlank(message = "O tipo da pergunta é obrigatório")
    private String tipo;

    @NotBlank(message = "O gabarito é obrigatório")
    private String gabarito;
    private String tempoEsperado;
}
package com.SistemaApiCrud.SistemaCrud.DTO;

import com.SistemaApiCrud.SistemaCrud.entity.enums.PapelUsuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class usuario_request_DTO {

    @NotBlank(message = "O usuario e obrigatorio")
    private String username;

    @NotBlank(message = "A senha e obrigatoria")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String senha;

    @NotNull(message = "A role e obrigatoria")
    private PapelUsuario role;

    private Boolean ativo;
}

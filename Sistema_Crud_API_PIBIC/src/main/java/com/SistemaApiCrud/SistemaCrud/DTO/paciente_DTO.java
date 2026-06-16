import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.SistemaApiCrud.SistemaCrud.entity.enums.EstadoCivil;
import com.SistemaApiCrud.SistemaCrud.entity.enums.Sexo;

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

    @NotNull(message = "O caso clinico e obrigatorio")
    @Min(value = 1, message = "O caso clinico informado e invalido")
    private Long idCaso;

    @NotBlank(message = "O nome do paciente e obrigatorio")
    private String nome;

    @NotBlank(message = "A profissao e obrigatoria")
    private String profissao;

    @NotNull(message = "O sexo e obrigatorio")
    private Sexo sexo;

    @NotNull(message = "A idade e obrigatoria")
    @Min(value = 0, message = "Idade invalida")
    @Max(value = 130, message = "Idade invalida")
    private Integer idade;

    @NotNull(message = "O estado civil e obrigatorio")
    private EstadoCivil estadoCivil;

    @NotBlank(message = "A altura e obrigatoria")
    private String altura;

    @NotBlank(message = "O peso e obrigatorio")
    private String peso;
}

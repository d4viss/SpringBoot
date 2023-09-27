package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCancelarConsulta (
        @NotNull Long idConsulta,
        @NotBlank MotivoCancelamiento motivo
){
}

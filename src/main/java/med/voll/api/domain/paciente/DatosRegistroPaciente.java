package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroPaciente(
        @NotBlank(message = "{nombre.obligatorio}") String nombre
        ) {

}

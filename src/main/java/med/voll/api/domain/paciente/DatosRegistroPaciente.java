package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroPaciente(
        Long id, @NotBlank(message = "{nombre.obligatorio}") String nombre,
        @NotBlank(message = "{email.obligatorio}") @Email(message = "{email.invalido}") String email,
        @NotBlank(message = "{phone.obligatorio}") @Pattern(regexp = "\\d{4,6}") String telefono,
        @NotBlank(message = "El documento es necesario") String documento,
        @NotNull(message = "{address.obligatorio}") @Valid DatosDireccion direccion
        ) {

}

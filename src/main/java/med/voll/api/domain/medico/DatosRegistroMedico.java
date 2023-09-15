package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroMedico(
        @NotBlank(message = "el nombre es obligatorio") String nombre,
        @NotBlank @Email(message = "formato de email incorrecto") String email,
        @NotBlank @Pattern(regexp = "\\d{4,6}") String documento,
        @NotBlank(message = "{phone.obligatorio}") @Pattern(regexp = "\\d{4,10}") String telefono,
        @NotNull Especialidad especialidad,
        @NotNull @Valid DatosDireccion direccion) {
}

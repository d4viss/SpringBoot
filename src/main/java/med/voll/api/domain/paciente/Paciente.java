package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.direccion.Direccion;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre, email, telefono, documento;
    @Embedded
    private Direccion direccion;
    private boolean activo;


    public Paciente(DatosRegistroPaciente datosRegistroPaciente) {
        this.nombre = datosRegistroPaciente.nombre();
        this.email = datosRegistroPaciente.email();
        this.telefono = datosRegistroPaciente.telefono();
        this.documento = datosRegistroPaciente.documento();
        this.direccion = new Direccion(datosRegistroPaciente.direccion());
    }

    public void actualizar(DatosActualizarPaciente datosActualizarPaciente){
        if(datosActualizarPaciente.nombre() != null)
            this.nombre = datosActualizarPaciente.nombre();
        if(datosActualizarPaciente.telefono() != null)
            this.telefono = datosActualizarPaciente.telefono();
        if(datosActualizarPaciente.direccion() != null)
            this.direccion.actualizarDatos(datosActualizarPaciente.direccion());
    }

    public void desactivarPaciente() {
        this.activo = false;
    }
}

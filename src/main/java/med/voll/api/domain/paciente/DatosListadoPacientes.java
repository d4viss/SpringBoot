package med.voll.api.domain.paciente;

import med.voll.api.domain.medico.Medico;

public record DatosListadoPacientes (
        Long id,
        String nombre,
        String documento,
        String email
){

    public DatosListadoPacientes(Paciente paciente){
        this(paciente.getId(),
                paciente.getNombre(),
                paciente.getDocumento(),
                paciente.getEmail());
    }
}

package med.voll.api.domain.consulta.validations;

import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionIntegridad;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorConsultas{

    private final PacienteRepository pacienteRepository;

    public PacienteActivo(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public void validar(@NotNull DatosAgendarConsulta datosAgendarConsulta){
        if(datosAgendarConsulta.idPaciente() == null)
            return;
        boolean pacienteActivo = pacienteRepository.findActivoById(datosAgendarConsulta.idPaciente());
        if(!pacienteActivo)
            throw new ValidacionIntegridad("No se puede permitir agendar con pacientes inactivos");
    }
}

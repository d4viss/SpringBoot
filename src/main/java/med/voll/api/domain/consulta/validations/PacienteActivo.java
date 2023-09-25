package med.voll.api.domain.consulta.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
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
        boolean pacienteActivo = pacienteRepository.findActivoById(datosAgendarConsulta.id());
        if(!pacienteActivo)
            throw new ValidationException("No se puede permitir agendar con pacientes inactivos");
    }
}

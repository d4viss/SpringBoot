package med.voll.api.domain.consulta.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorConsultas{

    private final MedicoRepository medicoRepository;

    public MedicoActivo(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public void validar(@NotNull DatosAgendarConsulta datosAgendarConsulta){
        if(datosAgendarConsulta.idMedico() == null)
            return;
        boolean medicoActivo = medicoRepository.findActivoById(datosAgendarConsulta.id());
        if(!medicoActivo)
            throw new ValidationException("No se puede permitir agendar con medicos inactivos");
    }
}

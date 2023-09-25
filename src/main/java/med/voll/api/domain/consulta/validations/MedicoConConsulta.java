package med.voll.api.domain.consulta.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRespository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorConsultas{

    private final ConsultaRespository consultaRespository;

    public MedicoConConsulta(ConsultaRespository consultaRespository) {
        this.consultaRespository = consultaRespository;
    }

    public void validar(@NotNull DatosAgendarConsulta datosAgendarConsulta){
        if(datosAgendarConsulta.idMedico() == null)
            return;

        boolean medicoConConsulta = consultaRespository.existsByMedicoIdAndFecha(datosAgendarConsulta.idMedico(), datosAgendarConsulta.fecha());

        if(medicoConConsulta)
            throw new ValidationException("Este medico ya tiene una consulta en ese horario");
    }
}

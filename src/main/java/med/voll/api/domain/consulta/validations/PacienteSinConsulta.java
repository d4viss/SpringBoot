package med.voll.api.domain.consulta.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRespository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PacienteSinConsulta implements ValidadorConsultas{

    private final ConsultaRespository consultaRespository;

    public PacienteSinConsulta(ConsultaRespository consultaRespository) {
        this.consultaRespository = consultaRespository;
    }

    public void validar(@NotNull DatosAgendarConsulta datosAgendarConsulta){
        LocalDateTime primerHorario = datosAgendarConsulta.fecha().withHour(7);
        LocalDateTime ultimoHorario = datosAgendarConsulta.fecha().withHour(18);

        boolean pacienteConConsulta = consultaRespository.existsByPacienteIdAndFechaBetween(datosAgendarConsulta.idPaciente(), primerHorario, ultimoHorario);

        if(pacienteConConsulta){
            throw new ValidationException("No se puede");
        }
    }
}

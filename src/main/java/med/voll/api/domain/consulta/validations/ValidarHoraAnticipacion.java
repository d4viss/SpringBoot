package med.voll.api.domain.consulta.validations;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRespository;
import med.voll.api.domain.consulta.DatosCancelarConsulta;
import med.voll.api.infra.errores.ValidacionIntegridad;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidarHoraAnticipacion implements ValidadorCancelarConsulta{

    private final ConsultaRespository consultaRespository;

    public ValidarHoraAnticipacion(ConsultaRespository consultaRespository) {
        this.consultaRespository = consultaRespository;
    }

    @Override
    public void validar(DatosCancelarConsulta datosCancelarConsulta) {
        Consulta consulta = consultaRespository.getReferenceById(datosCancelarConsulta.idConsulta());
        LocalDateTime ahora = LocalDateTime.now();
        long diferenciaHoras = Duration.between(ahora, consulta.getFecha()).toHours();

        if(diferenciaHoras < 24 )
            throw new ValidacionIntegridad("Una consulta solo puede ser cancelada con una antecedencia de 24 horas");
    }
}

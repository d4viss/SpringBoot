package med.voll.api.domain.consulta.validations;

import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionIntegridad;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioAnticipacion implements ValidadorConsultas {

    public void validar(@NotNull DatosAgendarConsulta datosAgendarConsulta){
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime horaConsulta = datosAgendarConsulta.fecha();

        boolean diferencia30Min = Duration.between(ahora, horaConsulta).toMinutes()<30;

        if(diferencia30Min)
            throw new ValidacionIntegridad("Las citas deben agendarse con 30 min de anticipacion");
    }
}

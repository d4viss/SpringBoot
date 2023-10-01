package med.voll.api.domain.consulta.validations;

import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionIntegridad;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioFuncionamientoClinica implements ValidadorConsultas{

    public void validar(@NotNull DatosAgendarConsulta datosAgendarConsulta){
        boolean isDomingo = DayOfWeek.SUNDAY.equals(datosAgendarConsulta.fecha().getDayOfWeek());

        boolean antesApertura = datosAgendarConsulta.fecha().getHour() < 7;
        boolean despuesCierre = datosAgendarConsulta.fecha().getHour() > 19;
        if(isDomingo || antesApertura || despuesCierre)
            throw new ValidacionIntegridad("El horario de antencion de la clinica es de lunes a sabado");
    }

}

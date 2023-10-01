package med.voll.api.domain.consulta.validations;

import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosCancelarConsulta;

public interface ValidadorCancelarConsulta {

    public void validar(DatosCancelarConsulta datosCancelarConsulta);

}

package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DatodDetalleConsulta (
        Long id,
        Long idPaciente,
        Long idMedico,
        LocalDateTime fecha
        ){

        public DatodDetalleConsulta(Consulta consulta){
                this(consulta.getId(),
                        consulta.getPaciente().getId(),
                        consulta.getMedico().getId(),
                        consulta.getFecha());
        }
}

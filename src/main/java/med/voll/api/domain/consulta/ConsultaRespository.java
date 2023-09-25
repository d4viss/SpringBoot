package med.voll.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRespository extends JpaRepository<Consulta, Long> {
    boolean existsByPacienteIdAndFechaBetween(Long aLong, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    boolean existsByMedicoIdAndFecha(Long aLong, LocalDateTime fecha);
}

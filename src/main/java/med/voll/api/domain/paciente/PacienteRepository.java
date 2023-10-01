package med.voll.api.domain.paciente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("""
            select p.activo from Paciente p
            where p.id = :id
            """)
    boolean findActivoById(@Param("id") Long id);
}

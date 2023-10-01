package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("deberia retornar null cuando el medico se encuentre en consulta con otro paciente en ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEscenario1() {
        LocalDateTime proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        Medico medico = registrarMedico("jose", "jose@gmail.com", "832475", "98243", Especialidad.CARDIOLOGIA);
        Paciente paciente = registrarPaciente("juan", "juan@gmail.com", "9283744", "92423");

        registrarConsulta(medico, paciente, proximoLunes10H);

        Medico medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);

        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("deberia retornar un medico cuando realice la consulta en la base de datos para ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEscenario2() {
        LocalDateTime proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        Medico medico = registrarMedico("jose", "jose@gmail.com", "832475", "98243", Especialidad.CARDIOLOGIA);

        Medico medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);

        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha){
        entityManager.persist(new Consulta(null, medico, paciente, fecha, null));
    }

    private Medico registrarMedico(String nombre, String email, String telefono, String documento, Especialidad especialidad){
        Medico medico = new Medico(datosRegistroMedico(nombre, email, telefono, documento, especialidad));
        entityManager.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String telefono, String documento){
        Paciente paciente = new Paciente(datosRegistroPaciente(nombre, email, telefono, documento));
        entityManager.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosRegistroMedico(String nombre, String email, String telefono, String documento, Especialidad especialidad){
        return new DatosRegistroMedico(
                nombre,
                email,
                documento,
                telefono,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosRegistroPaciente(String nombre, String email, String telefono, String documento){
        return new DatosRegistroPaciente(
                nombre,
                email,
                telefono,
                documento,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion(){
        return new DatosDireccion(
                "Maldonado",
                "San Juan",
                "Tunja",
                "16-4",
                "36a-46"
        );
    }

    @Test
    void findActivoById() {
    }
}
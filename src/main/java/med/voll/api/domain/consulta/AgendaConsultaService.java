package med.voll.api.domain.consulta;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.validations.ValidadorCancelarConsulta;
import med.voll.api.domain.consulta.validations.ValidadorConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionIntegridad;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class AgendaConsultaService {

    private ConsultaRespository consultaRespository;
    private MedicoRepository medicoRepository;
    private PacienteRepository pacienteRepository;
    private List<ValidadorConsultas> validadorConsultas;
    private List<ValidadorCancelarConsulta> validadorCancelarConsultas;

    public AgendaConsultaService(ConsultaRespository consultaRespository,
                                 MedicoRepository medicoRepository,
                                 PacienteRepository pacienteRepository, List<ValidadorConsultas> validadorConsultas, List<ValidadorCancelarConsulta> validadorCancelarConsultas) {
        this.consultaRespository = consultaRespository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadorConsultas = validadorConsultas;
        this.validadorCancelarConsultas = validadorCancelarConsultas;
    }

    public DatodDetalleConsulta agendar(@NotNull DatosAgendarConsulta datosAgendarConsulta) {
        if (!pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent())
            throw new ValidacionIntegridad("Este id para el paciente no fue encontrado");

        if (datosAgendarConsulta.idMedico() != null && !medicoRepository.existsById(datosAgendarConsulta.idMedico())) {
            throw new ValidacionIntegridad("Este id para el medico no fue encontrado");

        }

        validadorConsultas.forEach(v -> v.validar(datosAgendarConsulta));

        Medico medico = seleccionarMedico(datosAgendarConsulta);

        if (medico == null)
            throw new ValidacionIntegridad("No existen medicos disponibles");

        Paciente paciente = pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();

        Consulta consulta = new Consulta(medico, paciente, datosAgendarConsulta.fecha());
        consultaRespository.save(consulta);

        return new DatodDetalleConsulta(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        if (datosAgendarConsulta.idMedico() != null) {
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if (datosAgendarConsulta.especialidad() == null) {
            throw new ValidacionIntegridad("Debe seleccionarse una especialidad para el medico");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datosAgendarConsulta.especialidad(), datosAgendarConsulta.fecha());
    }

    public void cancelar(DatosCancelarConsulta datosCancelarConsulta) {
        if (!consultaRespository.existsById(datosCancelarConsulta.idConsulta()))
            throw new ValidacionIntegridad("La consulta no existe");

        validadorCancelarConsultas.forEach(v -> v.validar(datosCancelarConsulta));

        Consulta consulta = consultaRespository.getReferenceById(datosCancelarConsulta.idConsulta());
        consulta.cancelar(datosCancelarConsulta.motivo());
    }
}

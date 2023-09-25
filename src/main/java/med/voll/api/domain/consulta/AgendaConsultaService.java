package med.voll.api.domain.consulta;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionIntegridad;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AgendaConsultaService {

    private ConsultaRespository consultaRespository;
    private MedicoRepository medicoRepository;
    private PacienteRepository pacienteRepository;

    public AgendaConsultaService(ConsultaRespository consultaRespository,
                                 MedicoRepository medicoRepository,
                                 PacienteRepository pacienteRepository){
        this.consultaRespository = consultaRespository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public void agendar(@NotNull DatosAgendarConsulta datosAgendarConsulta){
        if(pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent())
            throw new ValidacionIntegridad("Este id para el paciente no fue encontrado");

        if(datosAgendarConsulta.idMedico() != null && medicoRepository.existsById(datosAgendarConsulta.idMedico())){
            throw new ValidacionIntegridad("Este id para el medico no fue encontrado");

        }
        Medico medico = seleccionarMedico(datosAgendarConsulta);

        Paciente paciente = pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();

        Consulta consulta = new Consulta(null, medico, paciente, datosAgendarConsulta.fecha());

        consultaRespository.save(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        if(datosAgendarConsulta.idMedico() != null){
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if(datosAgendarConsulta.especialidad() == null){
            throw new ValidacionIntegridad("Debe seleccionarse una especialidad para el medico");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datosAgendarConsulta.especialidad(), datosAgendarConsulta.fecha());
    }
}

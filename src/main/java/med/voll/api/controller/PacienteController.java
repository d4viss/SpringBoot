package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaPaciente> registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente,
                                                                    UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente);
        URI uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(datosRespuestaPaciente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaPaciente> retornoPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente);
        return ResponseEntity.ok(datosRespuestaPaciente);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoPacientes>> listadoPacientes(@PageableDefault(size = 10, page = 0, sort = {"nombre"}) Pageable pageable){
        return ResponseEntity.ok(pacienteRepository.findAll(pageable).map(DatosListadoPacientes::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente){
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizar(datosActualizarPaciente);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desactivarMedico(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }
}

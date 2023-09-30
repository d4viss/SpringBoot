package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaConsultaService;
import med.voll.api.domain.consulta.DatodDetalleConsulta;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosCancelarConsulta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    private AgendaConsultaService agendaConsultaService;

    public ConsultaController(AgendaConsultaService agendaConsultaService){
        this.agendaConsultaService = agendaConsultaService;
    }

    @PostMapping
    public ResponseEntity<DatodDetalleConsulta> agendar(@RequestBody @Valid DatosAgendarConsulta datosAgentarConsulta){
        DatodDetalleConsulta response = agendaConsultaService.agendar(datosAgentarConsulta);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DatosCancelarConsulta datosCancelarConsulta){
        agendaConsultaService.cancelar(datosCancelarConsulta);
        return ResponseEntity.noContent().build();
    }

}

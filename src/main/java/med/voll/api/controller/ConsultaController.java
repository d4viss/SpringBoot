package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaConsultaService;
import med.voll.api.domain.consulta.DatodDetalleConsulta;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private AgendaConsultaService agendaConsultaService;

    public ConsultaController(AgendaConsultaService agendaConsultaService){
        this.agendaConsultaService = agendaConsultaService;
    }

    @PostMapping
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datosAgentarConsulta){
        var response = agendaConsultaService.agendar(datosAgentarConsulta);
        return ResponseEntity.ok(response);
    }
}

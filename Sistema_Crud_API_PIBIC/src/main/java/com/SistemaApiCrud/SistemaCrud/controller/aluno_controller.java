package com.SistemaApiCrud.SistemaCrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SistemaApiCrud.SistemaCrud.DTO.aluno_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.casos_clinicos_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.desempenho_aluno_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.historico_aluno_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.responder_caso_request_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.resultado_caso_DTO;
import com.SistemaApiCrud.SistemaCrud.service.caso_clinico_service;
import com.SistemaApiCrud.SistemaCrud.service.aluno_service;
import com.SistemaApiCrud.SistemaCrud.service.resposta_aluno_service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/alunos")
public class aluno_controller {

    @Autowired
    private aluno_service service;

    @Autowired
    private caso_clinico_service casoService;

    @Autowired
    private resposta_aluno_service respostaService;

    @GetMapping
    public List<aluno_DTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<aluno_DTO> buscarPorId(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/{id}/casos-disponiveis")
    public List<casos_clinicos_DTO> listarCasosDisponiveis(@PathVariable @Min(1) Long id) {
        service.buscarPorId(id);
        return casoService.listarPublicados();
    }

    @GetMapping("/{id}/historico")
    public ResponseEntity<historico_aluno_DTO> buscarHistorico(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(respostaService.buscarHistorico(id));
    }

    @GetMapping("/{id}/desempenho")
    public ResponseEntity<desempenho_aluno_DTO> buscarDesempenho(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(respostaService.buscarDesempenho(id));
    }

    @PostMapping
    public ResponseEntity<aluno_DTO> salvar(@RequestBody @Valid aluno_DTO aluno) {
        aluno_DTO alunoSalvo = service.salvar(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoSalvo);
    }

    @PostMapping("/{id}/casos/{casoId}/responder")
    public ResponseEntity<resultado_caso_DTO> responderCaso(@PathVariable @Min(1) Long id,
                                                            @PathVariable @Min(1) Long casoId,
                                                            @RequestBody @Valid responder_caso_request_DTO request) {
        resultado_caso_DTO resultado = respostaService.responderCaso(id, casoId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<aluno_DTO> atualizar(@PathVariable @Min(1) Long id,
                                               @RequestBody @Valid aluno_DTO aluno) {
        return ResponseEntity.ok(service.atualizar(id, aluno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @Min(1) Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

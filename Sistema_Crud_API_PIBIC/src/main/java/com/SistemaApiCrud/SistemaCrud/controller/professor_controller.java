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

import com.SistemaApiCrud.SistemaCrud.DTO.Professor_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.casos_clinicos_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.relatorio_desempenho_professor_DTO;
import com.SistemaApiCrud.SistemaCrud.service.caso_clinico_service;
import com.SistemaApiCrud.SistemaCrud.service.professor_service;
import com.SistemaApiCrud.SistemaCrud.service.resposta_aluno_service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/professores")
public class professor_controller {

    @Autowired
    private professor_service service;

    @Autowired
    private caso_clinico_service casoService;

    @Autowired
    private resposta_aluno_service respostaService;

    @GetMapping
    public List<Professor_DTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor_DTO> buscarPorId(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/{id}/casos")
    public List<casos_clinicos_DTO> listarCasos(@PathVariable @Min(1) Long id) {
        return casoService.listarPorProfessor(id);
    }

    @GetMapping("/{id}/relatorio-desempenho")
    public ResponseEntity<relatorio_desempenho_professor_DTO> gerarRelatorioDesempenho(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(respostaService.gerarRelatorioProfessor(id));
    }

    @PostMapping
    public ResponseEntity<Professor_DTO> salvar(@RequestBody @Valid Professor_DTO professor) {
        Professor_DTO professorSalvo = service.salvar(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(professorSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor_DTO> atualizar(@PathVariable @Min(1) Long id,
                                                   @RequestBody @Valid Professor_DTO professor) {
        return ResponseEntity.ok(service.atualizar(id, professor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @Min(1) Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

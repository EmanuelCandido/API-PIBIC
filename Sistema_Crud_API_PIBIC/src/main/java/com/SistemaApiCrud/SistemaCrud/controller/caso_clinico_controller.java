package com.SistemaApiCrud.SistemaCrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SistemaApiCrud.SistemaCrud.DTO.caso_clinico_completo_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.casos_clinicos_DTO;
import com.SistemaApiCrud.SistemaCrud.DTO.pergunta_DTO;
import com.SistemaApiCrud.SistemaCrud.entity.enums.StatusCasoClinico;
import com.SistemaApiCrud.SistemaCrud.service.caso_clinico_service;
import com.SistemaApiCrud.SistemaCrud.service.pergunta_service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/casos")
public class caso_clinico_controller {

    @Autowired
    private caso_clinico_service service;

    @Autowired
    private pergunta_service perguntaService;

    @GetMapping
    public Page<casos_clinicos_DTO> listar(
            @RequestParam(required = false) StatusCasoClinico status,
            @RequestParam(required = false) @Min(1) Long idProfessor,
            @RequestParam(required = false) String termo,
            @PageableDefault(size = 20, sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.listarPaginado(status, idProfessor, termo, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<casos_clinicos_DTO> buscarPorId(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/{id}/completo")
    public ResponseEntity<caso_clinico_completo_DTO> buscarCompletoPorId(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(service.buscarCompletoPorId(id));
    }

    @GetMapping("/{casoId}/perguntas")
    public List<pergunta_DTO> listarPerguntas(@PathVariable @Min(1) Long casoId) {
        return perguntaService.listarPorCaso(casoId);
    }

    @PostMapping
    public ResponseEntity<casos_clinicos_DTO> salvar(@RequestBody @Valid casos_clinicos_DTO caso) {
        casos_clinicos_DTO casoSalvo = service.salvar(caso);
        return ResponseEntity.status(HttpStatus.CREATED).body(casoSalvo);
    }

    @PostMapping("/{casoId}/perguntas")
    public ResponseEntity<pergunta_DTO> salvarPergunta(@PathVariable @Min(1) Long casoId,
                                                       @RequestBody @Valid pergunta_DTO pergunta) {
        pergunta_DTO perguntaSalva = perguntaService.salvarEmCaso(casoId, pergunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(perguntaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<casos_clinicos_DTO> atualizar(@PathVariable @Min(1) Long id,
                                                        @RequestBody @Valid casos_clinicos_DTO caso) {
        return ResponseEntity.ok(service.atualizar(id, caso));
    }

    @PatchMapping("/{id}/publicar")
    public ResponseEntity<casos_clinicos_DTO> publicar(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(service.publicar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @Min(1) Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

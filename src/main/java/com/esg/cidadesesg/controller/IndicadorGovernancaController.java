package com.esg.cidadesesg.controller;

import com.esg.cidadesesg.dto.IndicadorGovernancaDTO;
import com.esg.cidadesesg.service.IndicadorGovernancaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cidades/{cidadeId}/governanca")
@RequiredArgsConstructor
@Tag(name = "Indicadores de Governança (G)", description = "Pilar Governança do ESG - transparência e conformidade")
public class IndicadorGovernancaController {

    private final IndicadorGovernancaService service;

    @PostMapping
    @Operation(summary = "Registrar indicador de governança para uma cidade")
    public ResponseEntity<IndicadorGovernancaDTO.Response> criar(
            @PathVariable Long cidadeId,
            @Valid @RequestBody IndicadorGovernancaDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(cidadeId, request));
    }

    @GetMapping
    @Operation(summary = "Listar indicadores de governança de uma cidade")
    public ResponseEntity<List<IndicadorGovernancaDTO.Response>> listar(@PathVariable Long cidadeId) {
        return ResponseEntity.ok(service.listarPorCidade(cidadeId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar indicador de governança por ID")
    public ResponseEntity<IndicadorGovernancaDTO.Response> buscarPorId(
            @PathVariable Long cidadeId,
            @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar indicador de governança")
    public ResponseEntity<IndicadorGovernancaDTO.Response> atualizar(
            @PathVariable Long cidadeId,
            @PathVariable Long id,
            @Valid @RequestBody IndicadorGovernancaDTO.Request request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar indicador de governança")
    public ResponseEntity<Void> deletar(
            @PathVariable Long cidadeId,
            @PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

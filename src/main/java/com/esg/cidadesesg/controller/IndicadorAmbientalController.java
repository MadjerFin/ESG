package com.esg.cidadesesg.controller;

import com.esg.cidadesesg.dto.IndicadorAmbientalDTO;
import com.esg.cidadesesg.service.IndicadorAmbientalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cidades/{cidadeId}/ambiental")
@RequiredArgsConstructor
@Tag(name = "Indicadores Ambientais (E)", description = "Pilar Ambiental do ESG - emissões, resíduos e energia")
public class IndicadorAmbientalController {

    private final IndicadorAmbientalService service;

    @PostMapping
    @Operation(summary = "Registrar indicador ambiental para uma cidade")
    public ResponseEntity<IndicadorAmbientalDTO.Response> criar(
            @PathVariable Long cidadeId,
            @Valid @RequestBody IndicadorAmbientalDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(cidadeId, request));
    }

    @GetMapping
    @Operation(summary = "Listar indicadores ambientais de uma cidade")
    public ResponseEntity<List<IndicadorAmbientalDTO.Response>> listar(@PathVariable Long cidadeId) {
        return ResponseEntity.ok(service.listarPorCidade(cidadeId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar indicador ambiental por ID")
    public ResponseEntity<IndicadorAmbientalDTO.Response> buscarPorId(
            @PathVariable Long cidadeId,
            @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar indicador ambiental")
    public ResponseEntity<IndicadorAmbientalDTO.Response> atualizar(
            @PathVariable Long cidadeId,
            @PathVariable Long id,
            @Valid @RequestBody IndicadorAmbientalDTO.Request request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar indicador ambiental")
    public ResponseEntity<Void> deletar(
            @PathVariable Long cidadeId,
            @PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

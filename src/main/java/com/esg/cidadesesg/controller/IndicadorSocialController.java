package com.esg.cidadesesg.controller;

import com.esg.cidadesesg.dto.IndicadorSocialDTO;
import com.esg.cidadesesg.service.IndicadorSocialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cidades/{cidadeId}/social")
@RequiredArgsConstructor
@Tag(name = "Indicadores Sociais (S)", description = "Pilar Social do ESG - emprego, saúde e diversidade")
public class IndicadorSocialController {

    private final IndicadorSocialService service;

    @PostMapping
    @Operation(summary = "Registrar indicador social para uma cidade")
    public ResponseEntity<IndicadorSocialDTO.Response> criar(
            @PathVariable Long cidadeId,
            @Valid @RequestBody IndicadorSocialDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(cidadeId, request));
    }

    @GetMapping
    @Operation(summary = "Listar indicadores sociais de uma cidade")
    public ResponseEntity<List<IndicadorSocialDTO.Response>> listar(@PathVariable Long cidadeId) {
        return ResponseEntity.ok(service.listarPorCidade(cidadeId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar indicador social por ID")
    public ResponseEntity<IndicadorSocialDTO.Response> buscarPorId(
            @PathVariable Long cidadeId,
            @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar indicador social")
    public ResponseEntity<IndicadorSocialDTO.Response> atualizar(
            @PathVariable Long cidadeId,
            @PathVariable Long id,
            @Valid @RequestBody IndicadorSocialDTO.Request request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar indicador social")
    public ResponseEntity<Void> deletar(
            @PathVariable Long cidadeId,
            @PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

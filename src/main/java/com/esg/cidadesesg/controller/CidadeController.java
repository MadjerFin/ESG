package com.esg.cidadesesg.controller;

import com.esg.cidadesesg.dto.CidadeDTO;
import com.esg.cidadesesg.service.CidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cidades")
@RequiredArgsConstructor
@Tag(name = "Cidades", description = "Gerenciamento de cidades ESG")
public class CidadeController {

    private final CidadeService cidadeService;

    @PostMapping
    @Operation(summary = "Cadastrar nova cidade")
    public ResponseEntity<CidadeDTO.Response> criar(@Valid @RequestBody CidadeDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cidadeService.criar(request));
    }

    @GetMapping
    @Operation(summary = "Listar todas as cidades")
    public ResponseEntity<List<CidadeDTO.Response>> listarTodas() {
        return ResponseEntity.ok(cidadeService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cidade por ID")
    public ResponseEntity<CidadeDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cidadeService.buscarPorId(id));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar cidades por estado")
    public ResponseEntity<List<CidadeDTO.Response>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(cidadeService.buscarPorEstado(estado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cidade")
    public ResponseEntity<CidadeDTO.Response> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CidadeDTO.Request request) {
        return ResponseEntity.ok(cidadeService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cidade")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

package com.esg.cidadesesg.service;

import com.esg.cidadesesg.dto.IndicadorGovernancaDTO;
import com.esg.cidadesesg.model.Cidade;
import com.esg.cidadesesg.model.IndicadorGovernanca;
import com.esg.cidadesesg.repository.IndicadorGovernancaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndicadorGovernancaService {

    private final IndicadorGovernancaRepository repository;
    private final CidadeService cidadeService;

    @Transactional
    public IndicadorGovernancaDTO.Response criar(Long cidadeId, IndicadorGovernancaDTO.Request request) {
        Cidade cidade = cidadeService.findById(cidadeId);
        IndicadorGovernanca indicador = IndicadorGovernanca.builder()
                .cidade(cidade)
                .descricao(request.getDescricao())
                .indiceTransparencia(request.getIndiceTransparencia())
                .conformidadeLegalPct(request.getConformidadeLegalPct())
                .indiceAntiCorrupcao(request.getIndiceAntiCorrupcao())
                .participacaoCidadaPct(request.getParticipacaoCidadaPct())
                .dataReferencia(request.getDataReferencia())
                .build();
        return toResponse(repository.save(indicador));
    }

    @Transactional(readOnly = true)
    public List<IndicadorGovernancaDTO.Response> listarPorCidade(Long cidadeId) {
        cidadeService.findById(cidadeId);
        return repository.findByCidadeId(cidadeId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IndicadorGovernancaDTO.Response buscarPorId(Long id) {
        return toResponse(findById(id));
    }

    @Transactional
    public IndicadorGovernancaDTO.Response atualizar(Long id, IndicadorGovernancaDTO.Request request) {
        IndicadorGovernanca indicador = findById(id);
        indicador.setDescricao(request.getDescricao());
        indicador.setIndiceTransparencia(request.getIndiceTransparencia());
        indicador.setConformidadeLegalPct(request.getConformidadeLegalPct());
        indicador.setIndiceAntiCorrupcao(request.getIndiceAntiCorrupcao());
        indicador.setParticipacaoCidadaPct(request.getParticipacaoCidadaPct());
        indicador.setDataReferencia(request.getDataReferencia());
        return toResponse(repository.save(indicador));
    }

    @Transactional
    public void deletar(Long id) {
        repository.delete(findById(id));
    }

    private IndicadorGovernanca findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Indicador de governança não encontrado com id: " + id));
    }

    private IndicadorGovernancaDTO.Response toResponse(IndicadorGovernanca i) {
        double score = calcularScore(i);
        return IndicadorGovernancaDTO.Response.builder()
                .id(i.getId())
                .cidadeId(i.getCidade().getId())
                .cidadeNome(i.getCidade().getNome())
                .descricao(i.getDescricao())
                .indiceTransparencia(i.getIndiceTransparencia())
                .conformidadeLegalPct(i.getConformidadeLegalPct())
                .indiceAntiCorrupcao(i.getIndiceAntiCorrupcao())
                .participacaoCidadaPct(i.getParticipacaoCidadaPct())
                .dataReferencia(i.getDataReferencia())
                .criadoEm(i.getCriadoEm())
                .scoreGovernanca(score)
                .build();
    }

    private double calcularScore(IndicadorGovernanca i) {
        double scoreTransparencia = i.getIndiceTransparencia();
        double scoreConformidade = i.getConformidadeLegalPct() / 10;
        double scoreAntiCorrupcao = i.getIndiceAntiCorrupcao();
        return Math.round((scoreTransparencia + scoreConformidade + scoreAntiCorrupcao) / 3 * 100.0) / 100.0;
    }
}

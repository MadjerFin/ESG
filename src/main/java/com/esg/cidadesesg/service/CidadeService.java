package com.esg.cidadesesg.service;

import com.esg.cidadesesg.dto.CidadeDTO;
import com.esg.cidadesesg.model.Cidade;
import com.esg.cidadesesg.model.IndicadorAmbiental;
import com.esg.cidadesesg.model.IndicadorGovernanca;
import com.esg.cidadesesg.model.IndicadorSocial;
import com.esg.cidadesesg.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    @Transactional
    public CidadeDTO.Response criar(CidadeDTO.Request request) {
        if (cidadeRepository.existsByNomeIgnoreCaseAndEstadoIgnoreCase(request.getNome(), request.getEstado())) {
            throw new IllegalArgumentException("Cidade já cadastrada: " + request.getNome() + "/" + request.getEstado());
        }
        Cidade cidade = Cidade.builder()
                .nome(request.getNome())
                .estado(request.getEstado().toUpperCase())
                .build();
        return toResponse(cidadeRepository.save(cidade));
    }

    @Transactional(readOnly = true)
    public List<CidadeDTO.Response> listarTodas() {
        return cidadeRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CidadeDTO.Response buscarPorId(Long id) {
        return toResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public List<CidadeDTO.Response> buscarPorEstado(String estado) {
        return cidadeRepository.findByEstadoIgnoreCase(estado).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CidadeDTO.Response atualizar(Long id, CidadeDTO.Request request) {
        Cidade cidade = findById(id);
        cidade.setNome(request.getNome());
        cidade.setEstado(request.getEstado().toUpperCase());
        return toResponse(cidadeRepository.save(cidade));
    }

    @Transactional
    public void deletar(Long id) {
        Cidade cidade = findById(id);
        cidadeRepository.delete(cidade);
    }

    public Cidade findById(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cidade não encontrada com id: " + id));
    }

    private CidadeDTO.Response toResponse(Cidade cidade) {
        double scoreESG = calcularScoreESG(cidade);
        return CidadeDTO.Response.builder()
                .id(cidade.getId())
                .nome(cidade.getNome())
                .estado(cidade.getEstado())
                .criadoEm(cidade.getCriadoEm())
                .atualizadoEm(cidade.getAtualizadoEm())
                .scoreESG(scoreESG)
                .build();
    }

    private double calcularScoreESG(Cidade cidade) {
        double scoreAmbiental = calcularScoreAmbiental(cidade.getIndicadoresAmbientais());
        double scoreSocial = calcularScoreSocial(cidade.getIndicadoresSociais());
        double scoreGovernanca = calcularScoreGovernanca(cidade.getIndicadoresGovernanca());

        long count = (scoreAmbiental > 0 ? 1 : 0) + (scoreSocial > 0 ? 1 : 0) + (scoreGovernanca > 0 ? 1 : 0);
        if (count == 0) return 0.0;

        return Math.round(((scoreAmbiental + scoreSocial + scoreGovernanca) / count) * 100.0) / 100.0;
    }

    private double calcularScoreAmbiental(List<IndicadorAmbiental> indicadores) {
        if (indicadores == null || indicadores.isEmpty()) return 0.0;
        IndicadorAmbiental ultimo = indicadores.get(indicadores.size() - 1);
        double scoreEnergia = ultimo.getPercentualEnergiaRenovavel();
        double scoreArea = Math.min(ultimo.getAreaVerdeMq() / 1000.0 * 10, 10.0);
        double scoreCo2 = Math.max(10.0 - (ultimo.getEmissaoCo2Toneladas() / 10000.0), 0.0);
        return Math.round(((scoreEnergia / 10) + scoreArea + scoreCo2) / 3 * 10.0) / 10.0;
    }

    private double calcularScoreSocial(List<IndicadorSocial> indicadores) {
        if (indicadores == null || indicadores.isEmpty()) return 0.0;
        IndicadorSocial ultimo = indicadores.get(indicadores.size() - 1);
        double scoreIdh = ultimo.getIdh() * 10;
        double scoreEmprego = 10.0 - ultimo.getTaxaDesempregoPct() / 10;
        double scoreSaude = ultimo.getAcessoSaudeBasicaPct() / 10;
        return Math.round((scoreIdh + scoreEmprego + scoreSaude) / 3 * 10.0) / 10.0;
    }

    private double calcularScoreGovernanca(List<IndicadorGovernanca> indicadores) {
        if (indicadores == null || indicadores.isEmpty()) return 0.0;
        IndicadorGovernanca ultimo = indicadores.get(indicadores.size() - 1);
        double scoreTransparencia = ultimo.getIndiceTransparencia();
        double scoreConformidade = ultimo.getConformidadeLegalPct() / 10;
        double scoreAntiCorrupcao = ultimo.getIndiceAntiCorrupcao();
        return Math.round((scoreTransparencia + scoreConformidade + scoreAntiCorrupcao) / 3 * 10.0) / 10.0;
    }
}

package com.esg.cidadesesg.service;

import com.esg.cidadesesg.dto.IndicadorAmbientalDTO;
import com.esg.cidadesesg.model.Cidade;
import com.esg.cidadesesg.model.IndicadorAmbiental;
import com.esg.cidadesesg.repository.IndicadorAmbientalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndicadorAmbientalService {

    private final IndicadorAmbientalRepository repository;
    private final CidadeService cidadeService;

    @Transactional
    public IndicadorAmbientalDTO.Response criar(Long cidadeId, IndicadorAmbientalDTO.Request request) {
        Cidade cidade = cidadeService.findById(cidadeId);
        IndicadorAmbiental indicador = IndicadorAmbiental.builder()
                .cidade(cidade)
                .descricao(request.getDescricao())
                .emissaoCo2Toneladas(request.getEmissaoCo2Toneladas())
                .residuosGeradosToneladas(request.getResiduosGeradosToneladas())
                .percentualEnergiaRenovavel(request.getPercentualEnergiaRenovavel())
                .areaVerdeMq(request.getAreaVerdeMq())
                .dataReferencia(request.getDataReferencia())
                .build();
        return toResponse(repository.save(indicador));
    }

    @Transactional(readOnly = true)
    public List<IndicadorAmbientalDTO.Response> listarPorCidade(Long cidadeId) {
        cidadeService.findById(cidadeId);
        return repository.findByCidadeId(cidadeId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IndicadorAmbientalDTO.Response buscarPorId(Long id) {
        return toResponse(findById(id));
    }

    @Transactional
    public IndicadorAmbientalDTO.Response atualizar(Long id, IndicadorAmbientalDTO.Request request) {
        IndicadorAmbiental indicador = findById(id);
        indicador.setDescricao(request.getDescricao());
        indicador.setEmissaoCo2Toneladas(request.getEmissaoCo2Toneladas());
        indicador.setResiduosGeradosToneladas(request.getResiduosGeradosToneladas());
        indicador.setPercentualEnergiaRenovavel(request.getPercentualEnergiaRenovavel());
        indicador.setAreaVerdeMq(request.getAreaVerdeMq());
        indicador.setDataReferencia(request.getDataReferencia());
        return toResponse(repository.save(indicador));
    }

    @Transactional
    public void deletar(Long id) {
        repository.delete(findById(id));
    }

    private IndicadorAmbiental findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Indicador ambiental não encontrado com id: " + id));
    }

    private IndicadorAmbientalDTO.Response toResponse(IndicadorAmbiental i) {
        double score = calcularScore(i);
        return IndicadorAmbientalDTO.Response.builder()
                .id(i.getId())
                .cidadeId(i.getCidade().getId())
                .cidadeNome(i.getCidade().getNome())
                .descricao(i.getDescricao())
                .emissaoCo2Toneladas(i.getEmissaoCo2Toneladas())
                .residuosGeradosToneladas(i.getResiduosGeradosToneladas())
                .percentualEnergiaRenovavel(i.getPercentualEnergiaRenovavel())
                .areaVerdeMq(i.getAreaVerdeMq())
                .dataReferencia(i.getDataReferencia())
                .criadoEm(i.getCriadoEm())
                .scoreAmbiental(score)
                .build();
    }

    private double calcularScore(IndicadorAmbiental i) {
        double scoreEnergia = i.getPercentualEnergiaRenovavel() / 10;
        double scoreArea = Math.min(i.getAreaVerdeMq() / 1000.0, 10.0);
        double scoreCo2 = Math.max(10.0 - (i.getEmissaoCo2Toneladas() / 10000.0), 0.0);
        return Math.round((scoreEnergia + scoreArea + scoreCo2) / 3 * 100.0) / 100.0;
    }
}

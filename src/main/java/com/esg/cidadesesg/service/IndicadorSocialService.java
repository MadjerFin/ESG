package com.esg.cidadesesg.service;

import com.esg.cidadesesg.dto.IndicadorSocialDTO;
import com.esg.cidadesesg.model.Cidade;
import com.esg.cidadesesg.model.IndicadorSocial;
import com.esg.cidadesesg.repository.IndicadorSocialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndicadorSocialService {

    private final IndicadorSocialRepository repository;
    private final CidadeService cidadeService;

    @Transactional
    public IndicadorSocialDTO.Response criar(Long cidadeId, IndicadorSocialDTO.Request request) {
        Cidade cidade = cidadeService.findById(cidadeId);
        IndicadorSocial indicador = IndicadorSocial.builder()
                .cidade(cidade)
                .descricao(request.getDescricao())
                .taxaDesempregoPct(request.getTaxaDesempregoPct())
                .idh(request.getIdh())
                .diversidadeGeneroPct(request.getDiversidadeGeneroPct())
                .acessoSaudeBasicaPct(request.getAcessoSaudeBasicaPct())
                .taxaAlfabetizacaoPct(request.getTaxaAlfabetizacaoPct())
                .dataReferencia(request.getDataReferencia())
                .build();
        return toResponse(repository.save(indicador));
    }

    @Transactional(readOnly = true)
    public List<IndicadorSocialDTO.Response> listarPorCidade(Long cidadeId) {
        cidadeService.findById(cidadeId);
        return repository.findByCidadeId(cidadeId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IndicadorSocialDTO.Response buscarPorId(Long id) {
        return toResponse(findById(id));
    }

    @Transactional
    public IndicadorSocialDTO.Response atualizar(Long id, IndicadorSocialDTO.Request request) {
        IndicadorSocial indicador = findById(id);
        indicador.setDescricao(request.getDescricao());
        indicador.setTaxaDesempregoPct(request.getTaxaDesempregoPct());
        indicador.setIdh(request.getIdh());
        indicador.setDiversidadeGeneroPct(request.getDiversidadeGeneroPct());
        indicador.setAcessoSaudeBasicaPct(request.getAcessoSaudeBasicaPct());
        indicador.setTaxaAlfabetizacaoPct(request.getTaxaAlfabetizacaoPct());
        indicador.setDataReferencia(request.getDataReferencia());
        return toResponse(repository.save(indicador));
    }

    @Transactional
    public void deletar(Long id) {
        repository.delete(findById(id));
    }

    private IndicadorSocial findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Indicador social não encontrado com id: " + id));
    }

    private IndicadorSocialDTO.Response toResponse(IndicadorSocial i) {
        double score = calcularScore(i);
        return IndicadorSocialDTO.Response.builder()
                .id(i.getId())
                .cidadeId(i.getCidade().getId())
                .cidadeNome(i.getCidade().getNome())
                .descricao(i.getDescricao())
                .taxaDesempregoPct(i.getTaxaDesempregoPct())
                .idh(i.getIdh())
                .diversidadeGeneroPct(i.getDiversidadeGeneroPct())
                .acessoSaudeBasicaPct(i.getAcessoSaudeBasicaPct())
                .taxaAlfabetizacaoPct(i.getTaxaAlfabetizacaoPct())
                .dataReferencia(i.getDataReferencia())
                .criadoEm(i.getCriadoEm())
                .scoreSocial(score)
                .build();
    }

    private double calcularScore(IndicadorSocial i) {
        double scoreIdh = i.getIdh() * 10;
        double scoreEmprego = 10.0 - (i.getTaxaDesempregoPct() / 10);
        double scoreSaude = i.getAcessoSaudeBasicaPct() / 10;
        return Math.round((scoreIdh + scoreEmprego + scoreSaude) / 3 * 100.0) / 100.0;
    }
}

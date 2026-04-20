package com.esg.cidadesesg;

import com.esg.cidadesesg.model.*;
import com.esg.cidadesesg.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CidadeRepository cidadeRepo;
    private final IndicadorAmbientalRepository ambientalRepo;
    private final IndicadorSocialRepository socialRepo;
    private final IndicadorGovernancaRepository governancaRepo;

    @Override
    public void run(String... args) {
        // Cidade 1: São Paulo
        Cidade sp = cidadeRepo.save(Cidade.builder().nome("São Paulo").estado("SP").build());

        ambientalRepo.save(IndicadorAmbiental.builder()
                .cidade(sp).descricao("Levantamento 2024")
                .emissaoCo2Toneladas(85000.0).residuosGeradosToneladas(12000.0)
                .percentualEnergiaRenovavel(42.5).areaVerdeMq(3200.0)
                .dataReferencia(LocalDate.of(2024, 6, 1)).build());

        socialRepo.save(IndicadorSocial.builder()
                .cidade(sp).descricao("Dados sociais 2024")
                .taxaDesempregoPct(11.2).idh(0.805).diversidadeGeneroPct(48.5)
                .acessoSaudeBasicaPct(87.3).taxaAlfabetizacaoPct(97.1)
                .dataReferencia(LocalDate.of(2024, 6, 1)).build());

        governancaRepo.save(IndicadorGovernanca.builder()
                .cidade(sp).descricao("Governança 2024")
                .indiceTransparencia(7.8).conformidadeLegalPct(88.0)
                .indiceAntiCorrupcao(6.5).participacaoCidadaPct(34.0)
                .dataReferencia(LocalDate.of(2024, 6, 1)).build());

        // Cidade 2: Curitiba
        Cidade cwb = cidadeRepo.save(Cidade.builder().nome("Curitiba").estado("PR").build());

        ambientalRepo.save(IndicadorAmbiental.builder()
                .cidade(cwb).descricao("Levantamento 2024")
                .emissaoCo2Toneladas(32000.0).residuosGeradosToneladas(4500.0)
                .percentualEnergiaRenovavel(71.0).areaVerdeMq(8500.0)
                .dataReferencia(LocalDate.of(2024, 6, 1)).build());

        socialRepo.save(IndicadorSocial.builder()
                .cidade(cwb).descricao("Dados sociais 2024")
                .taxaDesempregoPct(7.5).idh(0.823).diversidadeGeneroPct(50.2)
                .acessoSaudeBasicaPct(92.1).taxaAlfabetizacaoPct(98.4)
                .dataReferencia(LocalDate.of(2024, 6, 1)).build());

        governancaRepo.save(IndicadorGovernanca.builder()
                .cidade(cwb).descricao("Governança 2024")
                .indiceTransparencia(8.9).conformidadeLegalPct(94.0)
                .indiceAntiCorrupcao(8.2).participacaoCidadaPct(52.0)
                .dataReferencia(LocalDate.of(2024, 6, 1)).build());

        // Cidade 3: Fortaleza
        Cidade for_ = cidadeRepo.save(Cidade.builder().nome("Fortaleza").estado("CE").build());

        ambientalRepo.save(IndicadorAmbiental.builder()
                .cidade(for_).descricao("Levantamento 2024")
                .emissaoCo2Toneladas(41000.0).residuosGeradosToneladas(6200.0)
                .percentualEnergiaRenovavel(58.3).areaVerdeMq(1800.0)
                .dataReferencia(LocalDate.of(2024, 6, 1)).build());

        socialRepo.save(IndicadorSocial.builder()
                .cidade(for_).descricao("Dados sociais 2024")
                .taxaDesempregoPct(14.8).idh(0.754).diversidadeGeneroPct(51.0)
                .acessoSaudeBasicaPct(79.4).taxaAlfabetizacaoPct(93.2)
                .dataReferencia(LocalDate.of(2024, 6, 1)).build());

        governancaRepo.save(IndicadorGovernanca.builder()
                .cidade(for_).descricao("Governança 2024")
                .indiceTransparencia(6.4).conformidadeLegalPct(76.0)
                .indiceAntiCorrupcao(5.8).participacaoCidadaPct(28.0)
                .dataReferencia(LocalDate.of(2024, 6, 1)).build());

        System.out.println("✅ Dados de exemplo carregados: SP, Curitiba, Fortaleza");
    }
}

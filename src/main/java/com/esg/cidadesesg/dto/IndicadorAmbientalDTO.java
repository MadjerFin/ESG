package com.esg.cidadesesg.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class IndicadorAmbientalDTO {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        @NotBlank(message = "Descrição é obrigatória")
        private String descricao;

        @NotNull @DecimalMin("0.0")
        private Double emissaoCo2Toneladas;

        @NotNull @DecimalMin("0.0")
        private Double residuosGeradosToneladas;

        @NotNull @DecimalMin("0.0") @DecimalMax("100.0")
        private Double percentualEnergiaRenovavel;

        @NotNull @DecimalMin("0.0")
        private Double areaVerdeMq;

        @NotNull
        private LocalDate dataReferencia;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long id;
        private Long cidadeId;
        private String cidadeNome;
        private String descricao;
        private Double emissaoCo2Toneladas;
        private Double residuosGeradosToneladas;
        private Double percentualEnergiaRenovavel;
        private Double areaVerdeMq;
        private LocalDate dataReferencia;
        private LocalDateTime criadoEm;
        private Double scoreAmbiental;
    }
}

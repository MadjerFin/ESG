package com.esg.cidadesesg.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class IndicadorGovernancaDTO {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        @NotBlank(message = "Descrição é obrigatória")
        private String descricao;

        @NotNull @DecimalMin("0.0") @DecimalMax("10.0")
        private Double indiceTransparencia;

        @NotNull @DecimalMin("0.0") @DecimalMax("100.0")
        private Double conformidadeLegalPct;

        @NotNull @DecimalMin("0.0") @DecimalMax("10.0")
        private Double indiceAntiCorrupcao;

        @NotNull @DecimalMin("0.0") @DecimalMax("100.0")
        private Double participacaoCidadaPct;

        @NotNull
        private LocalDate dataReferencia;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long id;
        private Long cidadeId;
        private String cidadeNome;
        private String descricao;
        private Double indiceTransparencia;
        private Double conformidadeLegalPct;
        private Double indiceAntiCorrupcao;
        private Double participacaoCidadaPct;
        private LocalDate dataReferencia;
        private LocalDateTime criadoEm;
        private Double scoreGovernanca;
    }
}

package com.esg.cidadesesg.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class IndicadorSocialDTO {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        @NotBlank(message = "Descrição é obrigatória")
        private String descricao;

        @NotNull @DecimalMin("0.0") @DecimalMax("100.0")
        private Double taxaDesempregoPct;

        @NotNull @DecimalMin("0.0") @DecimalMax("1.0")
        private Double idh;

        @NotNull @DecimalMin("0.0") @DecimalMax("100.0")
        private Double diversidadeGeneroPct;

        @NotNull @DecimalMin("0.0") @DecimalMax("100.0")
        private Double acessoSaudeBasicaPct;

        @NotNull @DecimalMin("0.0") @DecimalMax("100.0")
        private Double taxaAlfabetizacaoPct;

        @NotNull
        private LocalDate dataReferencia;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long id;
        private Long cidadeId;
        private String cidadeNome;
        private String descricao;
        private Double taxaDesempregoPct;
        private Double idh;
        private Double diversidadeGeneroPct;
        private Double acessoSaudeBasicaPct;
        private Double taxaAlfabetizacaoPct;
        private LocalDate dataReferencia;
        private LocalDateTime criadoEm;
        private Double scoreSocial;
    }
}

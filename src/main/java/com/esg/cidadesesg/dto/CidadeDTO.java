package com.esg.cidadesesg.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// ======= CIDADE =======

public class CidadeDTO {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 2, max = 100)
        private String nome;

        @NotBlank(message = "Estado é obrigatório")
        @Size(min = 2, max = 2, message = "Estado deve ter 2 letras (ex: SP)")
        private String estado;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long id;
        private String nome;
        private String estado;
        private LocalDateTime criadoEm;
        private LocalDateTime atualizadoEm;
        private Double scoreESG;
    }
}

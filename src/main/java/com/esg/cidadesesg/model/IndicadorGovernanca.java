package com.esg.cidadesesg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "indicadores_governanca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndicadorGovernanca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cidade_id", nullable = false)
    private Cidade cidade;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "Índice de transparência (0-10) é obrigatório")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    @Column(nullable = false)
    private Double indiceTransparencia;

    @NotNull(message = "Percentual de conformidade legal é obrigatório")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column(nullable = false)
    private Double conformidadeLegalPct;

    @NotNull(message = "Índice anticorrupção (0-10) é obrigatório")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    @Column(nullable = false)
    private Double indiceAntiCorrupcao;

    @NotNull(message = "Participação cidadã (%) é obrigatória")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column(nullable = false)
    private Double participacaoCidadaPct;

    @NotNull(message = "Data de referência é obrigatória")
    @Column(nullable = false)
    private LocalDate dataReferencia;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    public void prePersist() {
        criadoEm = LocalDateTime.now();
    }
}

package com.esg.cidadesesg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "indicadores_ambientais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndicadorAmbiental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cidade_id", nullable = false)
    private Cidade cidade;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "Emissão de CO2 (toneladas) é obrigatória")
    @DecimalMin(value = "0.0", message = "Emissão deve ser positiva")
    @Column(nullable = false)
    private Double emissaoCo2Toneladas;

    @NotNull(message = "Resíduos gerados (toneladas) é obrigatório")
    @DecimalMin(value = "0.0")
    @Column(nullable = false)
    private Double residuosGeradosToneladas;

    @NotNull(message = "Percentual de energia renovável é obrigatório")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column(nullable = false)
    private Double percentualEnergiaRenovavel;

    @NotNull(message = "Área verde (m²) é obrigatória")
    @DecimalMin(value = "0.0")
    @Column(nullable = false)
    private Double areaVerdeMq;

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

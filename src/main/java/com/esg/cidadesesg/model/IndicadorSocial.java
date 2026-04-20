package com.esg.cidadesesg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "indicadores_sociais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndicadorSocial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cidade_id", nullable = false)
    private Cidade cidade;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "Taxa de desemprego (%) é obrigatória")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column(nullable = false)
    private Double taxaDesempregoPct;

    @NotNull(message = "IDH é obrigatório")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "1.0")
    @Column(nullable = false)
    private Double idh;

    @NotNull(message = "Percentual de diversidade de gênero é obrigatório")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column(nullable = false)
    private Double diversidadeGeneroPct;

    @NotNull(message = "Acesso à saúde básica (%) é obrigatório")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column(nullable = false)
    private Double acessoSaudeBasicaPct;

    @NotNull(message = "Taxa de alfabetização (%) é obrigatória")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column(nullable = false)
    private Double taxaAlfabetizacaoPct;

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

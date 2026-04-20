package com.esg.cidadesesg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cidades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da cidade é obrigatório")
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Estado é obrigatório")
    @Size(min = 2, max = 2)
    @Column(nullable = false, length = 2)
    private String estado;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(nullable = false)
    private LocalDateTime atualizadoEm;

    @OneToMany(mappedBy = "cidade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IndicadorAmbiental> indicadoresAmbientais = new ArrayList<>();

    @OneToMany(mappedBy = "cidade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IndicadorSocial> indicadoresSociais = new ArrayList<>();

    @OneToMany(mappedBy = "cidade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IndicadorGovernanca> indicadoresGovernanca = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}

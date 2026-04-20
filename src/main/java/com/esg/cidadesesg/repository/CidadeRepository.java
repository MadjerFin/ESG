package com.esg.cidadesesg.repository;

import com.esg.cidadesesg.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    Optional<Cidade> findByNomeIgnoreCase(String nome);
    List<Cidade> findByEstadoIgnoreCase(String estado);
    boolean existsByNomeIgnoreCaseAndEstadoIgnoreCase(String nome, String estado);
}

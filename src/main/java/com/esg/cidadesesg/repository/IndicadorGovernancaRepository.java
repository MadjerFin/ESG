package com.esg.cidadesesg.repository;

import com.esg.cidadesesg.model.IndicadorGovernanca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndicadorGovernancaRepository extends JpaRepository<IndicadorGovernanca, Long> {
    List<IndicadorGovernanca> findByCidadeId(Long cidadeId);
}

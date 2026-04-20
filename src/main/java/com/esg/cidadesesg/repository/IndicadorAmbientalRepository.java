package com.esg.cidadesesg.repository;

import com.esg.cidadesesg.model.IndicadorAmbiental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndicadorAmbientalRepository extends JpaRepository<IndicadorAmbiental, Long> {
    List<IndicadorAmbiental> findByCidadeId(Long cidadeId);
}

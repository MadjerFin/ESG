package com.esg.cidadesesg.repository;

import com.esg.cidadesesg.model.IndicadorSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndicadorSocialRepository extends JpaRepository<IndicadorSocial, Long> {
    List<IndicadorSocial> findByCidadeId(Long cidadeId);
}

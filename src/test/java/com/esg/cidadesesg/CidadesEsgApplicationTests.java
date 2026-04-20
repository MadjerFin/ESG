package com.esg.cidadesesg;

import com.esg.cidadesesg.model.Cidade;
import com.esg.cidadesesg.repository.CidadeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CidadesEsgApplicationTests {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Test
    void contextLoads() {
        assertNotNull(cidadeRepository);
    }

    @Test
    void deveSalvarEBuscarCidade() {
        Cidade cidade = Cidade.builder()
                .nome("Belo Horizonte")
                .estado("MG")
                .build();
        Cidade salva = cidadeRepository.save(cidade);

        assertNotNull(salva.getId());
        assertEquals("Belo Horizonte", salva.getNome());
        assertEquals("MG", salva.getEstado());
    }

    @Test
    void deveVerificarSeExisteCidade() {
        Cidade cidade = Cidade.builder().nome("Recife").estado("PE").build();
        cidadeRepository.save(cidade);

        boolean existe = cidadeRepository.existsByNomeIgnoreCaseAndEstadoIgnoreCase("recife", "pe");
        assertTrue(existe);
    }

    @Test
    void deveListarCidadesPorEstado() {
        cidadeRepository.save(Cidade.builder().nome("Campinas").estado("SP").build());
        cidadeRepository.save(Cidade.builder().nome("Santos").estado("SP").build());

        var cidades = cidadeRepository.findByEstadoIgnoreCase("SP");
        assertTrue(cidades.size() >= 2);
    }
}

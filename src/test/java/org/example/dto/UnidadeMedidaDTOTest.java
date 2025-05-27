package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeMedidaDTOTest {

    @Test
    void deveConstruirUnidadeMedidaDTOComBuilder() {
        UnidadeMedidaDTO dto = UnidadeMedidaDTO.builder()
                .id(5L)
                .sigla("ML")
                .descricao("Mililitro")
                .build();

        assertEquals(5L, dto.getId());
        assertEquals("ML", dto.getSigla());
        assertEquals("Mililitro", dto.getDescricao());
    }
}

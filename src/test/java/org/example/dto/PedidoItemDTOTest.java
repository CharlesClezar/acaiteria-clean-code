package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoItemDTOTest {

    @Test
    void deveConstruirPedidoItemDTOComBuilder() {
        PedidoItemDTO dto = PedidoItemDTO.builder()
                .id(99L)
                .descricaoItem("Açaí com Banana")
                .valorVenda(12.50)
                .quantidade(2)
                .build();

        assertEquals(99L, dto.getId());
        assertEquals("Açaí com Banana", dto.getDescricaoItem());
        assertEquals(12.50, dto.getValorVenda());
        assertEquals(2, dto.getQuantidade());
    }
}

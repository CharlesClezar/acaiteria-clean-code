package org.example.dto;

import org.example.model.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PedidoDTOTest {

    @Test
    void deveConstruirPedidoDTOComBuilder() {
        LocalDateTime dataHora = LocalDateTime.now();

        PedidoDTO dto = PedidoDTO.builder()
                .id(1L)
                .dataHora(dataHora)
                .valorTotal(100.0)
                .desconto(10.0)
                .cliente("João da Silva")
                .status(Status.FECHADO)
                .build();

        assertEquals(1L, dto.getId());
        assertEquals(dataHora, dto.getDataHora());
        assertEquals(100.0, dto.getValorTotal());
        assertEquals(10.0, dto.getDesconto());
        assertEquals("João da Silva", dto.getCliente());
        assertEquals(Status.FECHADO, dto.getStatus());
    }
}

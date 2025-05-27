package org.example.dto;

import org.example.model.TipoMovimentacao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovimentacaoEstoqueDTOTest {

    @Test
    void deveConstruirMovimentacaoEstoqueDTOComBuilder() {
        MovimentacaoEstoqueDTO dto = MovimentacaoEstoqueDTO.builder()
                .id(100L)
                .quantidadeMovimento(50)
                .tipo(TipoMovimentacao.ENTRADA)
                .valor(275.0)
                .build();

        assertEquals(100L, dto.getId());
        assertEquals(50, dto.getQuantidadeMovimento());
        assertEquals(TipoMovimentacao.ENTRADA, dto.getTipo());
        assertEquals(275.0, dto.getValor());
    }
}

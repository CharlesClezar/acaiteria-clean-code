package org.example.dto;

import org.example.model.TipoItem;
import org.example.model.UnidadeMedida;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemDTOTest {

    @Test
    void deveConstruirItemDTOComBuilder() {
        UnidadeMedida unidade = new UnidadeMedida();
        unidade.setId(1L);
        unidade.setDescricao("Litro");
        unidade.setSigla("L");

        ItemDTO dto = ItemDTO.builder()
                .id(10L)
                .descricao("Açaí Tradicional")
                .precoCompra(5.50)
                .precoVenda(9.90)
                .quantidadeEstoque(100)
                .imagem("acai.png")
                .unidadeMedida(unidade)
                .filtro(TipoItem.ACAIS)
                .ativo(true)
                .build();

        assertEquals("Açaí Tradicional", dto.getDescricao());
        assertEquals(5.50, dto.getPrecoCompra());
        assertEquals(9.90, dto.getPrecoVenda());
        assertEquals(100, dto.getQuantidadeEstoque());
        assertEquals("acai.png", dto.getImagem());
        assertEquals(unidade, dto.getUnidadeMedida());
        assertEquals(TipoItem.ACAIS, dto.getFiltro());
        assertTrue(dto.getAtivo());
    }
}

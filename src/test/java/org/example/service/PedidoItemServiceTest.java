package org.example.service;

import org.example.model.Item;
import org.example.model.PedidoItem;
import org.example.model.TipoMovimentacao;
import org.example.repository.PedidoItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoItemServiceTest {

    private PedidoItemRepository repository;
    private ModelMapper modelMapper;
    private MovimentacaoEstoqueService movimentacaoEstoqueService;
    private ItemService itemService;
    private PedidoItemService service;

    @BeforeEach
    void setUp() {
        repository = mock(PedidoItemRepository.class);
        modelMapper = mock(ModelMapper.class);
        movimentacaoEstoqueService = mock(MovimentacaoEstoqueService.class);
        itemService = mock(ItemService.class);
        service = new PedidoItemService(repository, modelMapper, movimentacaoEstoqueService, itemService);
    }

    @Test
    void deveSalvarPedidoItemComEstoqueValidoERegistrarMovimentacao() {
        Item item = new Item();
        item.setId(1L);
        item.setQuantidadeEstoque(10);

        PedidoItem pedidoItem = new PedidoItem();
        pedidoItem.setItem(item);
        pedidoItem.setQuantidade(5);
        pedidoItem.setValorVenda(100.0);

        when(itemService.buscarItemPorId(1L)).thenReturn(item);
        when(repository.save(pedidoItem)).thenReturn(pedidoItem);

        PedidoItem salvo = service.salvar(pedidoItem);

        assertEquals(5, salvo.getQuantidade());
        verify(movimentacaoEstoqueService).registrarMovimentacao(item, 5, TipoMovimentacao.SAIDA, 100.0);
        verify(itemService).editarItem(eq(1L), any(Item.class));
    }

    @Test
    void deveLancarExcecaoSeEstoqueForInsuficiente() {
        Item item = new Item();
        item.setId(1L);
        item.setQuantidadeEstoque(3); // Menor que a quantidade solicitada

        PedidoItem pedidoItem = new PedidoItem();
        pedidoItem.setItem(item);
        pedidoItem.setQuantidade(5);
        pedidoItem.setValorVenda(100.0);

        when(itemService.buscarItemPorId(1L)).thenReturn(item);

        Exception ex = assertThrows(ValidationException.class, () -> service.salvar(pedidoItem));
        assertEquals("Quantidade solicitada é maior do que o estoque disponível!", ex.getMessage());
    }

    @Test
    void deveSalvarPedidoItemSemMovimentacao() {
        PedidoItem pedidoItem = new PedidoItem();
        pedidoItem.setQuantidade(2);

        when(repository.save(pedidoItem)).thenReturn(pedidoItem);

        PedidoItem salvo = service.salvarSemMovimentacao(pedidoItem);

        assertEquals(2, salvo.getQuantidade());
        verify(repository).save(pedidoItem);
    }
}

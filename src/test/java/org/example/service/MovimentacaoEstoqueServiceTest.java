package org.example.service;

import org.example.model.Item;
import org.example.model.MovimentacaoEstoque;
import org.example.model.TipoMovimentacao;
import org.example.repository.MovimentacaoEstoqueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimentacaoEstoqueServiceTest {

    private MovimentacaoEstoqueRepository repository;
    private ModelMapper modelMapper;
    private MovimentacaoEstoqueService service;

    @BeforeEach
    void setUp() {
        repository = mock(MovimentacaoEstoqueRepository.class);
        modelMapper = mock(ModelMapper.class);
        service = new MovimentacaoEstoqueService(repository, modelMapper);
    }

    @Test
    void deveRegistrarMovimentacaoValida() {
        Item item = new Item();
        item.setId(1L);

        doReturn(new MovimentacaoEstoque()).when(repository).save(any(MovimentacaoEstoque.class));

        assertDoesNotThrow(() -> service.registrarMovimentacao(item, 10, TipoMovimentacao.ENTRADA, 100.0));
        verify(repository, times(1)).save(any(MovimentacaoEstoque.class));
    }

    @Test
    void deveLancarExcecaoSeQuantidadeForNegativa() {
        Item item = new Item();
        item.setId(1L);

        Exception ex = assertThrows(ValidationException.class, () ->
                service.registrarMovimentacao(item, -5, TipoMovimentacao.SAIDA, 50.0)
        );

        assertEquals("Quantidade não pode ser menor que zero!", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeQuantidadeForNula() {
        Item item = new Item();
        item.setId(1L);

        Exception ex = assertThrows(ValidationException.class, () ->
                service.registrarMovimentacao(item, null, TipoMovimentacao.SAIDA, 50.0)
        );

        assertEquals("Quantidade não pode ser menor que zero!", ex.getMessage());
    }
}

package org.example.service;

import com.querydsl.core.types.Predicate;
import org.example.model.Item;
import org.example.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    private ItemRepository repository;
    private ModelMapper modelMapper;
    private ItemService service;

    @BeforeEach
    void setUp() {
        repository = mock(ItemRepository.class);
        modelMapper = mock(ModelMapper.class);
        service = new ItemService(repository, modelMapper);
    }

    @Test
    void deveLancarExcecaoSeItemForNuloAoSalvar() {
        Exception ex = assertThrows(ValidationException.class, () -> service.salvarItem(null));
        assertEquals("Item inválido.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeDescricaoDuplicadaAoSalvar() {
        Item item = new Item();
        item.setDescricao("Açaí");
        item.setPrecoCompra(5.0);
        item.setPrecoVenda(10.0);

        when(repository.exists(any(Predicate.class))).thenReturn(true);

        Exception ex = assertThrows(ValidationException.class, () -> service.salvarItem(item));
        assertTrue(ex.getMessage().contains("Já existe um item com a descrição"));
    }

    @Test
    void deveLancarExcecaoSePrecoCompraMaiorQuePrecoVenda() {
        Item item = new Item();
        item.setDescricao("Açaí");
        item.setPrecoCompra(12.0);
        item.setPrecoVenda(10.0);

        when(repository.exists(any(Predicate.class))).thenReturn(false);

        Exception ex = assertThrows(ValidationException.class, () -> service.salvarItem(item));
        assertEquals("O preço de compra não pode ser maior que o de venda.", ex.getMessage());
    }

    @Test
    void deveSalvarItemValido() {
        Item item = new Item();
        item.setDescricao("Açaí");
        item.setPrecoCompra(5.0);
        item.setPrecoVenda(10.0);

        when(repository.exists(any(Predicate.class))).thenReturn(false);
        when(repository.save(item)).thenReturn(item);

        Item salvo = service.salvarItem(item);
        assertEquals("Açaí", salvo.getDescricao());
    }

    @Test
    void deveLancarExcecaoSeItemForNuloAoEditar() {
        Exception ex = assertThrows(ValidationException.class, () -> service.editarItem(1L, null));
        assertEquals("Item inválido.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeItemNaoExistirAoEditar() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Item item = new Item();
        item.setDescricao("Novo");

        Exception ex = assertThrows(NotFoundException.class, () -> service.editarItem(1L, item));
        assertTrue(ex.getMessage().contains("Item com ID 1 não encontrado."));
    }

    @Test
    void deveLancarExcecaoSeDescricaoDuplicadaAoEditar() {
        Item existente = new Item();
        existente.setId(1L);
        existente.setDescricao("Açaí");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.exists(any(Predicate.class))).thenReturn(true);

        Item novo = new Item();
        novo.setDescricao("Açaí");

        Exception ex = assertThrows(ValidationException.class, () -> service.editarItem(1L, novo));
        assertTrue(ex.getMessage().contains("Já existe um item com a descrição"));
    }

    @Test
    void deveEditarItemValido() {
        Item existente = new Item();
        existente.setId(1L);
        existente.setDescricao("Velho");

        Item novo = new Item();
        novo.setDescricao("Novo");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.exists(any(Predicate.class))).thenReturn(false);
        doAnswer(invocation -> {
            Item source = invocation.getArgument(0);
            Item target = invocation.getArgument(1);
            target.setDescricao(source.getDescricao());
            return null;
        }).when(modelMapper).map(novo, existente);
        when(repository.save(existente)).thenReturn(existente);

        Item atualizado = service.editarItem(1L, novo);
        assertEquals("Novo", atualizado.getDescricao());
    }
}

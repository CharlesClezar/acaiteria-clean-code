package org.example.service;

import org.example.model.Pedido;
import org.example.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    private PedidoRepository repository;
    private ModelMapper modelMapper;
    private PedidoService service;

    @BeforeEach
    void setUp() {
        repository = mock(PedidoRepository.class);
        modelMapper = mock(ModelMapper.class);
        service = new PedidoService(repository, modelMapper);
    }

    @Test
    void deveLancarExcecaoAoSalvarPedidoNulo() {
        Exception ex = assertThrows(ValidationException.class, () -> service.salvarPedido(null));
        assertEquals("Pedido inválido.", ex.getMessage());
    }

    @Test
    void deveSalvarPedidoValido() {
        Pedido pedido = new Pedido();
        pedido.setCliente("João");

        when(repository.save(pedido)).thenReturn(pedido);

        Pedido salvo = service.salvarPedido(pedido);

        assertEquals("João", salvo.getCliente());
        verify(repository).save(pedido);
    }

    @Test
    void deveBuscarPedidoPorIdExistente() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(pedido));

        Pedido resultado = service.buscarPedidoPorId(1L);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarPedidoPorIdInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(NotFoundException.class, () -> service.buscarPedidoPorId(1L));
        assertEquals("Pedido não encontrado!", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoAoEditarPedidoNulo() {
        Exception ex = assertThrows(ValidationException.class, () -> service.editarPedido(1L, null));
        assertEquals("Pedido inválido.", ex.getMessage());
    }

    @Test
    void deveEditarPedidoValido() {
        Pedido existente = new Pedido();
        existente.setId(1L);
        existente.setCliente("Antigo");

        Pedido novo = new Pedido();
        novo.setCliente("Atualizado");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        doAnswer(invocation -> {
            Pedido source = invocation.getArgument(0);
            Pedido target = invocation.getArgument(1);
            target.setCliente(source.getCliente());
            return null;
        }).when(modelMapper).map(novo, existente);
        when(repository.save(existente)).thenReturn(existente);

        Pedido atualizado = service.editarPedido(1L, novo);

        assertEquals("Atualizado", atualizado.getCliente());
        verify(repository).save(existente);
    }
}

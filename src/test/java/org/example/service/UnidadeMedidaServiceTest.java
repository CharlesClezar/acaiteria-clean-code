package org.example.service;

import com.querydsl.core.types.Predicate;
import org.example.model.UnidadeMedida;
import org.example.repository.UnidadeMedidaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnidadeMedidaServiceTest {

    private UnidadeMedidaRepository repository;
    private ModelMapper modelMapper;
    private UnidadeMedidaService service;

    @BeforeEach
    void setUp() {
        repository = mock(UnidadeMedidaRepository.class);
        modelMapper = mock(ModelMapper.class);
        service = new UnidadeMedidaService(repository, modelMapper);
    }

    @Test
    void deveLancarExcecaoAoSalvarSiglaNula() {
        UnidadeMedida unidade = new UnidadeMedida();
        unidade.setDescricao("Litro");

        Exception ex = assertThrows(ValidationException.class, () -> service.salvarUnidadeMedida(unidade));
        assertEquals("Unidade de medida inválida.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeSiglaDuplicadaAoSalvar() {
        UnidadeMedida unidade = new UnidadeMedida();
        unidade.setSigla("L");
        unidade.setDescricao("Litro");

        when(repository.exists(any(Predicate.class))).thenReturn(true);

        Exception ex = assertThrows(ValidationException.class, () -> service.salvarUnidadeMedida(unidade));
        assertEquals("Sigla já existente no sistema!", ex.getMessage());
    }

    @Test
    void deveSalvarUnidadeValida() {
        UnidadeMedida unidade = new UnidadeMedida();
        unidade.setSigla("L");
        unidade.setDescricao("Litro");

        when(repository.exists(any(Predicate.class))).thenReturn(false);
        when(repository.save(unidade)).thenReturn(unidade);

        UnidadeMedida salvo = service.salvarUnidadeMedida(unidade);

        assertEquals("L", salvo.getSigla());
        verify(repository).save(unidade);
    }

    @Test
    void deveEditarUnidadeComSiglaValida() {
        UnidadeMedida existente = new UnidadeMedida();
        existente.setId(1L);
        existente.setSigla("ML");

        UnidadeMedida nova = new UnidadeMedida();
        nova.setSigla("L");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.exists(any(Predicate.class))).thenReturn(false);
        doAnswer(invocation -> {
            UnidadeMedida src = invocation.getArgument(0);
            UnidadeMedida dest = invocation.getArgument(1);
            dest.setSigla(src.getSigla());
            return null;
        }).when(modelMapper).map(nova, existente);
        when(repository.save(existente)).thenReturn(existente);

        UnidadeMedida atualizada = service.editarUnidadeMedida(1L, nova);
        assertEquals("L", atualizada.getSigla());
    }

    @Test
    void deveLancarExcecaoSeSiglaDuplicadaAoEditar() {
        UnidadeMedida existente = new UnidadeMedida();
        existente.setId(1L);
        existente.setSigla("ML");

        UnidadeMedida nova = new UnidadeMedida();
        nova.setSigla("L");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.exists(any(Predicate.class))).thenReturn(true);

        Exception ex = assertThrows(ValidationException.class, () -> service.editarUnidadeMedida(1L, nova));
        assertEquals("Sigla já existente no sistema!", ex.getMessage());
    }
}

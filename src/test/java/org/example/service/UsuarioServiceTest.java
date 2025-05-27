package org.example.service;

import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import com.querydsl.core.types.Predicate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepository repository;
    private ModelMapper modelMapper;
    private UsuarioService service;

    @BeforeEach
    void setUp() {
        repository = mock(UsuarioRepository.class);
        modelMapper = mock(ModelMapper.class);
        service = new UsuarioService(repository, modelMapper);
    }

    @Test
    void deveLancarExcecaoSeUsuarioOuLoginNuloAoSalvar() {
        Usuario usuario = new Usuario();
        Exception ex = assertThrows(ValidationException.class, () -> service.salvarUsuario(usuario));
        assertEquals("Usuário inválido.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeLoginDuplicadoAoSalvar() {
        Usuario usuario = new Usuario();
        usuario.setLogin("admin");

        when(repository.exists(any(Predicate.class))).thenReturn(true);

        Exception ex = assertThrows(ValidationException.class, () -> service.salvarUsuario(usuario));
        assertEquals("Login já existente no sistema!", ex.getMessage());
    }

    @Test
    void deveSalvarUsuarioValido() {
        Usuario usuario = new Usuario();
        usuario.setLogin("admin");

        when(repository.exists(any(Predicate.class))).thenReturn(false);
        when(repository.save(usuario)).thenReturn(usuario);

        Usuario salvo = service.salvarUsuario(usuario);
        assertEquals("admin", salvo.getLogin());
        verify(repository).save(usuario);
    }

    @Test
    void deveLancarExcecaoAoEditarUsuarioNulo() {
        Exception ex = assertThrows(ValidationException.class, () -> service.editarUsuario(1L, null));
        assertEquals("Usuário inválido.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeLoginDuplicadoAoEditar() {
        Usuario existente = new Usuario();
        existente.setId(1L);
        existente.setLogin("admin");

        Usuario novo = new Usuario();
        novo.setLogin("admin");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.exists(any(Predicate.class))).thenReturn(true);

        Exception ex = assertThrows(ValidationException.class, () -> service.editarUsuario(1L, novo));
        assertEquals("Login já existente no sistema!", ex.getMessage());
    }

    @Test
    void deveEditarUsuarioValido() {
        Usuario existente = new Usuario();
        existente.setId(1L);
        existente.setLogin("antigo");

        Usuario novo = new Usuario();
        novo.setLogin("novo");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.exists(any(Predicate.class))).thenReturn(false);
        doAnswer(invocation -> {
            Usuario src = invocation.getArgument(0);
            Usuario dest = invocation.getArgument(1);
            dest.setLogin(src.getLogin());
            return null;
        }).when(modelMapper).map(novo, existente);
        when(repository.save(existente)).thenReturn(existente);

        Usuario atualizado = service.editarUsuario(1L, novo);
        assertEquals("novo", atualizado.getLogin());
    }

    @Test
    void deveRetornarUsuarioSeLoginESenhaCorretos() {
        Usuario usuario = new Usuario();
        usuario.setLogin("admin");
        usuario.setSenha("123");

        when(repository.findByLogin("admin")).thenReturn(Optional.of(usuario));

        Usuario result = service.findByUserAndPassword("admin", "123");
        assertNotNull(result);
        assertEquals("admin", result.getLogin());
    }

    @Test
    void deveRetornarNullSeSenhaIncorreta() {
        Usuario usuario = new Usuario();
        usuario.setLogin("admin");
        usuario.setSenha("123");

        when(repository.findByLogin("admin")).thenReturn(Optional.of(usuario));

        Usuario result = service.findByUserAndPassword("admin", "errada");
        assertNull(result);
    }
}

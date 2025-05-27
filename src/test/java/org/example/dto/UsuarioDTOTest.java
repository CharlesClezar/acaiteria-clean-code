package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDTOTest {

    @Test
    void deveConstruirUsuarioDTOComBuilder() {
        UsuarioDTO dto = UsuarioDTO.builder()
                .id(7L)
                .nome("Carlos Almeida")
                .login("carlos.almeida")
                .permissao(2)
                .build();

        assertEquals(7L, dto.getId());
        assertEquals("Carlos Almeida", dto.getNome());
        assertEquals("carlos.almeida", dto.getLogin());
        assertEquals(2, dto.getPermissao());
    }
}

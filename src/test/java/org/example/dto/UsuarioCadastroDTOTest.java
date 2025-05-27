package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioCadastroDTOTest {

    @Test
    void deveConstruirUsuarioCadastroDTOComBuilder() {
        UsuarioCadastroDTO dto = UsuarioCadastroDTO.builder()
                .nome("Maria Oliveira")
                .login("maria123")
                .senha("senhaForte@2024")
                .permissao(1)
                .build();

        assertEquals("Maria Oliveira", dto.getNome());
        assertEquals("maria123", dto.getLogin());
        assertEquals("senhaForte@2024", dto.getSenha());
        assertEquals(1, dto.getPermissao());

        assertTrue(dto.toString().contains("Maria Oliveira"));
        assertTrue(dto.toString().contains("maria123"));
        assertFalse(dto.toString().contains("senhaForte@2024")); // por segurança
    }
}

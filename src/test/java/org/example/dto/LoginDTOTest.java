package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginDTOTest {

    @Test
    void deveConstruirLoginDTOComBuilder() {
        LoginDTO dto = LoginDTO.builder()
                .login("usuario_teste")
                .senha("senhaSegura123")
                .build();

        assertEquals("usuario_teste", dto.getLogin());
        assertEquals("senhaSegura123", dto.getSenha());
        assertTrue(dto.toString().contains("usuario_teste"));
        assertFalse(dto.toString().contains("senhaSegura123")); // Garantia de segurança no toString
    }
}

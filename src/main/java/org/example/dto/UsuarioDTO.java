package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String login;
    private Integer permissao;

    // Conversões
    public static UsuarioDTO fromEntity(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getPermissao()
        );
    }

    public static List<UsuarioDTO> fromEntity(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(UsuarioDTO::fromEntity)
                .toList();
    }

    public static Page<UsuarioDTO> fromEntity(Page<Usuario> usuarios) {
        List<UsuarioDTO> dtoList = fromEntity(usuarios.getContent());
        return new PageImpl<>(dtoList, usuarios.getPageable(), usuarios.getTotalElements());
    }
}

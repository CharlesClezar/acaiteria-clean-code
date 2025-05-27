package org.example.dto;

import org.example.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioDTO {
    private Long id;
    private String nome;
    private String login;
    private Integer permissao;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getPermissao() {
        return permissao;
    }

    public void setPermissao(Integer permissao) {
        this.permissao = permissao;
    }

    // Conversões
    public static UsuarioDTO fromEntity(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setLogin(usuario.getLogin());
        dto.setPermissao(usuario.getPermissao());
        return dto;
    }

    public static List<UsuarioDTO> fromEntity(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioDTO::fromEntity).collect(Collectors.toList());
    }

    public static Page<UsuarioDTO> fromEntity(Page<Usuario> usuarios) {
        List<UsuarioDTO> list = usuarios.stream().map(UsuarioDTO::fromEntity).collect(Collectors.toList());
        return new PageImpl<>(list, usuarios.getPageable(), usuarios.getTotalElements());
    }
}

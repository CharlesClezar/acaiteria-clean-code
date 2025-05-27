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

    // Construtores
    public UsuarioDTO() {}

    public UsuarioDTO(Long id, String nome, String login, Integer permissao) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.permissao = permissao;
    }

    // Getters e Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

    public Integer getPermissao() { return permissao; }

    public void setPermissao(Integer permissao) { this.permissao = permissao; }

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
                .collect(Collectors.toList());
    }

    public static Page<UsuarioDTO> fromEntity(Page<Usuario> usuarios) {
        List<UsuarioDTO> dtoList = fromEntity(usuarios.getContent());
        return new PageImpl<>(dtoList, usuarios.getPageable(), usuarios.getTotalElements());
    }
}

package org.example.dto;

import org.example.model.Usuario;

import javax.validation.constraints.NotBlank;

public class UsuarioCadastroDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String login;

    @NotBlank
    private String senha;

    private Integer permissao;

    // Getters e Setters
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getPermissao() {
        return permissao;
    }

    public void setPermissao(Integer permissao) {
        this.permissao = permissao;
    }

    // Conversão para entidade
    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setNome(this.getNome());
        usuario.setLogin(this.getLogin());
        usuario.setSenha(this.getSenha());
        usuario.setPermissao(this.getPermissao());
        return usuario;
    }
}

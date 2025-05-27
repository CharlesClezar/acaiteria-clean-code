package org.example.dto;

import org.example.model.Usuario;

import javax.validation.constraints.NotBlank;

public class UsuarioCadastroDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O login é obrigatório.")
    private String login;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    private Integer permissao;

    // Construtores
    public UsuarioCadastroDTO() {}

    public UsuarioCadastroDTO(String nome, String login, String senha, Integer permissao) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.permissao = permissao;
    }

    // Getters e Setters
    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }

    public void setSenha(String senha) { this.senha = senha; }

    public Integer getPermissao() { return permissao; }

    public void setPermissao(Integer permissao) { this.permissao = permissao; }

    // Conversão para entidade
    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setNome(this.nome);
        usuario.setLogin(this.login);
        usuario.setSenha(this.senha);
        usuario.setPermissao(this.permissao);
        return usuario;
    }

    // Logging seguro (opcional)
    @Override
    public String toString() {
        return "UsuarioCadastroDTO{nome='" + nome + "', login='" + login + "', permissao=" + permissao + "}";
    }
}

package org.example.dto;

import javax.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank(message = "Login não pode estar em branco")
    private String login;

    @NotBlank(message = "Senha não pode estar em branco")
    private String senha;

    // Construtores
    public LoginDTO() {}

    public LoginDTO(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    // Getters e setters
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

    // Logging seguro
    @Override
    public String toString() {
        return "LoginDTO{login='" + login + "'}";
    }
}

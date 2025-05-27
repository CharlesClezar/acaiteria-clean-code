package org.example.dto;

import javax.validation.constraints.NotBlank;

public class LoginDTO {
    @NotBlank
    private String login;

    @NotBlank
    private String senha;

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
}

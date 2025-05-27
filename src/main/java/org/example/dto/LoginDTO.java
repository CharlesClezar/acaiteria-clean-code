package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDTO {

    @NotBlank(message = "Login não pode estar em branco")
    private String login;

    @NotBlank(message = "Senha não pode estar em branco")
    private String senha;

    // Logging seguro
    @Override
    public String toString() {
        return "LoginDTO{login='" + login + "'}";
    }
}

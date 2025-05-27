package org.example.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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

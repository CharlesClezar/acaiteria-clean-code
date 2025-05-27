package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Usuario;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioCadastroDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O login é obrigatório.")
    private String login;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    private Integer permissao;

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

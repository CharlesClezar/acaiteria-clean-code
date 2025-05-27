package org.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
public class Usuario extends EntityId {
    @NotNull @NotBlank @Size(max = 60)
    @Column(name = "nome", nullable = false, length = 60)
    private String nome;
    @NotNull @NotBlank @Size(max = 60)
    @Column(name = "login", nullable = false, length = 60)
    private String login;
    @NotNull @NotBlank @Size(max = 60)
    @Column(name = "senha", nullable = false, length = 20)
    private String senha;
    @NotNull
    @Column(name = "permissao", nullable = false)
    private Integer permissao;

}
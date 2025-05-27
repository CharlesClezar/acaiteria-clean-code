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
public class UnidadeMedida extends EntityId {
    @NotNull @NotBlank @Size(max = 20)
    @Column(name = "sigla", nullable = false, length = 20)
    private String sigla;
    @NotNull @NotBlank @Size(max = 50)
    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;

}
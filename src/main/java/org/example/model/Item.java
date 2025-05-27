package org.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
public class Item extends EntityId {
    @NotNull
    @OneToOne
    @JoinColumn(name = "unidade_medida_id")
    private UnidadeMedida unidadeMedida;
    @NotNull @NotBlank @Size(max = 250)
    @Column(name = "descricao", nullable = false, length = 250)
    private String descricao;
    @NotNull @PositiveOrZero
    @Column(name = "quantidade_estoque", nullable = false)
    private Integer quantidadeEstoque;
    @NotNull @Positive
    @Column(name = "preco_compra", nullable = false, precision = 10, scale = 2)
    private Double precoCompra;
    @NotNull @Positive
    @Column(name = "preco_venda", nullable = false, precision = 10, scale = 2)
    private Double precoVenda;
    @NotNull @NotBlank @Size(max = 250)
    @Column(name = "imagem", nullable = false, length = 250)
    private String imagem;
    @NotNull @Enumerated(EnumType.STRING)
    @Column(name = "filtro", nullable = false)
    private TipoItem filtro;
    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;
}
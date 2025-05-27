package org.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class MovimentacaoEstoque extends EntityId {

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Item item;

    @NotNull
    @Positive
    @Column(name = "quantidade_movimento", nullable = false)
    private Integer quantidadeMovimento;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDateTime dataHora;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMovimentacao tipo;

    @NotNull
    @Positive
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private Double valor;
}

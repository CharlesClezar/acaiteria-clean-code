package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
public class PedidoItem extends EntityId {

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnoreProperties("pedidoItens") // Ignora recursão infinita durante a serialização
    private Pedido pedido;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @NotBlank
    @Size(max = 250)
    @Column(name = "descricao_item", nullable = false, length = 250)
    private String descricaoItem;

    @NotNull
    @Positive
    @Column(name = "valor_venda", nullable = false, precision = 10, scale = 2)
    private Double valorVenda;

    @NotNull
    @Positive
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

}

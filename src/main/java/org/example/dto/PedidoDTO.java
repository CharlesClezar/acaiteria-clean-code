package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Pedido;
import org.example.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoDTO {

    private Long id;
    private LocalDateTime dataHora;
    private Double valorTotal;
    private Double desconto;
    private String cliente;
    private Status status;

    // Conversões
    public static PedidoDTO fromEntity(Pedido pedido) {
        if (pedido == null) return null;
        return new PedidoDTO(
                pedido.getId(),
                pedido.getDataHora(),
                pedido.getValorTotal(),
                pedido.getDesconto(),
                pedido.getCliente(),
                pedido.getStatus()
        );
    }

    public Pedido toEntity() {
        Pedido pedido = new Pedido();
        pedido.setId(this.id);
        pedido.setDataHora(this.dataHora);
        pedido.setValorTotal(this.valorTotal);
        pedido.setDesconto(this.desconto);
        pedido.setCliente(this.cliente);
        pedido.setStatus(this.status);
        return pedido;
    }

    public static List<PedidoDTO> fromEntity(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(PedidoDTO::fromEntity)
                .toList();
    }

    public static Page<PedidoDTO> fromEntity(Page<Pedido> pedidos) {
        List<PedidoDTO> dtoList = fromEntity(pedidos.getContent());
        return new PageImpl<>(dtoList, pedidos.getPageable(), pedidos.getTotalElements());
    }
}

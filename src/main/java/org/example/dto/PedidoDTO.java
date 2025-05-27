package org.example.dto;

import org.example.model.Pedido;
import org.example.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {

    private Long id;
    private LocalDateTime dataHora;
    private Double valorTotal;
    private Double desconto;
    private String cliente;
    private Status status;

    // Construtores
    public PedidoDTO() {}

    public PedidoDTO(Long id, LocalDateTime dataHora, Double valorTotal, Double desconto, String cliente, Status status) {
        this.id = id;
        this.dataHora = dataHora;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
        this.cliente = cliente;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDataHora() { return dataHora; }

    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Double getValorTotal() { return valorTotal; }

    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public Double getDesconto() { return desconto; }

    public void setDesconto(Double desconto) { this.desconto = desconto; }

    public String getCliente() { return cliente; }

    public void setCliente(String cliente) { this.cliente = cliente; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

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

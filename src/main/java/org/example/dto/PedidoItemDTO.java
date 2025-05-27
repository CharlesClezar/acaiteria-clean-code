package org.example.dto;

import org.example.model.PedidoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoItemDTO {

    private Long id;
    private String descricaoItem;
    private Double valorVenda;
    private Integer quantidade;

    // Construtores
    public PedidoItemDTO() {}

    public PedidoItemDTO(Long id, String descricaoItem, Double valorVenda, Integer quantidade) {
        this.id = id;
        this.descricaoItem = descricaoItem;
        this.valorVenda = valorVenda;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getDescricaoItem() { return descricaoItem; }

    public void setDescricaoItem(String descricaoItem) { this.descricaoItem = descricaoItem; }

    public Double getValorVenda() { return valorVenda; }

    public void setValorVenda(Double valorVenda) { this.valorVenda = valorVenda; }

    public Integer getQuantidade() { return quantidade; }

    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    // Conversão de entidade para DTO
    public static PedidoItemDTO fromEntity(PedidoItem pedidoItem) {
        if (pedidoItem == null) return null;
        return new PedidoItemDTO(
                pedidoItem.getId(),
                pedidoItem.getDescricaoItem(),
                pedidoItem.getValorVenda(),
                pedidoItem.getQuantidade()
        );
    }

    // Conversão de DTO para entidade
    public PedidoItem toEntity() {
        PedidoItem entity = new PedidoItem();
        entity.setId(this.id);
        entity.setDescricaoItem(this.descricaoItem);
        entity.setValorVenda(this.valorVenda);
        entity.setQuantidade(this.quantidade);
        return entity;
    }

    // Conversão de lista de entidades
    public static List<PedidoItemDTO> fromEntity(List<PedidoItem> entities) {
        return entities.stream()
                .map(PedidoItemDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Conversão de página de entidades
    public static Page<PedidoItemDTO> fromEntity(Page<PedidoItem> page) {
        List<PedidoItemDTO> dtoList = fromEntity(page.getContent());
        return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }
}

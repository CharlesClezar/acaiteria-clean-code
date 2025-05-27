package org.example.dto;

import lombok.*;
import org.example.model.PedidoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PedidoItemDTO {

    private Long id;
    private String descricaoItem;
    private Double valorVenda;
    private Integer quantidade;

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
                .toList(); // Substitui Collectors.toList()
    }

    // Conversão de página de entidades
    public static Page<PedidoItemDTO> fromEntity(Page<PedidoItem> page) {
        List<PedidoItemDTO> dtoList = fromEntity(page.getContent());
        return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }
}

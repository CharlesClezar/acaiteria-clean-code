package org.example.dto;

import lombok.*;
import org.example.model.MovimentacaoEstoque;
import org.example.model.TipoMovimentacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MovimentacaoEstoqueDTO {

    private Long id;
    private Integer quantidadeMovimento;
    private TipoMovimentacao tipo;
    private Double valor;

    // Conversão: entidade -> DTO
    public static MovimentacaoEstoqueDTO fromEntity(MovimentacaoEstoque entity) {
        if (entity == null) return null;
        return new MovimentacaoEstoqueDTO(
                entity.getId(),
                entity.getQuantidadeMovimento(),
                entity.getTipo(),
                entity.getValor()
        );
    }

    // Conversão: DTO -> entidade
    public MovimentacaoEstoque toEntity() {
        MovimentacaoEstoque entity = new MovimentacaoEstoque();
        entity.setId(this.id);
        entity.setQuantidadeMovimento(this.quantidadeMovimento);
        entity.setTipo(this.tipo);
        entity.setValor(this.valor);
        return entity;
    }

    // Lista de entidades -> lista de DTOs
    public static List<MovimentacaoEstoqueDTO> fromEntity(List<MovimentacaoEstoque> entities) {
        return entities.stream()
                .map(MovimentacaoEstoqueDTO::fromEntity)
                .toList();
    }

    // Página de entidades -> página de DTOs
    public static Page<MovimentacaoEstoqueDTO> fromEntity(Page<MovimentacaoEstoque> entities) {
        List<MovimentacaoEstoqueDTO> dtoList = fromEntity(entities.getContent());
        return new PageImpl<>(dtoList, entities.getPageable(), entities.getTotalElements());
    }
}

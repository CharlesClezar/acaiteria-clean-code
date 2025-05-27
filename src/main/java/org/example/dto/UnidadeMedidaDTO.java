package org.example.dto;

import org.example.model.UnidadeMedida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class UnidadeMedidaDTO {

    private Long id;
    private String sigla;
    private String descricao;

    // Construtores
    public UnidadeMedidaDTO() {}

    public UnidadeMedidaDTO(Long id, String sigla, String descricao) {
        this.id = id;
        this.sigla = sigla;
        this.descricao = descricao;
    }

    // Getters e Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getSigla() { return sigla; }

    public void setSigla(String sigla) { this.sigla = sigla; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    // Conversão de entidade para DTO
    public static UnidadeMedidaDTO fromEntity(UnidadeMedida unidadeMedida) {
        if (unidadeMedida == null) return null;
        return new UnidadeMedidaDTO(
                unidadeMedida.getId(),
                unidadeMedida.getSigla(),
                unidadeMedida.getDescricao()
        );
    }

    // Conversão de DTO para entidade
    public UnidadeMedida toEntity() {
        UnidadeMedida entity = new UnidadeMedida();
        entity.setId(this.id);
        entity.setSigla(this.sigla);
        entity.setDescricao(this.descricao);
        return entity;
    }

    // Lista de entidades para lista de DTOs
    public static List<UnidadeMedidaDTO> fromEntity(List<UnidadeMedida> entities) {
        return entities.stream()
                .map(UnidadeMedidaDTO::fromEntity)
                .toList();
    }

    // Página de entidades para página de DTOs
    public static Page<UnidadeMedidaDTO> fromEntity(Page<UnidadeMedida> entities) {
        List<UnidadeMedidaDTO> dtoList = fromEntity(entities.getContent());
        return new PageImpl<>(dtoList, entities.getPageable(), entities.getTotalElements());
    }
}

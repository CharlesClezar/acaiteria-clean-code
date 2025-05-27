package org.example.dto;

import org.example.model.MovimentacaoEstoque;
import org.example.model.TipoMovimentacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class MovimentacaoEstoqueDTO {

    private Long id;
    private Integer quantidadeMovimento;
    private TipoMovimentacao tipo;
    private Double valor;

    // Construtores
    public MovimentacaoEstoqueDTO() {}

    public MovimentacaoEstoqueDTO(Long id, Integer quantidadeMovimento, TipoMovimentacao tipo, Double valor) {
        this.id = id;
        this.quantidadeMovimento = quantidadeMovimento;
        this.tipo = tipo;
        this.valor = valor;
    }

    // Getters e Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Integer getQuantidadeMovimento() { return quantidadeMovimento; }

    public void setQuantidadeMovimento(Integer quantidadeMovimento) { this.quantidadeMovimento = quantidadeMovimento; }

    public TipoMovimentacao getTipo() { return tipo; }

    public void setTipo(TipoMovimentacao tipo) { this.tipo = tipo; }

    public Double getValor() { return valor; }

    public void setValor(Double valor) { this.valor = valor; }

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
                .collect(Collectors.toList());
    }

    // Página de entidades -> página de DTOs
    public static Page<MovimentacaoEstoqueDTO> fromEntity(Page<MovimentacaoEstoque> entities) {
        List<MovimentacaoEstoqueDTO> dtoList = fromEntity(entities.getContent());
        return new PageImpl<>(dtoList, entities.getPageable(), entities.getTotalElements());
    }
}

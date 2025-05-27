package org.example.dto;

import org.example.model.Item;
import org.example.model.TipoItem;
import org.example.model.UnidadeMedida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ItemDTO {
    private Long id;
    private UnidadeMedida unidadeMedida;
    private String descricao;
    private Integer quantidadeEstoque;
    private Double precoCompra;
    private Double precoVenda;
    private String imagem;
    private TipoItem filtro;
    private Boolean ativo;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UnidadeMedida getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(UnidadeMedida unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

    public Double getPrecoCompra() { return precoCompra; }
    public void setPrecoCompra(Double precoCompra) { this.precoCompra = precoCompra; }

    public Double getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(Double precoVenda) { this.precoVenda = precoVenda; }

    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }

    public TipoItem getFiltro() { return filtro; }
    public void setFiltro(TipoItem filtro) { this.filtro = filtro; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    // Conversores
    public static ItemDTO fromEntity(Item item) {
        if (item == null) return null;
        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setUnidadeMedida(item.getUnidadeMedida());
        dto.setDescricao(item.getDescricao());
        dto.setQuantidadeEstoque(item.getQuantidadeEstoque());
        dto.setPrecoCompra(item.getPrecoCompra());
        dto.setPrecoVenda(item.getPrecoVenda());
        dto.setImagem(item.getImagem());
        dto.setFiltro(item.getFiltro());
        dto.setAtivo(item.getAtivo());
        return dto;
    }

    public Item toEntity() {
        Item item = new Item();
        item.setId(this.id);
        item.setUnidadeMedida(this.unidadeMedida);
        item.setDescricao(this.descricao);
        item.setQuantidadeEstoque(this.quantidadeEstoque);
        item.setPrecoCompra(this.precoCompra);
        item.setPrecoVenda(this.precoVenda);
        item.setImagem(this.imagem);
        item.setFiltro(this.filtro);
        item.setAtivo(this.ativo);
        return item;
    }

    public static List<ItemDTO> fromEntity(List<Item> items) {
        return items.stream().map(ItemDTO::fromEntity).collect(Collectors.toList());
    }

    public static Page<ItemDTO> fromEntity(Page<Item> items) {
        List<ItemDTO> dtos = items.stream().map(ItemDTO::fromEntity).collect(Collectors.toList());
        return new PageImpl<>(dtos, items.getPageable(), items.getTotalElements());
    }
}

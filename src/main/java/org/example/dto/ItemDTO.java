package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Item;
import org.example.model.TipoItem;
import org.example.model.UnidadeMedida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
        return items.stream().map(ItemDTO::fromEntity).toList();
    }

    public static Page<ItemDTO> fromEntity(Page<Item> items) {
        List<ItemDTO> dtos = items.stream().map(ItemDTO::fromEntity).toList();
        return new PageImpl<>(dtos, items.getPageable(), items.getTotalElements());
    }
}

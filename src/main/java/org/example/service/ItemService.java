package org.example.service;

import org.example.model.Item;
import org.example.model.QItem;
import org.example.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository repository;
    private final ModelMapper modelMapper;

    public ItemService(ItemRepository repository,
                       ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public Item salvarItem(Item entity) {
        if (entity == null) {
            throw new ValidationException("Item inválido.");
        }

        verificarItemDuplicado(entity.getDescricao(), 0L);

        if (entity.getPrecoCompra() > entity.getPrecoVenda()) {
            throw new ValidationException("O preço de compra não pode ser maior que o de venda.");
        }

        return repository.save(entity);
    }

    public Page<Item> buscarTodosItensPaginado(String filter, Pageable pageable) {
        return repository.findAll(filter, Item.class, pageable);
    }

    public List<Item> buscarTodosItens(String filter) {
        return repository.findAll(filter, Item.class);
    }

    public Item buscarItemPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item com ID " + id + " não encontrado."));
    }

    public Item editarItem(Long id, Item entity) {
        if (entity == null) {
            throw new ValidationException("Item inválido.");
        }

        Item existente = buscarItemPorId(id); // Lança NotFoundException se não encontrar
        verificarItemDuplicado(entity.getDescricao(), id);

        modelMapper.map(entity, existente);
        return repository.save(existente);
    }

    public void deletarItem(Long id) {
        repository.deleteById(id);
    }

    private void verificarItemDuplicado(String descricao, Long id) {
          if (repository.exists(QItem.item.id.ne(id).and(QItem.item.descricao.eq(descricao)))) {
            throw new ValidationException("Já existe um item com a descrição '" + descricao + "'.");
        }
    }
}

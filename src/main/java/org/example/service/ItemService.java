package org.example.service;

import org.example.model.Item;
import org.example.model.QItem;
import org.example.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository repository;
    private final UnidadeMedidaService unidadeMedidaService;
    private final ModelMapper modelMapper;

    public ItemService(ItemRepository repository,
                       UnidadeMedidaService unidadeMedidaService,
                       ModelMapper modelMapper) {
        this.repository = repository;
        this.unidadeMedidaService = unidadeMedidaService;
        this.modelMapper = modelMapper;
    }

    public Item salvar(Item entity) {
        if (entity == null) throw new ValidationException("Item inválido.");

        validarDuplicidade(entity.getDescricao(), 0L);

        if (entity.getPrecoCompra() > entity.getPrecoVenda()) {
            throw new ValidationException("O preço de compra não pode ser maior que o de venda.");
        }

        return repository.save(entity);
    }

    public Page<Item> buscaTodos(String filter, Pageable pageable) {
        return repository.findAll(filter, Item.class, pageable);
    }

    public List<Item> buscaTodos(String filter) {
        return repository.findAll(filter, Item.class);
    }

    public Item buscaPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Item não encontrado."));
    }

    public Item alterar(Long id, Item entity) {
        if (entity == null) throw new ValidationException("Item inválido.");

        Item existente = buscaPorId(id); // já lança exceção se não encontrar
        validarDuplicidade(entity.getDescricao(), id);

        modelMapper.map(entity, existente);
        return repository.save(existente);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }

    private void validarDuplicidade(String descricao, Long id) {
        boolean existe = repository.exists(QItem.item.id.ne(id).and(QItem.item.descricao.eq(descricao)));
        if (existe) {
            throw new ValidationException("Já existe um item com essa descrição.");
        }
    }
}

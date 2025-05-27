package org.example.service;

import org.example.model.Item;
import org.example.model.PedidoItem;
import org.example.model.TipoMovimentacao;
import org.example.repository.PedidoItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoItemService {

    private final PedidoItemRepository repository;
    private final ModelMapper modelMapper;
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;
    private final ItemService itemService;

    public PedidoItemService(PedidoItemRepository repository,
                             ModelMapper modelMapper,
                             MovimentacaoEstoqueService movimentacaoEstoqueService,
                             ItemService itemService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.movimentacaoEstoqueService = movimentacaoEstoqueService;
        this.itemService = itemService;
    }

    public PedidoItem salvar(PedidoItem entity) {
        validarEstoqueEAtualizar(entity);
        movimentacaoEstoqueService.salvarMovimentacao(
                entity.getItem(),
                entity.getQuantidade(),
                TipoMovimentacao.SAIDA,
                entity.getValorVenda()
        );
        return repository.save(entity);
    }

    public PedidoItem salvarSemMovimentacao(PedidoItem entity) {
        return repository.save(entity);
    }

    public List<PedidoItem> buscaTodos(String filter) {
        return repository.findAll(filter, PedidoItem.class);
    }

    public Page<PedidoItem> buscaTodos(String filter, Pageable pageable) {
        return repository.findAll(filter, PedidoItem.class, pageable);
    }

    public PedidoItem buscaPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item do pedido não encontrado!"));
    }

    public PedidoItem alterar(Long id, PedidoItem entity) {
        PedidoItem existente = buscaPorId(id);
        modelMapper.map(entity, existente);
        return repository.save(existente);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }

    private void validarEstoqueEAtualizar(PedidoItem entity) {
        Item item = itemService.buscaPorId(entity.getItem().getId());
        int novoEstoque = item.getQuantidadeEstoque() - entity.getQuantidade();
        if (novoEstoque < 0) {
            throw new ValidationException("Quantidade solicitada é maior do que o estoque disponível!");
        }
        item.setQuantidadeEstoque(novoEstoque);
        itemService.alterar(item.getId(), item);
    }
}

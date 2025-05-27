package org.example.service;

import org.example.model.Item;
import org.example.model.MovimentacaoEstoque;
import org.example.model.TipoMovimentacao;
import org.example.repository.MovimentacaoEstoqueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentacaoEstoqueService {

    private final MovimentacaoEstoqueRepository repository;
    private final ModelMapper modelMapper;

    public MovimentacaoEstoqueService(MovimentacaoEstoqueRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public MovimentacaoEstoque salvar(MovimentacaoEstoque entity) {
        return repository.save(entity);
    }

    public List<MovimentacaoEstoque> buscaTodos(String filter) {
        return repository.findAll(filter, MovimentacaoEstoque.class);
    }

    public Page<MovimentacaoEstoque> buscaTodos(String filter, Pageable pageable) {
        return repository.findAll(filter, MovimentacaoEstoque.class, pageable);
    }

    public MovimentacaoEstoque buscaPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movimentação de estoque não encontrada!"));
    }

    public MovimentacaoEstoque alterar(Long id, MovimentacaoEstoque entity) {
        MovimentacaoEstoque existente = buscaPorId(id);
        modelMapper.map(entity, existente);
        return repository.save(existente);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }

    public void salvarMovimentacao(Item item, Integer quantidade, TipoMovimentacao tipo, Double valor) {
        validarQuantidade(quantidade);

        MovimentacaoEstoque mov = new MovimentacaoEstoque();
        mov.setItem(item);
        mov.setQuantidadeMovimento(quantidade);
        mov.setDataHora(LocalDateTime.now());
        mov.setTipo(tipo);
        mov.setValor(valor);

        salvar(mov);
    }

    private void validarQuantidade(Integer quantidade) {
        if (quantidade == null || quantidade < 0) {
            throw new ValidationException("Quantidade não pode ser menor que zero!");
        }
    }
}

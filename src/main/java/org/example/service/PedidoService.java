package org.example.service;

import org.example.model.Pedido;
import org.example.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ModelMapper modelMapper;

    public PedidoService(PedidoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public Pedido salvar(Pedido entity) {
        if (entity == null) {
            throw new ValidationException("Pedido inválido.");
        }
        return repository.save(entity);
    }

    public List<Pedido> buscaTodos(String filter) {
        return repository.findAll(filter, Pedido.class);
    }

    public Page<Pedido> buscaTodos(String filter, Pageable pageable) {
        return repository.findAll(filter, Pedido.class, pageable);
    }

    public Pedido buscaPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado!"));
    }

    public Pedido alterar(Long id, Pedido entity) {
        if (entity == null) {
            throw new ValidationException("Pedido inválido.");
        }
        Pedido existente = buscaPorId(id);
        modelMapper.map(entity, existente);
        return repository.save(existente);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }
}

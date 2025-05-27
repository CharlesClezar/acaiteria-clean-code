package org.example.service;

import org.example.model.QUnidadeMedida;
import org.example.model.UnidadeMedida;
import org.example.repository.UnidadeMedidaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeMedidaService {

    private final UnidadeMedidaRepository repository;
    private final ModelMapper modelMapper;

    public UnidadeMedidaService(UnidadeMedidaRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public UnidadeMedida salvarUnidadeMedida(UnidadeMedida entity) {
        if (entity == null || entity.getSigla() == null) {
            throw new ValidationException("Unidade de medida inválida.");
        }

        validarDuplicidadeSigla(entity.getSigla(), 0L);
        return repository.save(entity);
    }

    public List<UnidadeMedida> buscarTodasUnidadesMedida(String filter) {
        return repository.findAll(filter, UnidadeMedida.class);
    }

    public Page<UnidadeMedida> buscarTodasUnidadesMedidaPaginada(String filter, Pageable pageable) {
        return repository.findAll(filter, UnidadeMedida.class, pageable);
    }

    public UnidadeMedida buscarUnidadeMedidaPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Unidade de medida não encontrada!"));
    }

    public UnidadeMedida editarUnidadeMedida(Long id, UnidadeMedida entity) {
        if (entity == null || entity.getSigla() == null) {
            throw new ValidationException("Unidade de medida inválida.");
        }

        UnidadeMedida existente = buscarUnidadeMedidaPorId(id);
        validarDuplicidadeSigla(entity.getSigla(), id);

        modelMapper.map(entity, existente);
        return repository.save(existente);
    }

    public void deletarUnidadeMedida(Long id) {
        repository.deleteById(id);
    }

    private void validarDuplicidadeSigla(String sigla, Long id) {
        boolean existe = repository.exists(
                QUnidadeMedida.unidadeMedida.id.ne(id)
                        .and(QUnidadeMedida.unidadeMedida.sigla.eq(sigla))
        );
        if (existe) {
            throw new ValidationException("Sigla já existente no sistema!");
        }
    }
}

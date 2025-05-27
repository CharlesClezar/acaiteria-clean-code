package org.example.service;

import org.example.model.QUsuario;
import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public Usuario salvar(Usuario entity) {
        if (entity == null || entity.getLogin() == null) {
            throw new ValidationException("Usuário inválido.");
        }

        validarDuplicidadeLogin(entity.getLogin(), 0L);
        return repository.save(entity);
    }

    public List<Usuario> buscaTodos(String filter) {
        return repository.findAll(filter, Usuario.class);
    }

    public Page<Usuario> buscaTodos(String filter, Pageable pageable) {
        return repository.findAll(filter, Usuario.class, pageable);
    }

    public Usuario buscaPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
    }

    public Usuario alterar(Long id, Usuario entity) {
        if (entity == null || entity.getLogin() == null) {
            throw new ValidationException("Usuário inválido.");
        }

        Usuario existente = buscaPorId(id);
        validarDuplicidadeLogin(entity.getLogin(), id);
        modelMapper.map(entity, existente);
        return repository.save(existente);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }

    public Usuario findByUserAndPassword(String login, String senha) {
        return repository.findByLogin(login)
                .filter(usuario -> usuario.getSenha().equals(senha)) // ⚠️ substitua por BCrypt em produção!
                .orElse(null);
    }

    private void validarDuplicidadeLogin(String login, Long id) {
        boolean existe = repository.exists(
                QUsuario.usuario.id.ne(id)
                        .and(QUsuario.usuario.login.eq(login))
        );
        if (existe) {
            throw new ValidationException("Login já existente no sistema!");
        }
    }
}

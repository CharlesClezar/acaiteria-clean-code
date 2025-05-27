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

    public Usuario salvarUsuario(Usuario entity) {
        if (entity == null || entity.getLogin() == null) {
            throw new ValidationException("Usuário inválido.");
        }

        validarDuplicidadeLogin(entity.getLogin(), 0L);
        return repository.save(entity);
    }

    public List<Usuario> buscarTodosUsuarios(String filter) {
        return repository.findAll(filter, Usuario.class);
    }

    public Page<Usuario> buscarTodosUsuariosPaginado(String filter, Pageable pageable) {
        return repository.findAll(filter, Usuario.class, pageable);
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
    }

    public Usuario editarUsuario(Long id, Usuario entity) {
        if (entity == null || entity.getLogin() == null) {
            throw new ValidationException("Usuário inválido.");
        }

        Usuario existente = buscarUsuarioPorId(id);
        validarDuplicidadeLogin(entity.getLogin(), id);
        modelMapper.map(entity, existente);
        return repository.save(existente);
    }

    public void deletarUsuario(Long id) {
        repository.deleteById(id);
    }

    public Usuario findByUserAndPassword(String login, String senha) {
        return repository.findByLogin(login)
                .filter(usuario -> usuario.getSenha().equals(senha)) // ⚠️ substitua por BCrypt em produção!
                .orElse(null);
    }

    private void validarDuplicidadeLogin(String login, Long id) {
        if (repository.exists(QUsuario.usuario.id.ne(id).and(QUsuario.usuario.login.eq(login)))) {
            throw new ValidationException("Login já existente no sistema!");
        }
    }
}

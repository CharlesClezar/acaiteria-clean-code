package org.example.controller;

import org.example.dto.UsuarioCadastroDTO;
import org.example.dto.UsuarioDTO;
import org.example.dto.LoginDTO;
import org.example.model.Usuario;
import org.example.service.NotFoundException;
import org.example.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "http://localhost:3000")
public class UsuarioController extends AbstractController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCadastroDTO dto) {
        Usuario entity = dto.toEntity();
        Usuario saved = service.salvar(entity);
        return ResponseEntity.created(URI.create("/api/usuario/" + saved.getId()))
                .body(UsuarioDTO.fromEntity(saved));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody @Valid LoginDTO login) {
        Usuario usuario = service.findByUserAndPassword(login.getLogin(), login.getSenha());
        if (usuario != null) {
            return ResponseEntity.ok(UsuarioDTO.fromEntity(usuario));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> findAll(@RequestParam(required = false) String filter,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "15") int size) {
        Page<Usuario> usuarios = service.buscaTodos(filter, PageRequest.of(page, size));
        return ResponseEntity.ok(UsuarioDTO.fromEntity(usuarios));
    }

    @GetMapping("{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable("id") Long id) {
        Usuario usuario = service.buscaPorId(id);
        return ResponseEntity.ok(UsuarioDTO.fromEntity(usuario));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable("id") Long id,
                                             @RequestBody @Valid UsuarioCadastroDTO dto) {
        try {
            Usuario entity = dto.toEntity();
            Usuario alterado = service.alterar(id, entity);
            return ResponseEntity.ok(UsuarioDTO.fromEntity(alterado));
        } catch (NotFoundException nfe) {
            return ResponseEntity.noContent().build();
        }
    }
}

package org.example.controller;

import org.example.dto.MovimentacaoEstoqueDTO;
import org.example.model.MovimentacaoEstoque;
import org.example.service.MovimentacaoEstoqueService;
import org.example.service.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/movimentacaoEstoque")
@CrossOrigin(origins = "http://localhost:3000")
public class MovimentacaoEstoqueController extends AbstractController {

    @Autowired
    private MovimentacaoEstoqueService service;

    @PostMapping
    public ResponseEntity<MovimentacaoEstoqueDTO> create(@RequestBody @Valid MovimentacaoEstoqueDTO dto) {
        MovimentacaoEstoque entity = dto.toEntity();
        MovimentacaoEstoque save = service.salvar(entity);
        return ResponseEntity.created(URI.create("/api/movimentacaoEstoque/" + save.getId()))
                .body(MovimentacaoEstoqueDTO.fromEntity(save));
    }

    @GetMapping
    public ResponseEntity<Page<MovimentacaoEstoqueDTO>> findAll(@RequestParam(required = false) String filter,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "15") int size) {
        Page<MovimentacaoEstoque> movimentacoes = service.buscaTodos(filter, PageRequest.of(page, size));
        Page<MovimentacaoEstoqueDTO> dtos = MovimentacaoEstoqueDTO.fromEntity(movimentacoes);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<MovimentacaoEstoqueDTO> findById(@PathVariable("id") Long id) {
        MovimentacaoEstoque entity = service.buscaPorId(id);
        return ResponseEntity.ok(MovimentacaoEstoqueDTO.fromEntity(entity));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<MovimentacaoEstoqueDTO> update(@PathVariable("id") Long id,
                                                         @RequestBody @Valid MovimentacaoEstoqueDTO dto) {
        try {
            MovimentacaoEstoque entity = dto.toEntity();
            MovimentacaoEstoque alterado = service.alterar(id, entity);
            return ResponseEntity.ok(MovimentacaoEstoqueDTO.fromEntity(alterado));
        } catch (NotFoundException nfe) {
            return ResponseEntity.noContent().build();
        }
    }
}

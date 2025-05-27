package org.example.controller;

import org.example.dto.UnidadeMedidaDTO;
import org.example.model.UnidadeMedida;
import org.example.service.NotFoundException;
import org.example.service.UnidadeMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/unidadeMedida")
@CrossOrigin(origins = "http://localhost:3000")
public class UnidadeMedidaController extends AbstractController {

    @Autowired
    private UnidadeMedidaService service;

    @PostMapping
    public ResponseEntity<UnidadeMedidaDTO> create(@RequestBody @Valid UnidadeMedidaDTO dto) {
        UnidadeMedida entity = dto.toEntity();
        UnidadeMedida saved = service.salvar(entity);
        return ResponseEntity.created(URI.create("/api/unidadeMedida/" + saved.getId()))
                .body(UnidadeMedidaDTO.fromEntity(saved));
    }

    @GetMapping
    public ResponseEntity<Page<UnidadeMedidaDTO>> findAll(@RequestParam(required = false) String filter,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "15") int size) {
        Page<UnidadeMedida> unidades = service.buscaTodos(filter, PageRequest.of(page, size));
        return ResponseEntity.ok(UnidadeMedidaDTO.fromEntity(unidades));
    }

    @GetMapping("{id}")
    public ResponseEntity<UnidadeMedidaDTO> findById(@PathVariable("id") Long id) {
        UnidadeMedida unidade = service.buscaPorId(id);
        return ResponseEntity.ok(UnidadeMedidaDTO.fromEntity(unidade));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<UnidadeMedidaDTO> update(@PathVariable("id") Long id,
                                                   @RequestBody @Valid UnidadeMedidaDTO dto) {
        try {
            UnidadeMedida entity = dto.toEntity();
            UnidadeMedida alterado = service.alterar(id, entity);
            return ResponseEntity.ok(UnidadeMedidaDTO.fromEntity(alterado));
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }
}

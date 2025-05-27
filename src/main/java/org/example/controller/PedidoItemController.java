package org.example.controller;

import org.example.dto.PedidoItemDTO;
import org.example.model.PedidoItem;
import org.example.service.NotFoundException;
import org.example.service.PedidoItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/pedidoItem")
@CrossOrigin(origins = "http://localhost:3000")
public class PedidoItemController extends AbstractController {

    private final PedidoItemService service;

    public PedidoItemController(PedidoItemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PedidoItemDTO> create(@RequestBody @Valid PedidoItemDTO dto) {
        PedidoItem entity = dto.toEntity();
        PedidoItem save = service.salvar(entity);
        return ResponseEntity.created(URI.create("/api/pedidoItem/" + save.getId()))
                .body(PedidoItemDTO.fromEntity(save));
    }

    @GetMapping
    public ResponseEntity<Page<PedidoItemDTO>> findAll(@RequestParam(required = false) String filter,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "15") int size) {
        Page<PedidoItem> pedidoItems = service.buscaTodos(filter, PageRequest.of(page, size));
        Page<PedidoItemDTO> dtos = PedidoItemDTO.fromEntity(pedidoItems);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<PedidoItemDTO> findById(@PathVariable("id") Long id) {
        PedidoItem pedidoItem = service.buscaPorId(id);
        return ResponseEntity.ok(PedidoItemDTO.fromEntity(pedidoItem));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<PedidoItemDTO> update(@PathVariable("id") Long id,
                                                @RequestBody @Valid PedidoItemDTO dto) {
        try {
            PedidoItem entity = dto.toEntity();
            PedidoItem alterado = service.alterar(id, entity);
            return ResponseEntity.ok(PedidoItemDTO.fromEntity(alterado));
        } catch (NotFoundException nfe) {
            return ResponseEntity.notFound().build();
        }
    }
}

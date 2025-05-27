package org.example.controller;

import org.example.dto.ItemDTO;
import org.example.model.Item;
import org.example.model.TipoMovimentacao;
import org.example.service.ItemService;
import org.example.service.MovimentacaoEstoqueService;
import org.example.service.NotFoundException;
import org.example.service.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/item")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController extends AbstractController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private MovimentacaoEstoqueService movimentacaoEstoqueService;

    @PostMapping
    public ResponseEntity<ItemDTO> create(@RequestBody @Valid ItemDTO itemDTO) throws ValidationException {
        Item entity = itemDTO.toEntity();
        Item saved = itemService.salvar(entity);
        movimentacaoEstoqueService.salvarMovimentacao(
                saved,
                saved.getQuantidadeEstoque(),
                TipoMovimentacao.ENTRADA,
                saved.getPrecoCompra() * saved.getQuantidadeEstoque()
        );
        return ResponseEntity.created(URI.create("/api/item/" + saved.getId()))
                .body(ItemDTO.fromEntity(saved));
    }

    @GetMapping
    public ResponseEntity<Page<ItemDTO>> findAll(@RequestParam(required = false) String filter,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "15") int size) {
        Page<Item> itens = itemService.buscaTodos(filter, PageRequest.of(page, size));
        Page<ItemDTO> itemDTOS = ItemDTO.fromEntity(itens);
        return ResponseEntity.ok(itemDTOS);
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemDTO> findById(@PathVariable("id") Long id) {
        Item item = itemService.buscaPorId(id);
        return ResponseEntity.ok(ItemDTO.fromEntity(item));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        itemService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<ItemDTO> update(@PathVariable("id") Long id, @RequestBody @Valid ItemDTO itemDTO) {
        try {
            Integer quantidadeAnterior = itemService.buscaPorId(id).getQuantidadeEstoque();
            Item entity = itemDTO.toEntity();
            Item alterado = itemService.alterar(id, entity);

            if (!quantidadeAnterior.equals(alterado.getQuantidadeEstoque())) {
                Integer diferenca = alterado.getQuantidadeEstoque() - quantidadeAnterior;
                Double valor = alterado.getPrecoCompra() * Math.abs(diferenca);
                movimentacaoEstoqueService.salvarMovimentacao(
                        alterado,
                        diferenca,
                        diferenca > 0 ? TipoMovimentacao.ENTRADA : TipoMovimentacao.SAIDA,
                        valor
                );
            }

            return ResponseEntity.ok(ItemDTO.fromEntity(alterado));
        } catch (NotFoundException nfe) {
            return ResponseEntity.notFound().build();
        } catch (ValidationException ve) {
            throw new ValidationException("Não é possível adicionar menos itens que a quantidade atual do estoque.");
        }
    }
}

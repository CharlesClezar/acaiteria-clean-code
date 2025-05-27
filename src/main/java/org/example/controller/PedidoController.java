package org.example.controller;

import org.example.dto.PedidoDTO;
import org.example.model.Item;
import org.example.model.Pedido;
import org.example.model.PedidoItem;
import org.example.model.TipoMovimentacao;
import org.example.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin(origins = "http://localhost:3000")
public class PedidoController extends AbstractController {

    private final PedidoService pedidoService;
    private final PedidoItemService pedidoItemService;
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;
    private final ItemService itemService;

    public PedidoController(PedidoService pedidoService,
                            PedidoItemService pedidoItemService,
                            MovimentacaoEstoqueService movimentacaoEstoqueService,
                            ItemService itemService) {
        this.pedidoService = pedidoService;
        this.pedidoItemService = pedidoItemService;
        this.movimentacaoEstoqueService = movimentacaoEstoqueService;
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> create(@RequestBody @Valid PedidoDTO pedidoDTO) {
        Pedido entity = pedidoDTO.toEntity();
        Pedido save = pedidoService.salvar(entity);

        for (PedidoItem pedidoItem : entity.getPedidoItens()) {
            pedidoItem.setPedido(save);
            pedidoItemService.salvarSemMovimentacao(pedidoItem);

            Long itemId = pedidoItem.getItem().getId();
            Item item = itemService.buscaPorId(itemId);

            Integer quantidadePedido = pedidoItem.getQuantidade();
            item.setQuantidadeEstoque(item.getQuantidadeEstoque() - quantidadePedido);
            itemService.alterar(item.getId(), item);

            movimentacaoEstoqueService.salvarMovimentacao(
                    pedidoItem.getItem(),
                    pedidoItem.getQuantidade(),
                    TipoMovimentacao.SAIDA,
                    pedidoItem.getValorVenda()
            );
        }

        return ResponseEntity
                .created(URI.create("/api/pedido/" + save.getId()))
                .body(PedidoDTO.fromEntity(save));
    }

    @GetMapping
    public ResponseEntity<Page<PedidoDTO>> findAll(@RequestParam(required = false) String filter,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "15") int size) {
        Page<Pedido> pedidos = pedidoService.buscaTodos(filter, PageRequest.of(page, size));
        Page<PedidoDTO> pedidoDTOS = PedidoDTO.fromEntity(pedidos);
        return ResponseEntity.ok(pedidoDTOS);
    }

    @GetMapping("{id}")
    public ResponseEntity<PedidoDTO> findById(@PathVariable("id") Long id) {
        Pedido pedido = pedidoService.buscaPorId(id);
        return ResponseEntity.ok(PedidoDTO.fromEntity(pedido));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        pedidoService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<PedidoDTO> update(@PathVariable("id") Long id, @RequestBody @Valid PedidoDTO pedidoDTO) {
        try {
            Pedido entity = pedidoDTO.toEntity();
            Pedido alterado = pedidoService.alterar(id, entity);
            return ResponseEntity.ok(PedidoDTO.fromEntity(alterado));
        } catch (NotFoundException nfe) {
            return ResponseEntity.notFound().build();
        }
    }
}

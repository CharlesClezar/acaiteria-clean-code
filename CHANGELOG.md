# ChangeLog - Projeto Acaiteria Clean Code

## [2025-05-26] Refatorações estruturais e correções SonarQube

### ✅ Refatoração de Controllers
- Aplicada conversão de entidades JPA para DTOs em todos os Controllers:
  - `ItemController`
  - `PedidoController`
  - `PedidoItemController`
  - `UnidadeMedidaController`
  - `MovimentacaoEstoqueController`
  - `UsuarioController`
- Removido uso direto de entidades nos endpoints públicos (`@RequestBody` e `@ResponseBody`).
- Corrigido problema de segurança reportado pelo Sonar: `Replace this persistent entity with a DTO object`.
- Corrigido uso de `ResponseEntity` com `Void`, `ok()`, `noContent()` e `notFound()` corretamente aplicados.

### ✅ DTOs ajustados
- DTOs atualizados para conter apenas dados simples e seguros (sem entidades aninhadas):
  - `ItemDTO`
  - `PedidoDTO`
  - `PedidoItemDTO`
  - `UnidadeMedidaDTO`
  - `MovimentacaoEstoqueDTO`
  - `UsuarioDTO`, `UsuarioCadastroDTO`, `LoginDTO`
- `toEntity()` e `fromEntity()` padronizados e seguros.
- Campo `senha` removido dos DTOs de resposta (`UsuarioDTO`).

### ✅ Refatoração de Services
- Todos os serviços refatorados para:
  - Utilizar injeção de dependência via **construtor** ao invés de `@Autowired` em campo.
  - Eliminar `findById(...).orElse(null)` → substituído por `orElseThrow(...)` com `NotFoundException`.
  - Melhorar nomenclaturas e clareza semântica (`validarDuplicidadeLogin`, `validarDuplicidadeSigla`, etc.).
  - Adicionar validação explícita de entrada (`null` ou campos obrigatórios).
- `PedidoItemService`: método `salvarByPedido(...)` renomeado para `salvarSemMovimentacao(...)` para melhor expressividade.
- `UsuarioService`: refatorado para evitar injeção duplicada e comparação de senha por `equals()`, com recomendação para uso de `BCrypt`.







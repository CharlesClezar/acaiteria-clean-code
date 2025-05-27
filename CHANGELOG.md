# 📋 ChangeLog

## Refatorações 

### ✅ Segurança e Boas Práticas
- Substituído uso de entidades JPA diretamente nos controllers por DTOs em todas as operações `@RequestBody` e `@ResponseBody`.
- Implementada injeção de dependência por **construtor** em todas as classes de serviço e controllers, removendo `@Autowired` de campos (Code Smell resolvido).
- Removido o uso de `WebMvcConfigurerAdapter`, substituído por `WebMvcConfigurer` conforme a especificação atual do Spring.

### ✅ Estrutura e Manutenibilidade
- Separação clara entre camadas: `Controller`, `Service`, `DTO`, `Repository` e `Model`.
- DTOs simplificados e sem acoplamento com entidades ou estruturas complexas.
- Conversores adicionados em todos os DTOs (`fromEntity` / `toEntity`), com tratamento de `null` e construtores explícitos.
- Código de tratamento para exceções `NotFoundException` e `ValidationException` padronizado em todos os serviços.

### ✅ Refatorações baseadas no SonarQube
- Corrigidos problemas de **cobertura nula** com mensagens explícitas.
- Redução de complexidade cognitiva em métodos grandes.
- Remoção de imports não utilizados.
- Simplificações aplicadas (ex: `stream().collect(Collectors.toList())` → `stream().toList()`).
- Tratamento de exceções duplicadas unificado.
- Classes utilitárias com construtor privado para evitar instanciação incorreta.

### ✅ Integração com Linter
- O projeto foi analisado com **SonarQube (via Docker)** para detectar duplicações, code smells, ausência de cobertura e problemas de boas práticas.
- Diversas sugestões do Sonar foram aplicadas, incluindo:
  - Substituição de `@Autowired` por injeção via construtor;
  - Redução da complexidade de métodos;
  - Remoção de imports e blocos de código não utilizados;
  - Melhoria da segurança e clareza dos DTOs.

### ✅ Organização e Qualidade de Código
- Padronização de nome de métodos, parâmetros e campos em camelCase.
- Mensagens de erro e validação reescritas com clareza.
- Atualização do `pom.xml` com versões estáveis e correção de dependências ausentes.
- Reorganização dos `@JoinColumn`, `@Cascade` e mapeamentos JPA redundantes.
- As mudanças a seguir foram motivadas pelos princípios do **Clean Code**, que prioriza a clareza, legibilidade e fácil manutenção do código. Nomes de métodos agora refletem de forma mais precisa suas responsabilidades e contexto, facilitando a leitura e reduzindo ambiguidade. Essa padronização melhora a colaboração em equipe e reduz a curva de aprendizado de novos desenvolvedores no projeto.

#### `ItemService`
| Antes                 | Depois                     |
|----------------------|----------------------------|
| salvar               | salvarItem                 |
| buscaTodos           | buscarTodosItensPaginado   |
| buscaTodos           | buscarTodosItens           |
| buscaPorId           | buscarItemPorId            |
| alterar              | editarItem                 |
| remover              | deletarItem                |
| validarDuplicidade   | verificarItemDuplicado     |

#### `MovimentacaoEstoqueService`
| Antes                 | Depois                          |
|----------------------|----------------------------------|
| salvar               | salvarMovimentacao               |
| salvarMovimentacao   | registrarMovimentacao            |
| buscaTodos           | buscarTodasMovimentacoes         |
| buscaTodos           | buscarTodasMovimentacoesPaginada |
| buscaPorId           | buscarMovimentacaoPorId          |
| alterar              | editarMovimentacao               |
| remover              | deletarMovimentacao              |
| validarQuantidade    | validarQuantidadePositiva        |

#### `PedidoItemService`
| Antes       | Depois                        |
|------------|-------------------------------|
| buscaTodos | buscarTodosPedidosItem        |
| buscaTodos | buscarTodosPedidosItemPaginado|
| buscaPorId | buscarPedidoItemPorId         |
| alterar    | editarPedidoItem              |
| remover    | deletarPedidoItem             |

#### `PedidoService`
| Antes       | Depois                     |
|------------|----------------------------|
| salvar     | salvarPedido               |
| buscaTodos | buscarTodosPedidos         |
| buscaTodos | buscarTodosPedidosPaginado |
| buscaPorId | buscarPedidoPorId          |
| alterar    | editarPedido               |
| remover    | deletarPedido              |

#### `UnidadeMedidaService`
| Antes       | Depois                              |
|------------|--------------------------------------|
| salvar     | salvarUnidadeMedida                 |
| buscaTodos | buscarTodasUnidadesMedida           |
| buscaTodos | buscarTodasUnidadesMedidaPaginada   |
| buscaPorId | buscarUnidadeMedidaPorId            |
| alterar    | editarUnidadeMedida                 |
| remover    | deletarUnidadeMedida                |

#### `UsuarioService`
| Antes       | Depois                   |
|------------|--------------------------|
| salvar     | salvarUsuario            |
| buscaTodos | buscarTodosUsuarios      |
| buscaTodos | buscarTodosUsuariosPaginado |
| alterar    | editarUsuario            |
| remover    | deletarUsuario           |


### ✅ Otimização de Validações Redundantes
- Nas classes `ItemService`, `UsuarioService` e `UnidadeMedidaService`, métodos de validação foram simplificados para eliminar **variáveis temporárias desnecessárias**.
- Exemplo de melhoria aplicada:
```java
// Antes
boolean existe = repository.exists(QUnidadeMedida.unidadeMedida.id.ne(id).and(QUnidadeMedida.unidadeMedida.sigla.eq(sigla)));
if (existe) {
    throw new ValidationException("Sigla já existente no sistema!");
}

// Depois
if (repository.exists(QUnidadeMedida.unidadeMedida.id.ne(id).and(QUnidadeMedida.unidadeMedida.sigla.eq(sigla)))) {
    throw new ValidationException("Sigla já existente no sistema!");
}
```
- Essa alteração reduz ruído no código e segue o princípio do Clean Code de evitar declarações desnecessárias quando não há reutilização da variável.

### ✅ Uso de Lombok para Redução de Boilerplate
- Anotações do **Lombok** foram adicionadas às classes DTOs e modelos, como `@Getter`, `@Setter`, `@NoArgsConstructor` e `@AllArgsConstructor`.
- Essa abordagem elimina a necessidade de escrever manualmente os métodos de acesso, construtores e facilita a manutenção do código.
- Além de reduzir significativamente o tamanho dos arquivos, o uso do Lombok melhora a **produtividade do desenvolvedor** e **foca no domínio da aplicação**, mantendo o código mais limpo e enxuto.
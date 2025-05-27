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

### ✅ Organização e Qualidade de Código
- Padronização de nome de métodos, parâmetros e campos em camelCase.
- Mensagens de erro e validação reescritas com clareza.
- Atualização do `pom.xml` com versões estáveis e correção de dependências ausentes.
- Reorganização dos `@JoinColumn`, `@Cascade` e mapeamentos JPA redundantes.

### ✅ Integração com Linter
- O projeto foi analisado com **SonarQube (via Docker)** para detectar duplicações, code smells, ausência de cobertura e problemas de boas práticas.
- Diversas sugestões do Sonar foram aplicadas, incluindo:
  - Substituição de `@Autowired` por injeção via construtor;
  - Redução da complexidade de métodos;
  - Remoção de imports e blocos de código não utilizados;
  - Melhoria da segurança e clareza dos DTOs.








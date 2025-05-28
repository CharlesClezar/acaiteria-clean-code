# 🧃 Acaiteria ABP Java

Este projeto é uma API desenvolvida em Java com Spring Boot para gerenciamento de pedidos e controle de estoque de uma açaiteria fictícia. O sistema foi construído com foco em boas práticas de codificação, separação de responsabilidades e validações baseadas nas regras de negócio do domínio.

## 📌 Funcionalidades Principais

- Cadastro, edição e remoção de itens, usuários, pedidos, movimentações de estoque e unidades de medida.
- Controle de estoque automatizado com movimentações vinculadas aos pedidos.
- Validação de regras específicas do domínio para evitar inconsistências.
- Paginação e DTOs desacoplados das entidades JPA.
- Integração com SonarQube para análise de qualidade de código.
- Uso de Lombok para redução de boilerplate.

## 🚨 Análise dos Principais Problemas Detectados

Durante a revisão inicial do projeto, foram identificados diversos pontos de melhoria:

- Uso direto de entidades JPA nos controllers, o que comprometia a segurança e o desacoplamento da arquitetura.
- Injeção de dependência via `@Autowired` em campos, considerado code smell.
- Métodos e nomes pouco descritivos e inconsistentes com a semântica do domínio.
- Classes grandes com alta complexidade cognitiva.
- Validações com variáveis temporárias desnecessárias.
- Ausência de testes unitários.

## 🛠️ Estratégia de Refatoração

As ações de refatoração foram guiadas pelos princípios do **Clean Code** e recomendações do **SonarQube**. Entre as principais mudanças, destacam-se:

- Uso de DTOs para entrada e saída de dados.
- Criação de métodos `fromEntity()` e `toEntity()` em todos os DTOs.
- Injeção de dependência por construtor.
- Redução de complexidade e duplicações.
- Padronização da nomenclatura de métodos.
- Eliminação de variáveis intermediárias desnecessárias em validações.
- Separação clara entre camadas (Controller, Service, DTO, Repository, Model).

## 📋 ChangeLog

<<<<<<< HEAD
Todas as alterações realizadas estão detalhadas no arquivo [CHANGELOG.md](./CHANGELOG_FINAL.md).
=======
Todas as alterações realizadas estão detalhadas no arquivo [CHANGELOG.md](./CHANGELOG.md).
>>>>>>> 27c50fe1e7c6764749606d19611ba05584d06189

## ✅ Testes Implementados

Foram adicionados testes unitários para os principais serviços e controllers da aplicação, validando:

- Regras de negócio fundamentais (ex: impedir vendas sem estoque ou em dia não fechado).
- Validações de duplicidade (ex: sigla de unidade já existente).
- Conversão entre DTOs e entidades.
- Fluxos REST simulados com `MockMvc` nos controllers.

Os testes fazem uso da **interface fluente** gerada com `@Builder` do Lombok para tornar os objetos de entrada mais legíveis e claros durante os cenários de teste.

## 💬 Interface Fluente com Lombok

Foi adotado o padrão de **Interface Fluente** nos DTOs usando `@Builder`, especialmente útil em testes. Essa abordagem permite:

- Leitura mais fluida e natural da construção de objetos.
- Cadeia de chamadas encadeadas, reduzindo código verboso.
- Criação explícita de objetos com clareza nos atributos definidos.

Exemplo:
```java
ItemDTO dto = ItemDTO.builder()
    .descricao("Açaí")
    .precoCompra(5.0)
    .precoVenda(10.0)
    .quantidadeEstoque(10)
    .build();
```

## 📈 Análise SonarQube

A qualidade do código foi verificada com **SonarQube**, que avaliou aspectos como segurança, manutenibilidade, confiabilidade e duplicação de código.

![Relatório SonarQube](./sonar.png)

Resumo dos principais resultados:
- **Segurança:** Nenhum problema crítico detectado.
- **Confiabilidade:** Código confiável e estável.
- **Manutenibilidade:** Apenas 9 pontos de melhoria identificados.
- **Cobertura de Testes:** Ainda precisa ser aumentada (0%), mas os testes principais já estão sendo implementados.
- **Duplicações:** Nenhuma duplicação detectada.

Essa análise reforça que a base do projeto está saudável e pronta para evoluções futuras.

## 📦 Instalação e Execução

### Pré-requisitos
- Java 17+
- Maven
- Docker (para rodar SonarQube opcionalmente)

### Passos

```bash
# Clonar o repositório
git clone https://github.com/seu-usuario/acaiteria-abp-java.git
cd acaiteria-abp-java

# Compilar o projeto
mvn clean install

# Rodar a aplicação
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

### Rodar SonarQube (opcional)
```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=acaiteria \
  -Dsonar.projectName="acaiteria" \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=SEU_TOKEN
```

## 📜 Regras de Negócio

- Porcentagem de lucro mínima por item.
- Até 2 acompanhamentos grátis, demais são cobrados R$2,00 cada.
- Não permitir venda sem estoque disponível.
- Não permitir venda se o dia anterior não estiver fechado.
- Não permitir venda parcelada.

---

Desenvolvido com foco em qualidade, manutenibilidade e clareza de código.
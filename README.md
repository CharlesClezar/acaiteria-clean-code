
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

Todas as alterações realizadas estão detalhadas no arquivo [CHANGELOG.md](./CHANGELOG.md).

## ✅ Testes Implementados

Foram adicionados testes unitários para os principais serviços da aplicação, validando:

- Regras de negócio fundamentais (ex: impedir vendas sem estoque ou em dia não fechado).
- Validações de duplicidade (ex: sigla de unidade já existente).
- Conversão entre DTOs e entidades.

## 💬 Interface Fluente

Foi adotado o padrão de **Interface Fluente** em métodos de construção de objetos e builders, proporcionando:

- Leitura mais fluida e natural da criação de objetos complexos.
- Cadeia de chamadas encadeadas, reduzindo código verboso.

Exemplo:
```java
PedidoDTO pedido = new PedidoDTO()
    .comDescricao("Pedido Especial")
    .comItens(itens)
    .comCliente(cliente);
```

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
mvn clean verify sonar:sonar   -Dsonar.projectKey=acaiteria   -Dsonar.projectName="acaiteria"   -Dsonar.host.url=http://localhost:9000   -Dsonar.token=SEU_TOKEN
```

## 📜 Regras de Negócio

- Porcentagem de lucro mínima por item.
- Até 2 acompanhamentos grátis, demais são cobrados R$2,00 cada.
- Não permitir venda sem estoque disponível.
- Não permitir venda se o dia anterior não estiver fechado.
- Não permitir venda parcelada.

---

Desenvolvido com foco em qualidade, manutenibilidade e clareza de código.

# Arquitetura - Spring Boot AI Budgeting

## Visão Geral

Este projeto implementa uma API de orçamento pessoal usando Spring AI, seguindo a **Arquitetura em Camadas DDD** (Domain-Driven Design).

## Princípios Arquitetônicos

### 1. Separação em Camadas

```
┌─────────────────────────────────────┐
│      INFRAESTRUTURA (HTTP)          │  Controllers REST
│  TransactionController              │
├─────────────────────────────────────┤
│    CAMADA DE APLICAÇÃO              │  Casos de Uso
│  PersistTransactionUseCase          │  Orquestração
│  ListTransactionsByCategoryUseCase  │  Policies
├─────────────────────────────────────┤
│      CAMADA DE DOMÍNIO              │  Lógica de Negócio
│  Transaction (Agregado)             │  Invariantes
│  Category (Value Object)            │  Linguagem Ubíqua
│  TransactionRepository (Contrato)   │
├─────────────────────────────────────┤
│    INFRAESTRUTURA (Persistência)    │  Implementações
│  TransactionEntity (JPA)            │  Adaptadores
│  TransactionRepositoryAdapter       │  Detalhes Técnicos
│  TransactionJpaRepository           │
└─────────────────────────────────────┘
```

### 2. Fluxo de Requisição

```
Cliente
   ↓
TransactionController (Infrastructure)
   ↓
PersistTransactionUseCase (Application)
   ↓
Transaction (Domain) + TransactionRepository (Domain Contract)
   ↓
TransactionRepositoryAdapter (Infrastructure)
   ↓
TransactionJpaRepository (Infrastructure - Spring Data)
   ↓
MySQL Database
```

## Camadas Detalhadas

### Domain (Domínio)

**Responsabilidade:** Modelar conceitos de negócio

**Localização:** `src/main/java/springbootai/budgeting/domain/`

**Componentes:**

- **Category.java** (Enum)
  - Categorias de transações: GROCERIES, PHARMA, AUTO, etc.
  - Conceito de negócio puro

- **TransactionId.java** (Record)
  - Identificador fortemente tipado para Transaction
  - Evita confusão com outros IDs

- **Transaction.java** (Class/Agregado)
  - Entidade de domínio com identidade
  - Contém lógica de negócio relacionada
  - Anotações: `@Getter`, `@AllArgsConstructor`

- **TransactionRepository.java** (Interface)
  - Contrato definido pelo negócio
  - Abstrai persistência
  - Métodos: `save()`, `findAllByCategory()`

**Características:**

- ❌ Sem dependências de Spring
- ❌ Sem anotações JPA
- ✅ Focado em linguagem ubíqua
- ✅ Independente de frameworks

### Application (Aplicação)

**Responsabilidade:** Orquestrar casos de uso

**Localização:** `src/main/java/springbootai/budgeting/application/`

**Componentes:**

- **Use Cases** (Services com `@Tool`)
  - `PersistTransactionUseCase`
  - `ListTransactionsByCategoryUseCase`
  - Expõem funcionalidades para ChatClient

- **Input DTOs** (`application/input/`)
  - `PersistTransactionInput`
  - Contém `@ToolParam` para Spring AI

- **Output DTOs** (`application/output/`)
  - `TransactionOutput`
  - Transforma Domain para resposta

**Características:**

- ✅ Usa cases de domínio
- ✅ Injeta repositórios (abstrações)
- ✅ Implementa policies de aplicação
- ✅ `@Tool` registra em ChatClient
- ❌ Sem detalhes de HTTP
- ❌ Sem detalhes de persistência

### Infrastructure (Infraestrutura)

**Responsabilidade:** Adaptadores e implementações técnicas

**Localização:** `src/main/java/springbootai/budgeting/infrastructure/`

**Subcomponentes:**

#### HTTP (`infrastructure/http/`)
```
- TransactionController
  ├── request/
  │   └── TransactionRequest
  └── response/
      └── TransactionResponse
```

- **Controllers:** REST endpoints
- **Requests:** DTO de entrada HTTP
- **Responses:** DTO de saída HTTP

#### Persistence (`infrastructure/persistence/`)
```
- TransactionEntity (JPA @Entity)
- TransactionJpaRepository (Spring Data)
- TransactionRepositoryAdapter (Implementação de TransactionRepository)
```

- **Entities:** Mapeamento JPA
- **JpaRepository:** CRUD automático do Spring
- **Adapter:** Implementa contrato de domínio

#### Config (`infrastructure/config/`)
```
- AudioDirectoryConfig
```

- Configurações do Spring
- Beans customizados

**Características:**

- ✅ Sabe sobre Spring Framework
- ✅ Sabe sobre persistência
- ✅ Sabe sobre HTTP
- ❌ Não expõe detalhes para domain
- ❌ Adaptável: trocar MySQL por PostgreSQL aqui

## Padrões de Design

### 1. Repository Pattern

**Contrato em Domain:**
```java
// domain/TransactionRepository.java
public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> findAllByCategory(Category category);
}
```

**Implementação em Infrastructure:**
```java
// infrastructure/persistence/TransactionRepositoryAdapter.java
@Component
public class TransactionRepositoryAdapter implements TransactionRepository {
    // Implementação usando JPA
}
```

**Benefício:** Business lógica não depende de Spring Data ou JPA.

### 2. Adapter Pattern

```
Domain Contract (abstração)
        ↑
        │
    Adapter (implementação concreta)
        │
    Spring Data JPA (biblioteca técnica)
```

Permite trocar implementação sem afetar domínio.

### 3. Tool Calling com Spring AI

```java
@Tool(name = "persist-transaction", description = "...")
public TransactionOutput execute(PersistTransactionInput input) {
    // Uso case encapsulado como ferramenta para AI
}
```

ChatClient descobre automaticamente via `@Tool` e `@ToolParam`.

### 4. Value Objects vs Entities

- **Value Objects** (Records):
  - `TransactionId` - identidade
  - `PersistTransactionInput` - dados imutáveis
  - `TransactionOutput` - resposta imutável

- **Entities** (Classes):
  - `Transaction` - possui identidade e ciclo de vida
  - Pode mudar estado

## Fluxo de Persistência

### Criando Transação

```
1. Cliente HTTP: POST /transactions
   {"description": "Mercado", "category": "GROCERIES", "amount": 8000}

2. TransactionController.createTransaction(TransactionRequest)
   - Converte: request.toInput() → PersistTransactionInput

3. PersistTransactionUseCase.execute(PersistTransactionInput)
   - Cria: new Transaction(desc, amount, category)
   - Salva: transactionRepository.save(transaction)

4. TransactionRepositoryAdapter.save(Transaction)
   - Converte: TransactionEntity.from(transaction)
   - Persiste: jpaRepository.save(entity)
   - Retorna: entity.toDomain()

5. Resposta: TransactionResponse.from(TransactionOutput)
   - Cliente recebe JSON com transaction criada
```

### Lendo Transações por Categoria

```
1. Cliente HTTP: GET /transactions/GROCERIES

2. TransactionController.readTransactions(Category)
   - Chama: listByCategoryUseCase.execute(category)

3. ListTransactionsByCategoryUseCase.execute(Category)
   - Busca: transactionRepository.findAllByCategory(category)

4. TransactionRepositoryAdapter.findAllByCategory(Category)
   - Query: jpaRepository.findByCategory(category)
   - Converte: stream().map(TransactionEntity::toDomain())

5. Resposta: stream().map(TransactionResponse::from())
   - Cliente recebe lista de transações
```

## Ciclo de Desenvolvimento

### Adicionar Novo Caso de Uso

1. **Domain:** Adicionar métodos/modelos em Transaction ou novos value objects
2. **Application:** Criar novo Use Case com `@Tool` e `@ToolParam`
3. **Infrastructure/Persistence:** Adicionar queries se necessário em JpaRepository
4. **Infrastructure/HTTP:** Adicionar endpoint em TransactionController

### Trocar Banco de Dados

**Sem afetarimplementation Domain:**

1. Remover `TransactionRepositoryAdapter` existente
2. Implementar novo adapter (ex: `TransactionRepositoryMongoAdapter`)
3. Atualizar beans de configuração

Domain e Application continuam idênticas!

## Dependências Entre Camadas

```
Domain → ❌ Application, Infrastructure
         ❌ Spring Framework

Application → ✅ Domain
             ❌ Infrastructure (depende de abstrações)
             ✅ Spring (@Service, @Tool)

Infrastructure → ✅ Domain (interfaces)
                ✅ Application (usa cases)
                ✅ Spring Framework
```

**Regra:** Dependências apontam sempre para dentro (Domain é o núcleo).

## Configuração Spring AI

### Beans

```java
@Bean
ChatClient chatClient(ChatClient.Builder builder) {
    return builder.build();
}
```

### Tool Registration

No TransactionController construtor:

```java
this.chatClient = chatClientBuilder
    .defaultSystem(systemPrompt.getContentAsString(...))
    .defaultTools(persistTransactionUseCase, listTransactionsByCategoryUseCase)
    .build();
```

ChatClient descobre `@Tool` métodos automaticamente.

## Testes

### Estrutura

```
src/test/java/springbootai/budgeting/
├── BudgetingApplicationTests (contexto)
├── OpenAiTranscriptionModelIT (integração)
├── OpenAiSpeechModelIT (integração)
└── ToolCallingIT (integração)
```

### Estratégia

- **Unit Tests:** Testar Domain e Use Cases com repositórios mock
- **Integration Tests:** Testar com banco real (H2) e ChatClient

### Exemplo Unit Test

```java
@Test
void should_persist_transaction() {
    // Arrange
    var repository = new FakeTransactionRepository();
    var useCase = new PersistTransactionUseCase(repository);
    
    // Act
    var result = useCase.execute(
        new PersistTransactionInput("Mercado", 8000, GROCERIES)
    );
    
    // Assert
    assertThat(result.description()).isEqualTo("Mercado");
}
```

## Próximos Passos

1. 📚 Estudar cada componente em `src/main/java/springbootai/budgeting`
2. 🧪 Executar testes: `./gradlew test`
3. 🚀 Implementar novo caso de uso
4. 🔄 Refatorar com novos padrões aprendidos
5. 📖 Ler [Domain-Driven Design - Eric Evans](https://domainlanguage.com/ddd/)

## Referências

- [Spring Framework Documentation](https://spring.io/projects/spring-framework)
- [Spring AI Reference](https://docs.spring.io/spring-ai/reference/index.html)
- [Domain-Driven Design Distilled](https://vaughnvernon.com/)
- [Clean Architecture - Robert C. Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

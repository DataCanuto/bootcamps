# DIO Spring Boot - Final Project 05: Spring AI (Budgeting)

## Introdução

Este projeto final aplica Spring AI em uma API de orçamento pessoal, mantendo a mesma arquitetura em camadas utilizada ao longo do treinamento.

O objetivo é integrar capacidades de IA sem contornar os limites de domínio e casos de uso.

## Contexto do Código

O projeto processa comandos de voz para criar e consultar transações financeiras.

Fluxo primário:

1. Cliente envia um arquivo de áudio.
2. Áudio é transcrito em texto.
3. O modelo seleciona uma ferramenta/caso de uso da aplicação.
4. O caso de uso persiste ou consulta dados de transações.
5. A resposta final é convertida em áudio.

## Estrutura do Projeto

- `src/main/java/springbootai/budgeting/domain`
  - Modelo de domínio e contrato do repositório.
- `src/main/java/springbootai/budgeting/application`
  - Casos de uso usados por REST e tool calling com AI.
- `src/main/java/springbootai/budgeting/infrastructure`
  - Adaptadores HTTP, adaptadores JPA, e integração.

## Tópicos Específicos do Módulo

### Speech-to-text (Fala para Texto)

- Usa `ChatClient` para transcrição de áudio.
- Configurações do modelo em `application.properties`.

### Tool Calling

- `ChatClient` registra ferramentas de casos de uso.
- Métodos com `@Tool` expõem capacidades de negócio para o modelo.

### Text-to-speech (Texto para Fala)

- `ChatClient` produz áudio MP3 da resposta final.
- Endpoint de IA retorna áudio gerado.

## Documentação Spring AI

- Spring AI Reference: https://docs.spring.io/spring-ai/reference/index.html
- ChatModel API: https://docs.spring.io/spring-ai/reference/api/chatmodel.html
- ChatClient API: https://docs.spring.io/spring-ai/reference/api/chatclient.html
- Tools API: https://docs.spring.io/spring-ai/reference/api/tools.html
- Audio Transcriptions API: https://docs.spring.io/spring-ai/reference/api/audio/transcriptions.html
- Audio Speech API: https://docs.spring.io/spring-ai/reference/api/audio/speech.html

## Arquitetura DDD em Camadas

O projeto segue a separação conceitual em três camadas:

```
domain/          -> Modelo de negócio, invariantes, contratos
application/     -> Casos de uso, orquestração, políticas
infrastructure/  -> Adaptadores (HTTP, persistência, clients externos, integração com framework)
```

Por que isso importa:

- `domain` permanece focado em linguagem de negócio, não em detalhes do framework.
- `application` coordena comportamento de domínio para ações específicas.
- `infrastructure` pode mudar sem forçar reescritas de lógica de negócio.

## Identificadores Fortemente Tipados

Em vez de passar primitivos brutos (`UUID`, `String`) por toda parte, usamos tipos explícitos como `TransactionId`.

Benefícios:

- Melhor segurança em tempo de compilação.
- Assinaturas mais expressivas.
- Caminho de evolução mais limpo para regras de ID.

## Padrão Repository

O contrato do repositório pertence ao lado do negócio, enquanto implementações específicas de tecnologia ficam na infraestrutura.

Padrão usado:

- Contrato de domínio: `TransactionRepository` em `domain/`.
- Implementação adaptadora: JPA em `infrastructure/`.

Impacto arquitetural:

- Lógica de negócio depende de abstrações, não de frameworks de persistência.
- Mudança de tecnologia de armazenamento se torna uma mudança de adaptador.
- Testes unitários de casos de uso ficam mais simples com repositórios fake/mock.

## Docker Compose em Desenvolvimento

Este projeto inclui `compose.yml` e suporte Docker Compose do Spring Boot.

Papel no desenvolvimento local:

- Inicia serviços de infraestrutura necessários (banco de dados, dependências).
- Mantém setup local reproduzível.
- Reduz atrito de onboarding padronizando dependências de ambiente.

### Como Usar

```bash
# Iniciar serviços de infraestrutura (MySQL)
docker compose up

# Em outro terminal, executar a aplicação
./gradlew bootRun
```

## Como Executar

Defina sua chave de API OpenAI:

```bash
export OPENAI_API_KEY="your_api_key_here"
```

Execute a aplicação e testes:

```bash
# Build e execução
./gradlew bootRun

# Testes
./gradlew test

# Build completo
./gradlew build
```

### Desenvolvimento Local com Docker

```bash
# Terminal 1: Iniciar infraestrutura
docker compose up

# Terminal 2: Aplicação
export OPENAI_API_KEY="sua_chave_aqui"
./gradlew bootRun
```

## Configuração de Propriedades

Arquivo: `src/main/resources/application.properties`

Chaves principais:

- `spring.ai.openai.api-key` - Chave da API OpenAI (via variável de ambiente)
- `spring.ai.openai.chat.options.model` - Modelo de chat (gpt-4o-mini)
- `spring.ai.openai.audio.transcription.options.model` - Modelo de transcrição (whisper-1)
- `spring.ai.openai.audio.speech.options.model` - Modelo de fala (tts-1)
- `app.audio.output.path` - Diretório para áudio processado
- `app.audio.temp.path` - Diretório temporário
- `spring.datasource.url` - URL do banco de dados

## Dependências Principais

- **Spring Boot 4.0.6** - Framework web
- **Spring AI 2.0.0-M4** - IA com ChatClient, transcrição, síntese de fala
- **Spring Data JPA** - Persistência
- **MySQL Driver** - Banco de dados
- **H2 Database** - Testes
- **Lombok** - Redução de boilerplate
- **JUnit 5** - Testes

## Endpoints

### Transações

- `POST /transactions` - Criar transação
  ```json
  {
    "description": "Gastei no mercado",
    "category": "GROCERIES",
    "amount": 8000
  }
  ```

- `GET /transactions/{category}` - Listar por categoria
  ```
  GET /transactions/GROCERIES
  ```

- `POST /transactions/ai` - Processar áudio e executar ação
  - Envia arquivo de áudio
  - Transcreve com Whisper
  - Usa ChatClient com tool calling
  - Retorna resposta em áudio (MP3)

## Notas

- Projeto educacional focado em IA com disciplina arquitetural.
- Testes de integração com provedores externos podem exigir credenciais ativas.
- O compose.yml fornece MySQL em localhost:3307 por padrão.
- Variáveis de ambiente (`OPENAI_API_KEY`) são carregadas do arquivo `.env` em desenvolvimento.

## Próximos Passos

1. Configure a chave de API OpenAI
2. Inicie o Docker Compose: `docker compose up`
3. Execute a aplicação: `./gradlew bootRun`
4. Acesse: `http://localhost:8080/transactions`
5. Execute testes: `./gradlew test`

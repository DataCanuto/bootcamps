# Guia de Desenvolvimento Local

## Pré-requisitos

- Java 21 (JDK) ou superior
- Gradle (incluído via gradlew)
- Docker e Docker Compose
- Chave de API OpenAI

## Configuração Inicial

### 1. Clonar e Preparar

```bash
# Navegar até o diretório do projeto
cd seu_repositorio

# Copiar arquivo de exemplo de ambiente
cp .env.example .env

# Editar .env com suas credenciais
# Adicione sua chave OpenAI em OPENAI_API_KEY
```

### 2. Iniciar Infraestrutura com Docker Compose

```bash
# Terminal 1: Iniciar MySQL
docker compose up

# Saída esperada:
# database | Ready for connections
```

O MySQL estará disponível em:
- **Host:** localhost
- **Porta:** 3307
- **Banco de dados:** transaction
- **Usuário:** app
- **Senha:** app

### 3. Executar a Aplicação

```bash
# Terminal 2: Executar aplicação
source .env  # Linux/Mac
# ou
set -a && source .env && set +a  # Linux/Mac com variáveis exportadas
# ou
type .env | findstr OPENAI  # Windows (verificar conteúdo)

./gradlew bootRun
```

A aplicação estará disponível em:
- **URL:** http://localhost:8080
- **H2 Console:** http://localhost:8080/h2-console (se em perfil de teste)

## Comandos Gradle Úteis

```bash
# Compilar
./gradlew compileJava

# Executar testes
./gradlew test

# Build completo (sem testes)
./gradlew build -x test

# Build completo (com testes)
./gradlew build

# Executar aplicação
./gradlew bootRun

# Limpar build anterior
./gradlew clean

# Atualizar dependências
./gradlew dependencies
```

## Perfis de Configuração

### Perfil `dev` (Padrão com Docker)

Usa MySQL via Docker Compose.

```bash
./gradlew bootRun
# Usa application-dev.properties
```

### Perfil de Teste (Padrão em testes)

Usa H2 em memória.

```bash
./gradlew test
# Usa application.properties
```

## Estrutura de Diretórios

```
src/
├── main/
│   ├── java/springbootai/budgeting/
│   │   ├── domain/              # Modelos de domínio
│   │   ├── application/         # Casos de uso
│   │   ├── infrastructure/      # Adaptadores
│   │   └── BudgetingApplication.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       └── prompts/
│           └── system-message.st
└── test/
    ├── java/springbootai/budgeting/
    │   ├── *Tests.java          # Testes unitários
    │   └── *IT.java             # Testes de integração
    └── resources/audio/         # Arquivos de teste
```

## Testando Endpoints

### Criar Transação

```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Gastei no mercado",
    "category": "GROCERIES",
    "amount": 8000
  }'
```

### Listar Transações por Categoria

```bash
curl http://localhost:8080/transactions/GROCERIES
```

### Processar Áudio (em desenvolvimento)

```bash
curl -X POST http://localhost:8080/transactions/ai \
  -F "file=@recording.m4a"
```

## Troubleshooting

### Erro: "Cannot find OPENAI_API_KEY"

**Solução:**
```bash
# Verificar se .env está carregado
cat .env

# Exportar manualmente
export OPENAI_API_KEY="sua_chave_aqui"
./gradlew bootRun
```

### Erro: "Connection refused" (MySQL)

**Solução:**
```bash
# Verificar se Docker Compose está rodando
docker ps

# Iniciar se não estiver
docker compose up -d

# Verificar logs
docker compose logs database
```

### Erro: "Port 3307 already in use"

**Solução:**
```bash
# Encontrar processo usando porta 3307
lsof -i :3307  # Linux/Mac
netstat -ano | findstr 3307  # Windows

# Parar container existente
docker compose down
docker compose up
```

### Compilação Falha

**Solução:**
```bash
# Limpar e recompilar
./gradlew clean compileJava

# Se usar IDE, atualizar projeto:
# - IntelliJ: File > Invalidate Caches
# - VS Code: Reload Window
```

## Debugging

### Habilitar Logs Detalhados

Editar `application.properties`:
```properties
logging.level.root=INFO
logging.level.springbootai.budgeting=DEBUG
logging.level.org.springframework.ai=DEBUG
```

### Usar Debugger no IntelliJ/VS Code

```bash
# Terminal: Executar com debug
./gradlew bootRun --debug-jvm

# IDE: Conectar ao debug port 5005
```

## Parar Infraestrutura

```bash
# Parar sem remover volumes
docker compose stop

# Parar e remover tudo
docker compose down

# Remover volumes (dados)
docker compose down -v
```

## Dicas e Boas Práticas

1. **Sempre carregar .env antes de executar:**
   ```bash
   source .env && ./gradlew bootRun
   ```

2. **Manter Docker Compose rodando em terminal separado:**
   - Facilita visualização de logs
   - Melhor controle de inicialização/parada

3. **Usar `./gradlew clean` antes de builds principais:**
   - Evita cache indesejado
   - Garante compilação limpa

4. **Checar logs do Docker Compose:**
   ```bash
   docker compose logs -f database
   ```

5. **Para testes, não há necessidade de Docker rodando:**
   - Testes usam H2 em memória
   - `./gradlew test` funciona independentemente

## Próximos Passos

1. ✅ Configurar ambiente
2. ✅ Iniciar Docker Compose
3. ✅ Executar aplicação
4. ✅ Testar endpoints
5. 📚 Estudar código em `src/main/java/springbootai/budgeting`
6. 🧪 Executar testes: `./gradlew test`
7. 🚀 Implementar novas features

## Referências

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/index.html)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Gradle Documentation](https://docs.gradle.org/current/userguide/userguide.html)

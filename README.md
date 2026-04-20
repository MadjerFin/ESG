# 🌱 Projeto - Cidades ESG Inteligentes

Sistema REST em Java Spring Boot para monitoramento dos três pilares ESG (Environmental, Social e Governance) em cidades brasileiras.

---

## 📋 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2**
- **Spring Data JPA**
- **H2 Database** (em memória)
- **Lombok**
- **SpringDoc OpenAPI (Swagger)**
- **Docker & Docker Compose**
- **GitHub Actions** (CI/CD)

---

## 🚀 Como executar localmente com Docker

### Pré-requisitos
- Docker instalado
- Docker Compose instalado

### Subir em ambiente de Staging (porta 8081)
```bash
docker compose --profile staging up --build
```

### Subir em ambiente de Produção (porta 8080)
```bash
docker compose --profile prod up --build
```

### Acessar a aplicação
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **H2 Console:** http://localhost:8080/h2-console
- **API Base:** http://localhost:8080/api/cidades

### Executar sem Docker (Maven)
```bash
mvn spring-boot:run
```

---

## ⚙️ Pipeline CI/CD

### Ferramenta utilizada
**GitHub Actions** — configurado em `.github/workflows/ci-cd.yml`

### Etapas do pipeline

| Etapa | Descrição | Branch |
|---|---|---|
| **Build e Testes** | Compila o projeto e roda os testes JUnit | Todas |
| **Docker Build** | Constrói a imagem Docker e faz teste básico | Todas |
| **Deploy Staging** | Sobe a aplicação na porta `8081` | `develop` |
| **Deploy Produção** | Sobe a aplicação na porta `8080` | `main` |

### Fluxo
```
push develop → Build → Testes → Docker Build → Deploy Staging (8081)
push main    → Build → Testes → Docker Build → Deploy Produção (8080)
```

---

## 🐳 Containerização

### Dockerfile (Multi-stage build)

```dockerfile
# Stage 1: Build com Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime leve com JRE Alpine
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
```

**Estratégias adotadas:**
- Multi-stage build para imagem final menor
- Usuário não-root por segurança
- Variáveis de ambiente configuráveis
- Health check configurado no docker-compose

---

## 📡 Endpoints da API

### Cidades
| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/cidades` | Listar todas |
| POST | `/api/cidades` | Criar cidade |
| GET | `/api/cidades/{id}` | Buscar por ID |
| GET | `/api/cidades/estado/{uf}` | Buscar por estado |
| PUT | `/api/cidades/{id}` | Atualizar |
| DELETE | `/api/cidades/{id}` | Deletar |

### Indicadores Ambientais (E)
`/api/cidades/{cidadeId}/ambiental` — GET, POST, PUT, DELETE

### Indicadores Sociais (S)
`/api/cidades/{cidadeId}/social` — GET, POST, PUT, DELETE

### Indicadores de Governança (G)
`/api/cidades/{cidadeId}/governanca` — GET, POST, PUT, DELETE

---

## 📊 Score ESG

Cada cidade recebe um **Score ESG** calculado automaticamente (0 a 10) com base nos indicadores registrados:

- **Score Ambiental:** energia renovável + área verde + emissão de CO₂
- **Score Social:** IDH + taxa de emprego + acesso à saúde
- **Score Governança:** índice de transparência + conformidade legal + anticorrupção

---

# ==============================
# Stage 1: Build
# ==============================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copia o pom.xml e baixa dependências (camada cacheável)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e compila
COPY src ./src
RUN mvn clean package -DskipTests -B

# ==============================
# Stage 2: Runtime
# ==============================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Cria usuário não-root por segurança
RUN addgroup -S esg && adduser -S esg -G esg

# Copia o JAR gerado
COPY --from=build /app/target/*.jar app.jar

# Define usuário e permissões
RUN chown esg:esg app.jar
USER esg

# Expõe a porta da aplicação
EXPOSE 8080

# Variáveis de ambiente padrão
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENV SPRING_PROFILES_ACTIVE=default

# Comando de inicialização
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

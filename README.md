# 📂 API de Projetos

API REST para cadastro de projetos e seus respectivos funcionários, com operações de criação e listagem.

---

## 🚀 Tecnologias

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 / PostgreSQL
- Lombok
- Swagger OpenAPI 3
- Maven

---

## 🧰 Requisitos

| Ferramenta      | Versão mínima |
|-----------------|----------------|
| Java            | 17             |
| Maven           | 3.8+           |
| Banco de dados  | PostgreSQL ou H2 (test) |

---

## 🚀 Como gerar e executar a API

### 📥 1. Clonar o repositório (se necessário)

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

---

### 💠 2. Gerar o `.jar` com Maven

```bash
mvn clean package
```

> Isso irá gerar o arquivo `.jar` na pasta `target/`.

---

### 🚀 3. Executar a aplicação

```bash
java -jar target/api-projetos-0.0.1-SNAPSHOT.jar
```

---

### ✅ 4. Testar se a API está funcionando

Usar o Swagger, a interface estará disponível em:

```
http://localhost:8098/apiprojetos/swagger-ui/index.html
```
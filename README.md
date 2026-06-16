# SistemaAPI_PIBIC

API REST para cadastro, publicacao e resolucao de casos clinicos em um contexto academico. O sistema permite gerenciar alunos, professores, casos clinicos, pacientes, conteudos clinicos, perguntas e respostas dos alunos.

## Tecnologias

- Java 25
- Spring Boot 4
- Spring WebMVC
- Spring Data JPA
- Bean Validation
- PostgreSQL
- H2 para testes
- Maven Wrapper

## Como Rodar

Entre na pasta da aplicacao:

```powershell
cd Sistema_Crud_API_PIBIC
```

Opcionalmente, suba o PostgreSQL com Docker:

```powershell
docker compose up -d
```

Configure as variaveis de ambiente:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/CRUD_API"
$env:DB_USER="postgres"
$env:DB_PASSWORD="postgres"
```

Execute:

```powershell
.\mvnw.cmd spring-boot:run
```

## Testes

```powershell
.\mvnw.cmd test
```

Os testes usam H2 em memoria, configurado em `src/test/resources/application.properties`.

## Endpoints Principais

- `GET /alunos`
- `GET /alunos/{id}`
- `GET /alunos/{id}/casos-disponiveis`
- `POST /alunos/{id}/casos/{casoId}/responder`
- `GET /alunos/{id}/historico`
- `GET /alunos/{id}/desempenho`
- `GET /professores`
- `GET /professores/{id}/casos`
- `GET /professores/{id}/relatorio-desempenho`
- `GET /casos`
- `GET /casos/{id}`
- `GET /casos/{id}/completo`
- `PATCH /casos/{id}/publicar`
- `POST /casos/{casoId}/perguntas`
- `GET /casos/{casoId}/perguntas`

## Exemplo de Resposta de Caso

```json
{
  "respostas": [
    {
      "idPergunta": 1,
      "respostaMarcada": "A"
    }
  ]
}
```

## Configuracao

O arquivo principal usa variaveis de ambiente:

- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`
- `DDL_AUTO`
- `SHOW_SQL`
- `ADMIN_USER` / `ADMIN_PASSWORD`
- `PROFESSOR_USER` / `PROFESSOR_PASSWORD`
- `ALUNO_USER` / `ALUNO_PASSWORD`

Veja `Sistema_Crud_API_PIBIC/src/main/resources/application-example.properties` para um exemplo sem senha real.

## Acesso

A API usa HTTP Basic. Em ambiente local, os usuarios padrao sao:

- `admin` / `admin123` com papel `ADMIN`
- `professor` / `professor123` com papel `PROFESSOR`
- `aluno` / `aluno123` com papel `ALUNO`

## Autores

Projeto academico PIBIC.

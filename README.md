# SistemaAPI_PIBIC

API REST para cadastro, publicacao e resolucao de casos clinicos em um contexto academico. O sistema permite gerenciar alunos, professores, casos clinicos, pacientes, conteudos clinicos, perguntas e respostas dos alunos.

## Tecnologias

- Java 25
- Spring Boot 4
- Spring WebMVC
- Spring Data JPA
- Bean Validation
- Spring Security
- Flyway
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

## Documentacao e Collections

- OpenAPI: `http://localhost:8080/openapi.yaml`
- Collection Postman: `docs/SistemaAPI_PIBIC.postman_collection.json`
- Requisicoes HTTP: `docs/requests.http`

## Endpoints Principais

- `GET /openapi.yaml`
- `GET /alunos`
- `GET /alunos/{id}`
- `GET /alunos/{id}/casos-disponiveis`
- `GET /alunos/{id}/casos/{casoId}/completo`
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

## Valores Padronizados

- `status`: `RASCUNHO`, `PUBLICADO`, `ARQUIVADO`
- `nivelDificuldade`: `FACIL`, `MEDIA`, `DIFICIL`
- `tipo` da pergunta: `MULTIPLA_ESCOLHA`, `VERDADEIRO_FALSO`, `DISCURSIVA`
- `sexo`: `MASCULINO`, `FEMININO`, `OUTRO`, `NAO_INFORMADO`
- `estadoCivil`: `SOLTEIRO`, `CASADO`, `DIVORCIADO`, `VIUVO`, `SEPARADO`, `UNIAO_ESTAVEL`, `NAO_INFORMADO`

## Exemplo de Pergunta

```json
{
  "texto": "Qual alternativa correta?",
  "tipo": "MULTIPLA_ESCOLHA",
  "gabarito": "A",
  "resposta": "A",
  "tempoEsperado": "5 minutos",
  "alternativas": [
    { "letra": "A", "texto": "Conduta inicial correta", "correta": true },
    { "letra": "B", "texto": "Conduta inadequada", "correta": false }
  ]
}
```

Os campos antigos `alternativaA` ate `alternativaE` ainda sao aceitos por compatibilidade.

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

Alunos acessam casos publicados pelos endpoints de aluno. Professores e administradores acessam a gestao completa de casos.

## Autores

Projeto academico PIBIC.

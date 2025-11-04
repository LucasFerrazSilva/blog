# Blog

Este repositório contém um blog construído com Spring Boot, Thymeleaf e JPA/Hibernate. O projeto inclui recursos básicos 
de CMS: posts, categorias, busca paginada, upload de imagem, autenticação para endpoints administrativos e páginas públicas.

## Visão geral
- Backend: Spring Boot (Java 21)
- Templates: Thymeleaf
- Persistência: Spring Data JPA + Hibernate
- Banco de dados: PostgreSQL
- Migrações: scripts SQL em `src/main/resources/db/migration`

## Recursos principais
- CRUD de posts e categorias (endpoints administrativos protegidos)
- Endpoint público para leitura de posts (`/ler/{category}/{slug}`)
- Upload de imagem para capa do post (campo `image` convertido para base64 no envio)
- Busca paginada em `/search?q={termo}&page={n}` (10 resultados por página)
- Página que lista posts por categoria em `/categoria/{name}`
- Menu lateral com categorias

## Estrutura principal de pastas

- `src/main/java` - código fonte Java
- `src/main/resources/templates` - templates Thymeleaf
- `src/main/resources/static` - CSS/JS/estáticos
- `src/main/resources/db/migration` - SQLs de migração (Flyway-ready)
- `src/test` - testes automatizados

## Preparar o ambiente (Windows)
Requisitos mínimos:
- Java 21 JDK
- Maven (o projeto contém o wrapper `mvnw.cmd`)

1. Abra um terminal (cmd.exe ou PowerShell) na raiz do projeto.

2. Para compilar e executar os testes:

```powershell
# executar todos os testes
.\mvnw.cmd test

# executar apenas um teste específico (exemplo)
.\mvnw.cmd -Dtest=HomeControllerTest#shouldSearchPostsPaginated test
```

3. Para executar a aplicação localmente:

```powershell
# rodar a aplicação com o wrapper
.\mvnw.cmd spring-boot:run
```

Depois abra http://localhost:8080 no navegador.

> Observação: Ao rodar testes a suite utiliza um banco H2 em memória com as migrações SQL aplicadas automaticamente.

## Propriedades importantes
- `src/main/resources/application.properties` — configurações da aplicação em runtime
- `src/test/resources/application-test.properties` — configurações usadas nos testes (H2, etc.)

## Endpoints principais
- GET `/` — Home (página com posts recentes)
- GET `/ler/{category}/{slug}` — Ler um post
- GET `/categoria/{name}` — Lista posts por categoria
- GET `/search?q={termo}&page={n}` — Busca paginada (10 itens por página)
- GET `/login` — Página de login
- Endpoints de administração (Posts/Categories) requerem ROLE_ADMIN

## Notas sobre comportamento e templates
- O campo `slug` é gerado a partir do `title` removendo espaços e caracteres especiais (ex.: "Minha Postagem" → `minha-postagem`).
- O upload de imagem agora é feito via campo de arquivo (input type=file) na UI; o back-end converte o arquivo para base64 no momento do envio e armazena o base64 no campo `image`.
- Os templates do Thymeleaf foram ajustados para construir a tag `<img>` a partir do base64 em atributos `data-base64` e um pequeno script monta o `src` dinamicamente quando o DOM estiver pronto.
- O menu de categorias foi mudado para um sidebar (abrível via hamburger); no desktop as categorias aparecem no menu lateral; no mobile o hamburger abre o sidebar.

## Testes e cobertura
- Existem testes de controller (`HomeControllerTest`, etc.).
- Para rodar todos os testes use `.\mvnw.cmd test`.

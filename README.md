# Blog

Este repositório contém um blog construído com Spring Boot, Thymeleaf e JPA/Hibernate. O projeto inclui recursos básicos 
de CMS: posts, categorias, busca paginada, upload de imagem, autenticação para endpoints administrativos e páginas públicas.

## Visão geral
- **Backend**: Spring Boot (Java 21)
- **Templates**: Thymeleaf
- **Persistência**: Spring Data JPA + Hibernate
- **Banco de dados**: PostgreSQL
- **Migrações**: Flyway (scripts SQL em `src/main/resources/db/migration`)
- **Orquestração**: Kubernetes (manifestos em `k8s/`)
- **Ingress Controller**: Traefik (IngressRoute)
- **Containerização**: Docker (imagem `lucasferraz95/blog`)

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
- `k8s/` - manifestos Kubernetes para deploy
  - `k8s/namespace.yml` - namespace 'blog'
  - `k8s/app/` - deployment, service e ingress da aplicação
  - `k8s/postgres/` - deployment, service, PV/PVC e secrets do PostgreSQL
  - `k8s/secrets/` - secrets para credenciais do banco

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

## Executando com Docker Compose (Postgres)

O arquivo de compose para executar o serviço Postgres localmente é `compose-postgres.yaml` na raiz do projeto. Use o comando abaixo para subir os containers (ele irá conectar o Postgres à network externa `blog-net`):

```powershell
# subir containers usando o arquivo compose-postgres.yaml
docker compose -f compose-postgres.yaml up -d

# parar e remover containers
docker compose -f compose-postgres.yaml down
```

Depois abra http://localhost:8080 no navegador.

> Observação: Ao rodar testes a suite utiliza um banco H2 em memória com as migrações SQL aplicadas automaticamente.

## Executando com Docker

Para executar a aplicação completa (app + PostgreSQL) via Docker:

```powershell
# Criar a network
docker network create blog-net

# Subir o PostgreSQL
docker run -d --name postgres-local --network blog-net ^
  -e POSTGRES_DB=blog ^
  -e POSTGRES_USER=blog ^
  -e POSTGRES_PASSWORD=blog123 ^
  -p 5432:5432 ^
  postgres:16-alpine

# Subir a aplicação
docker run -d --name blog --network blog-net ^
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-local:5432/blog ^
  -e SPRING_DATASOURCE_USERNAME=blog ^
  -e SPRING_DATASOURCE_PASSWORD=blog123 ^
  -p 8080:8080 ^
  lucasferraz95/blog:1.0.1
```

Acesse http://localhost:8080

## Deploy com Kubernetes

O projeto inclui manifestos Kubernetes para deploy completo da aplicação e banco de dados PostgreSQL.

### Pré-requisitos
- Cluster Kubernetes funcionando (minikube, k3s, EKS, GKE, etc.)
- `kubectl` configurado para o cluster
- Traefik Ingress Controller instalado (ou ajustar para outro controller)
- Certificado TLS configurado (Let's Encrypt via cert-manager ou secret manual)

### Estrutura dos manifestos
```
k8s/
├── namespace.yml              # Namespace 'blog'
├── app/
│   ├── app-deployment.yml     # Deployment da aplicação (lucasferraz95/blog:1.0.1)
│   ├── app-service.yml        # Service ClusterIP (porta 8080)
│   ├── app-ingress.yml        # Ingress padrão Kubernetes
│   └── app-ingressroute.yml   # IngressRoute Traefik (HTTPS)
├── postgres/
│   ├── postgres-deployment.yml # Deployment PostgreSQL 16-alpine
│   ├── postgres-service.yml   # Service ClusterIP (porta 5432)
│   └── postgres-pvc.yml       # PersistentVolumeClaim (5Gi)
└── secrets/
    └── db-credentials.yml     # Secret com credenciais do banco
```

### Passo a passo para deploy

1. **Criar o namespace**:
```powershell
kubectl apply -f k8s/namespace.yml
```

2. **Trocar para o namespace blog** (opcional, facilita comandos posteriores):
```powershell
kubectl config set-context --current --namespace=blog
```

3. **Configurar credenciais do banco**:
```powershell
# Aplicar secret com credenciais (ajustar valores em db-credentials.yml antes)
kubectl apply -f k8s/secrets/db-credentials.yml
```

4. **Deploy do PostgreSQL**:
```powershell
# Aplicar PVC para persistência
kubectl apply -f k8s/postgres/postgres-pvc.yml

# Aplicar deployment e service do PostgreSQL
kubectl apply -f k8s/postgres/postgres-deployment.yml
kubectl apply -f k8s/postgres/postgres-service.yml
```

5. **Deploy da aplicação**:
```powershell
# Aplicar deployment e service
kubectl apply -f k8s/app/app-deployment.yml
kubectl apply -f k8s/app/app-service.yml

# Aplicar IngressRoute (ajustar domínio e certificado em app-ingressroute.yml)
kubectl apply -f k8s/app/app-ingressroute.yml
```

6. **Verificar status**:
```powershell
# Listar todos os recursos no namespace blog
kubectl get all -n blog

# Ver logs da aplicação
kubectl logs -n blog deployment/blog-app-deployment -f

# Ver logs do PostgreSQL
kubectl logs -n blog deployment/postgres -f
```

### Variáveis de ambiente (aplicação)
O deployment da aplicação (`app-deployment.yml`) configura:
- `DATABASE_URL`: `jdbc:postgresql://postgres:5432/blog` (hostname do Service PostgreSQL)
- `DATABASE_USER`: lido do Secret `db-credentials`
- `DATABASE_PASSWORD`: lido do Secret `db-credentials`

> **Importante**: Ao buildar uma nova versão da imagem Docker, atualize a tag em `app-deployment.yml` (ex.: `lucasferraz95/blog:1.0.2`) e aplique o manifesto novamente.

### IngressRoute vs Ingress
Este projeto fornece dois arquivos de ingress:

#### IngressRoute (Traefik)
- **Arquivo**: `k8s/app/app-ingressroute.yml`
- **Específico do Traefik**: CRD que só funciona com Traefik Ingress Controller
- **Vantagens**: configuração declarativa de TLS, middlewares nativos, roteamento avançado
- **Uso**: ideal se você já usa Traefik no cluster

#### Ingress (Kubernetes padrão)
- **Arquivo**: `k8s/app/app-ingress.yml`
- **Padrão do Kubernetes**: recurso nativo (`networking.k8s.io/v1`)
- **Vantagens**: funciona com qualquer Ingress Controller (Nginx, HAProxy, Traefik, etc.)
- **Uso**: escolha este se não usa Traefik ou quer portabilidade

**Como escolher**:
- Se usa **Traefik**: aplique `app-ingressroute.yml`
- Se usa **Nginx** ou outro: aplique `app-ingress.yml`
- Não aplique os dois ao mesmo tempo para evitar conflitos

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

## Troubleshooting

### Aplicação não conecta ao PostgreSQL no Kubernetes
- Verifique se o Service `postgres` está rodando: `kubectl get svc -n blog`
- Confirme que o Secret `db-credentials` existe e contém `POSTGRES_USER` e `POSTGRES_PASSWORD`:
  ```powershell
  kubectl get secret db-credentials -n blog
  kubectl describe secret db-credentials -n blog
  ```
- Cheque os logs: `kubectl logs -n blog deployment/blog-app-deployment`
- Teste conectividade do pod ao banco:
  ```powershell
  kubectl exec -n blog deployment/blog-app-deployment -- nc -zv postgres 5432
  ```

### IngressRoute não responde
- Verifique se o Traefik está instalado: `kubectl get pods -n kube-system | findstr traefik`
- Confirme que o certificado TLS existe (se usar HTTPS):
  ```powershell
  kubectl get secret -n blog
  ```
- Veja os logs do Traefik: `kubectl logs -n kube-system deployment/traefik`
- Verifique se o IngressRoute foi criado corretamente:
  ```powershell
  kubectl get ingressroute -n blog
  kubectl describe ingressroute blog-ingressroute -n blog
  ```

### PersistentVolume não monta
- Verifique se o PVC foi provisionado: `kubectl get pvc -n blog`
- Confirme o status do PVC (deve estar `Bound`):
  ```powershell
  kubectl describe pvc postgres-pvc -n blog
  ```
- Se usar storage class diferente, ajuste `storageClassName` em `postgres-pvc.yml`
- Para clusters locais (minikube, k3s), certifique-se de que o provisionador de storage está habilitado

### Erro "Schema-validation: wrong column type" no Flyway
- Este erro ocorre quando o schema do banco não corresponde às entidades JPA
- Solução: delete o banco e deixe o Flyway recriar do zero:
  ```powershell
  kubectl exec -n blog deployment/postgres -- psql -U blog -c "DROP SCHEMA public CASCADE; CREATE SCHEMA public;"
  kubectl rollout restart deployment/blog-app-deployment -n blog
  ```

### Imagem muito grande no base64
- Se receber erro "Concatenated string is too long, exceeding the threshold of '100.000' characters"
- Causa: a imagem convertida para base64 excede o limite do Thymeleaf
- Solução: reduza o tamanho da imagem antes do upload (comprimir ou redimensionar para max 500KB)


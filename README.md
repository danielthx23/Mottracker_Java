# Mottracker

## Descrição do Projeto

Projeto desenvolvido para o **Challenge da empresa Mottu**, FIAP 2025.

O projeto busca resolver o problema de organização e localização das motos nos pátios da Mottu, facilitando o monitoramento em tempo real. A solução utiliza câmeras com sensores de posicionamento para capturar a localização exata de cada moto. Cada moto possui um **QR Code exclusivo**, assim como os pátios, permitindo identificação e rastreamento eficiente.

A **API** integra os dados capturados pelos dispositivos IoT com uma estrutura de armazenamento adequada:

* **Banco NoSQL**: dados de IoT em tempo real.
* **Banco relacional**: dados estruturados (usuários, motos, contratos, pátios etc).

Essa integração permite o acompanhamento via aplicativo, promovendo **eficiência, segurança e organização**. O sistema também gerencia cadastro/edição de dados, autenticação/autorizacão, gestão de permissões e dashboards com relatórios para tomada de decisão.

## Autores

### Turma 2TDSR - Análise e Desenvolvimento de Sistemas

* Daniel Saburo Akiyama - RM 558263
* Danilo Correia e Silva - RM 557540
* João Pedro Rodrigues da Costa - RM 558199

## ⚙️ Instalação do Projeto

### Requisitos

* Java SDK 21
* (Opcional) IntelliJ IDEA ou Eclipse

### Configuração

1. Edite o arquivo `src/main/resources/application.properties` com sua string de conexão Oracle:

```properties
spring.datasource.username=SEU_USUARIO_AQUI
spring.datasource.password=SUA_SENHA_AQUI
```

### Rodar o Projeto

#### Opção 1: Usando Maven direto

```bash
./mvnw spring-boot:run
```

Ou, se já tiver Maven instalado:

```bash
mvn spring-boot:run
```

#### Opção 2: Usando IDE (Eclipse / IntelliJ)

Importe o projeto como Maven Project e execute a classe principal com `@SpringBootApplication`.

#### Opção 3: Compilar e rodar .jar

1. Gerar o `.jar`:

```bash
./mvnw clean package
```

Ou:

```bash
mvn clean package
```

2. Rodar o `.jar` com Java:

```bash
java -jar target/mottracker-0.0.1-SNAPSHOT.jar
```

### Acessar a API

* A API estará acessível em: [http://localhost:8080](http://localhost:8080)
* Documentação Swagger UI: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

## Rotas da API

A seguir estão listadas as principais rotas disponíveis na API do projeto Mottracker, agrupadas por domínio:

### Autenticação

| Método | Rota              | Descrição          |
| ------ | ----------------- | ------------------ |
| POST   | `/usuarios/login` | Autenticar usuário |

---

### Usuários

| Método | Rota             | Descrição                |
| ------ | ---------------- | ------------------------ |
| GET    | `/usuarios`      | Listar todos os usuários |
| GET    | `/usuarios/{id}` | Buscar usuário por ID    |
| POST   | `/usuarios`      | Cadastrar novo usuário   |
| PUT    | `/usuarios/{id}` | Atualizar um usuário     |
| DELETE | `/usuarios/{id}` | Excluir usuário          |

---

### Telefones

| Método | Rota                 | Descrição                          |
| ------ | -------------------- | ---------------------------------- |
| GET    | `/telefones`         | Listar todos os telefones          |
| GET    | `/telefones/{id}`    | Buscar telefone por ID             |
| GET    | `/telefones/usuario` | Buscar telefones por ID de usuário |
| GET    | `/telefones/tipo`    | Buscar telefones por tipo          |
| GET    | `/telefones/numero`  | Buscar telefones por número        |
| POST   | `/telefones`         | Cadastrar novo telefone            |
| PUT    | `/telefones/{id}`    | Atualizar telefone                 |
| DELETE | `/telefones/{id}`    | Excluir telefone                   |

---

### Motos

| Método | Rota                           | Descrição                       |
| ------ | ------------------------------ | ------------------------------- |
| GET    | `/motos`                       | Listar todas as motos           |
| GET    | `/motos/{id}`                  | Buscar moto por ID              |
| GET    | `/motos/placa/{placa}`         | Buscar moto por placa           |
| GET    | `/motos/estado`                | Listar motos por estado         |
| GET    | `/motos/contrato/{contratoId}` | Listar motos por ID de contrato |
| POST   | `/motos`                       | Cadastrar nova moto             |
| PUT    | `/motos/{id}`                  | Atualizar moto                  |
| DELETE | `/motos/{id}`                  | Excluir moto                    |

---

### Pátios

| Método | Rota                  | Descrição                                   |
| ------ | --------------------- | ------------------------------------------- |
| GET    | `/patios`             | Listar todos os pátios                      |
| GET    | `/patios/{id}`        | Buscar pátio por ID                         |
| GET    | `/patios/nome`        | Buscar pátio por nome                       |
| GET    | `/patios/disponiveis` | Pátios com motos disponíveis                |
| GET    | `/patios/data-before` | Buscar pátios cadastrados antes de uma data |
| GET    | `/patios/data-after`  | Buscar pátios cadastrados após uma data     |
| POST   | `/patios`             | Cadastrar novo pátio                        |
| PUT    | `/patios/{id}`        | Atualizar dados do pátio                    |
| DELETE | `/patios/{id}`        | Excluir pátio                               |

---

### Contratos

| Método | Rota                             | Descrição                                 |
| ------ | -------------------------------- | ----------------------------------------- |
| GET    | `/contratos`                     | Listar todos os contratos                 |
| GET    | `/contratos/{id}`                | Buscar contrato por ID                    |
| GET    | `/contratos/validos`             | Listar contratos válidos                  |
| GET    | `/contratos/ativos`              | Listar contratos ativos                   |
| GET    | `/contratos/usuario/{usuarioId}` | Listar contratos por usuário              |
| GET    | `/contratos/moto/{motoId}`       | Listar contratos por moto                 |
| GET    | `/contratos/renovacao`           | Buscar contratos com renovação automática |
| GET    | `/contratos/entrada`             | Buscar contratos por data de entrada      |
| POST   | `/contratos`                     | Criar contrato                            |
| PUT    | `/contratos/{id}`                | Atualizar contrato                        |
| DELETE | `/contratos/{id}`                | Deletar contrato                          |

---

### Layouts de Pátio

| Método | Rota                            | Descrição                            |
| ------ | ------------------------------- | ------------------------------------ |
| GET    | `/layoutpatios`                 | Listar todos os layouts de pátio     |
| GET    | `/layoutpatios/{id}`            | Buscar layout por ID                 |
| GET    | `/layoutpatios/patio/{patioId}` | Buscar layout por ID do pátio        |
| GET    | `/layoutpatios/data-criacao`    | Buscar layouts por intervalo de data |
| POST   | `/layoutpatios`                 | Criar layout de pátio                |
| PUT    | `/layoutpatios/{id}`            | Atualizar layout                     |
| DELETE | `/layoutpatios/{id}`            | Deletar layout                       |

---

### Câmeras

| Método | Rota                       | Descrição                               |
| ------ | -------------------------- | --------------------------------------- |
| GET    | `/cameras`                 | Listar todas as câmeras                 |
| GET    | `/cameras/{id}`            | Buscar câmera por ID                    |
| GET    | `/cameras/status/{status}` | Buscar por status (ATIVA, INATIVA etc.) |
| GET    | `/cameras/nome/{nome}`     | Buscar por nome                         |
| POST   | `/cameras`                 | Cadastrar câmera                        |
| PUT    | `/cameras/{id}`            | Atualizar câmera                        |
| DELETE | `/cameras/{id}`            | Excluir câmera                          |

---

### Permissões

| Método | Rota                    | Descrição            |
| ------ | ----------------------- | -------------------- |
| GET    | `/permissoes`           | Listar permissões    |
| GET    | `/permissoes/{id}`      | Buscar por ID        |
| GET    | `/permissoes/nome`      | Buscar por nome      |
| GET    | `/permissoes/descricao` | Buscar por descrição |
| POST   | `/permissoes`           | Criar nova permissão |
| PUT    | `/permissoes/{id}`      | Atualizar permissão  |
| DELETE | `/permissoes/{id}`      | Excluir permissão    |

---

### Usuário-Permissões

| Método | Rota                                                              | Descrição                              |
| ------ | ----------------------------------------------------------------- | -------------------------------------- |
| GET    | `/usuario-permissoes`                                             | Listar todas as permissões de usuários |
| GET    | `/usuario-permissoes/usuario`                                     | Permissões por ID de usuário           |
| GET    | `/usuario-permissoes/permissao`                                   | Permissões por ID da permissão         |
| GET    | `/usuario-permissoes/{usuarioId}/{permissaoId}`                   | Buscar permissão específica            |
| GET    | `/usuario-permissoes/usuario/{usuarioId}/permissao/{permissaoId}` | Buscar por ID composto                 |
| POST   | `/usuario-permissoes`                                             | Criar permissão para usuário           |
| PUT    | `/usuario-permissoes/{usuarioId}/{permissaoId}`                   | Atualizar permissão de usuário         |
| DELETE | `/usuario-permissoes/{usuarioId}/{permissaoId}`                   | Excluir permissão                      |

---

### QR Codes

| Método | Rota                     | Descrição                |
| ------ | ------------------------ | ------------------------ |
| GET    | `/qrcodes`               | Listar todos os QR Codes |
| GET    | `/qrcodes/{id}`          | Buscar QR Code por ID    |
| GET    | `/qrcodes/identificador` | Buscar por identificador |
| GET    | `/qrcodes/posx`          | Buscar por posição X     |
| GET    | `/qrcodes/posy`          | Buscar por posição Y     |
| GET    | `/qrcodes/layout`        | Listar por ID do layout  |
| POST   | `/qrcodes`               | Criar novo QR Code       |
| PUT    | `/qrcodes/{id}`          | Atualizar QR Code        |
| DELETE | `/qrcodes/{id}`          | Excluir QR Code          |

## Implementações Futuras

- DTO's para respostas, não adicionamos no momento porque não planejamos quais dados da API vão ser necessários para o Front
- Spring Security e criptografia de senha
- Implementação do NoSQL (MongoDB)
- Lógica para alterar dados das tabelas a partir do NoSQL
- Otimizar requisições do banco de dados

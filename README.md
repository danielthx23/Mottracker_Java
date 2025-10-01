# Mottracker - Sistema de Gestão de Motocicletas

## Descrição do Projeto

O **Mottracker** é um sistema completo de gestão de motocicletas desenvolvido para o **Challenge da empresa Mottu**, FIAP 2025. O projeto resolve o problema de organização e localização das motos nos pátios da Mottu, facilitando o monitoramento em tempo real através de uma aplicação web moderna.

### Funcionalidades Principais

- **Gestão de Usuários**: Sistema completo de cadastro, autenticação e controle de permissões
- **Gestão de Motos**: Controle de frota com estados, localização e condições
- **Gestão de Pátios**: Administração de locais de estacionamento com layouts e câmeras
- **Gestão de Contratos**: Sistema de locação com renovação automática
- **Dashboard Interativo**: Relatórios e métricas em tempo real
- **Sistema de Permissões**: 4 níveis de acesso (ADMIN, GERENTE, OPERADOR, USER)

## Tecnologias Utilizadas

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 3.4.5** - Framework principal
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **Thymeleaf** - Engine de templates para frontend
- **Flyway** - Controle de versão do banco de dados
- **Oracle Database** - Banco de dados relacional
- **Maven** - Gerenciamento de dependências

### Frontend
- **Bootstrap 5.3.0** - Framework CSS
- **Thymeleaf** - Templates dinâmicos
- **JavaScript** - Interatividade
- **Font Awesome** - Ícones

## Autores

### Turma 2TDSR - Análise e Desenvolvimento de Sistemas

- **Daniel Saburo Akiyama** - RM 558263
- **Danilo Correia e Silva** - RM 557540  
- **João Pedro Rodrigues da Costa** - RM 558199

## Instalação e Configuração

### Pré-requisitos

- **Java SDK 21** ou superior
- **Oracle Database** (acesso via FIAP)
- **Maven 3.6+** (opcional, projeto inclui Maven Wrapper)
- **IDE** (IntelliJ IDEA, Eclipse ou VS Code)

### Configuração do Banco de Dados

1. **Configure as variáveis de ambiente** criando um arquivo `.env` na raiz do projeto:

```properties
ORACLEHOST=oracle.fiap.com.br
ORACLEPORT=1521
ORACLEDATABASE=ORCL
ORACLEUSER=SEU_USUARIO_AQUI
ORACLEPASSWORD=SUA_SENHA_AQUI
```

2. **Habilite o Flyway** editando `src/main/resources/application.properties`:

```properties
# Flyway Configuration
spring.flyway.enabled=true
```

### Executando o Projeto

#### Opção 1: Maven Wrapper (Recomendado)
```bash
# Windows
./mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

#### Opção 2: Maven Instalado
```bash
mvn spring-boot:run
```

#### Opção 3: IDE
1. Importe o projeto como **Maven Project**
2. Execute a classe `MottrackerApplication.java`

#### Opção 4: JAR Executável
```bash
# Compilar
./mvnw clean package

# Executar
java -jar target/Mottracker-0.0.1-SNAPSHOT.jar
```

## Acesso à Aplicação

Após executar o projeto, acesse:

- **Aplicação Web**: [http://localhost:8080](http://localhost:8080)
- **Login**: Use as credenciais criadas pelo Flyway
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Credenciais de Acesso

O sistema vem pré-configurado com usuários de teste:

| Email | Senha | Perfil | Descrição |
|-------|-------|--------|-----------|
| admin@mottu.com | admin123 | ADMIN | Acesso total ao sistema |
| gerente@mottu.com | admin123 | GERENTE | Gestão de operações |
| operador@mottu.com | admin123 | OPERADOR | Operações de pátio |
| usuario@mottu.com | admin123 | USER | Usuário comum |

### Sistema de Setup e Inicialização

O Mottracker inclui um sistema completo de setup para facilitar a configuração inicial:

#### **Setup de Permissões (/setup/init)**
- **URL**: [http://localhost:8080/setup/init](http://localhost:8080/setup/init)
- **Função**: Cria automaticamente todas as permissões do sistema
- **Permissões criadas**:
  - ADMIN - Administrador do sistema
  - GERENTE - Gerente de operações  
  - OPERADOR - Operador de pátio
  - USER - Usuário comum
- **Associação automática**: Todas as permissões são associadas aos usuários existentes
- **Uso**: Execute após a primeira instalação para configurar o sistema

#### **Criação de Usuário Admin (/admin/create-admin)**
- **URL**: [http://localhost:8080/admin/create-admin](http://localhost:8080/admin/create-admin)
- **Função**: Cria um novo usuário com todas as permissões
- **Características**:
  - Acesso público (não requer autenticação)
  - Criação de usuário com perfil ADMIN completo
  - Associação automática de todas as permissões
  - Criptografia automática da senha
- **Uso**: Para criar o primeiro administrador do sistema

#### **Fluxo de Configuração Recomendado**
1. **Primeira execução**: Acesse `/setup/init` para criar permissões
2. **Criar admin**: Use `/admin/create-admin` para criar usuário administrador
3. **Login**: Faça login com as credenciais criadas
4. **Dashboard**: Acesse o sistema completo via dashboard

## Arquitetura do Sistema

### Estrutura do Banco de Dados

O sistema utiliza **4 migrações Flyway** para versionamento:

1. **V1__Create_tables.sql** - Criação das tabelas principais
2. **V2__Insert_initial_permissions.sql** - Inserção das permissões
3. **V3__Insert_initial_data.sql** - Dados iniciais e usuários de teste
4. **V4__Create_indexes_and_constraints.sql** - Índices e constraints

### Sistema de Permissões

O sistema implementa **4 níveis de acesso**:

- **ADMIN**: Acesso total (usuários, permissões, relatórios)
- **GERENTE**: Gestão de operações (contratos, relatórios)
- **OPERADOR**: Operações de pátio (motos, pátios)
- **USER**: Acesso básico (visualizar motos, contratos próprios)

### Frontend com Thymeleaf

- **Templates Fragmentados**: Header, sidebar e footer reutilizáveis
- **Segurança Integrada**: Controle de acesso baseado em roles
- **Responsivo**: Interface adaptável para diferentes dispositivos
- **Validação**: Formulários com validação client-side e server-side

## Funcionalidades Implementadas

#### 1. **Thymeleaf**
- Páginas HTML completas para CRUD de todas as entidades
- Fragmentos reutilizáveis (header, sidebar, footer)
- Integração com Spring Security
- Templates responsivos com Bootstrap

#### 2. **Flyway**
- Configuração completa do Flyway
- 4 migrações de banco de dados
- Versionamento automático
- Dados iniciais e usuários de teste

#### 3. **Spring Security**
- Autenticação via formulário
- 4 tipos de usuário com permissões diferentes
- Proteção de rotas baseada em roles
- Criptografia de senhas com BCrypt

#### 4. **Funcionalidades Completas**
- **Fluxo de Autenticação**: Login/logout com controle de sessão
- **Fluxo de Gestão de Motos**: CRUD completo com validações
- **Fluxo de Gestão de Contratos**: Criação, edição e controle de status
- **Sistema de Setup**: Inicialização automática de permissões
- **Criação de Admin**: Interface para criar usuários administradores
- **Validações**: Formulários com validação Bean Validation
- **Dashboard**: Métricas e estatísticas em tempo real

## Estrutura do Projeto

```
src/main/java/br/com/fiap/Mottracker/
├── configuration/          # Configurações (Security, Database, Swagger)
├── controller/            # Controllers REST e Web
├── dto/                   # Data Transfer Objects
├── enums/                 # Enumeradores
├── exception/             # Tratamento de exceções
├── model/                 # Entidades JPA
├── repository/            # Repositórios JPA
└── service/               # Lógica de negócio

src/main/resources/
├── db/migration/          # Scripts Flyway
├── static/               # CSS, JS, imagens
└── templates/            # Templates Thymeleaf
    ├── shared/           # Fragmentos reutilizáveis
    ├── auth/            # Páginas de autenticação
    ├── dashboard.html   # Dashboard principal
    └── [entidades]/     # Páginas por entidade
```

## Fluxos Principais

### 1. **Fluxo de Autenticação**
1. Usuário acessa `/login`
2. Sistema valida credenciais
3. Redirecionamento baseado no perfil
4. Dashboard personalizado por role

### 2. **Fluxo de Gestão de Motos**
1. Listagem paginada de motos
2. Criação com validações
3. Edição com dados pré-carregados
4. Exclusão com confirmação
5. Controle de estado e localização

### 3. **Fluxo de Gestão de Contratos**
1. Criação de contratos vinculando usuário e moto
2. Controle de datas e valores
3. Renovação automática
4. Relatórios por usuário

## Testes e Validações

### Validações Implementadas
- **Email**: Formato válido e único
- **CPF**: Formato e unicidade
- **Senha**: Criptografia BCrypt
- **Datas**: Validação de períodos
- **Campos Obrigatórios**: Validação Bean Validation

### Dados de Teste
- 4 usuários com diferentes perfis
- 2 pátios de exemplo
- 3 motos com diferentes estados
- Permissões pré-configuradas

## Melhorias Futuras

- [ ] Integração com MongoDB para dados IoT
- [ ] API REST completa com documentação
- [ ] Testes automatizados
- [ ] Cache com Redis
- [ ] Notificações em tempo real
- [ ] Relatórios avançados com gráficos

## Solução de Problemas

### Problema: Erro de Conexão com Banco
```bash
# Verifique as variáveis de ambiente
# Certifique-se que o Oracle está acessível
# Teste a conexão manualmente
```

### Problema: Flyway não executa
```bash
# Habilite o Flyway no application.properties
spring.flyway.enabled=true
```

### Problema: Páginas não carregam
```bash
# Verifique se o Thymeleaf está configurado
# Limpe o cache do navegador
# Verifique os logs da aplicação
```

## Suporte

Para dúvidas ou problemas:

- **Email**: [seu-email@fiap.com.br]
- **GitHub**: [link-do-repositorio]
- **Documentação**: Swagger UI disponível em `/swagger-ui.html`

### Guia para Demonstração (Máx. 10 min)

#### **1. Login e Autenticação (2 min)**
- Demonstre login com diferentes perfis
- Mostre redirecionamento baseado em role
- Explique o sistema de permissões

#### **2. Dashboard e Navegação (2 min)**
- Mostre o dashboard com métricas
- Demonstre a navegação por sidebar
- Explique a responsividade da interface

#### **3. Gestão de Motos (3 min)**
- Listar motos disponíveis
- Criar nova moto com validações
- Editar moto existente
- Mostrar controle de estados

#### **4. Gestão de Contratos (2 min)**
- Criar contrato vinculando usuário e moto
- Mostrar relatórios por usuário
- Demonstrar controle de datas e valores

#### **5. Sistema de Permissões (1 min)**
- Mostrar acesso diferenciado por perfil
- Demonstrar proteção de rotas
- Explicar níveis de acesso

### Comandos para Execução Rápida

```bash
# 1. Habilitar Flyway (se necessário)
# Editar application.properties: spring.flyway.enabled=true

# 2. Executar aplicação
./mvnw spring-boot:run

# 3. Acessar aplicação
# http://localhost:8080

# 4. Configurar sistema (primeira execução)
# http://localhost:8080/setup/init - Criar permissões
# http://localhost:8080/admin/create-admin - Criar admin (opcional)

# 5. Login de teste
# Email: admin@mottu.com
# Senha: admin123
```

## Video Demonstração:

[![Thumbnail](https://img.youtube.com/vi/ydLuvVBRNfA/0.jpg)](https://www.youtube.com/watch?v=TPSpM418jjE)   


# Amigo de Pata API 🐾

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

Esta é a API robusta do projeto **Amigo de Pata**, um sistema desenvolvido para facilitar a gestão de adoções de animais para ONGs. A aplicação oferece uma solução completa para controle de pets, formulários de adoção, gestão de voluntários e integração com serviços de pagamento e armazenamento de mídia.

---

## 🚀 Funcionalidades

- **Gestão de Animais**: CRUD completo com suporte a filtros e paginação.
- **Sistema de Adoção**: Gerenciamento de formulários de interesse e rastreamento de status.
- **Autenticação e Segurança**: Implementação de JWT com chaves RSA (público/privada) e controle de acesso baseado em escopos (`ADMIN`, `USER`).
- **Gestão de Mídia**: Integração com **Cloudinary** para upload e armazenamento de fotos dos animais.
- **Pagamentos e Doações**: Integração com a API do **Stripe** para processamento de doações e webhooks.
- **Tratamento de Erros**: Handler global para respostas de erro padronizadas.
- **Mapeamento Eficiente**: Uso de **MapStruct** para conversão entre Entidades e DTOs.

---

## 🛠️ Tecnologias e Ferramentas

- **Linguagem:** Java 21
- **Framework:** Spring Boot 3.4.4
- **Segurança:** Spring Security & OAuth2 Resource Server (JWT)
- **Persistência:** Spring Data JPA & Hibernate
- **Banco de Dados:** PostgreSQL (Produção) e H2 (Testes)
- **Documentação de Mapeamento:** MapStruct
- **Utilitários:** Lombok, Dotenv Java
- **Integrações Externas:** Cloudinary (Imagens), Stripe (Pagamentos)
- **Containerização:** Docker & Docker Compose

---

## 📂 Estrutura do Projeto

O projeto segue a arquitetura em camadas padrão do ecossistema Spring:

- `config/`: Configurações de segurança, CORS, Cloudinary e filtros.
- `controller/`: Endpoints da API e exposição de recursos.
- `service/`: Regras de negócio e integrações.
- `repository/`: Interface de comunicação com o banco de dados.
- `model/`: Entidades de domínio da aplicação.
- `dto/`: Objetos de transferência de dados (Request/Response).
- `mapper/`: Interfaces MapStruct para conversão de objetos.
- `exceptions/`: Definições de exceções personalizadas e tratamento global.

---

## ⚙️ Como Executar

### Pré-requisitos
- JDK 21
- Maven 3.9+
- Docker (opcional)

### Configuração de Variáveis de Ambiente
Crie um arquivo `.env` na raiz do projeto com as seguintes chaves:

```env
DB_URL=jdbc:postgresql://localhost:5432/nome_do_banco
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha

JWT_PRIVATE_KEY=caminho_ou_chave_privada
JWT_PUBLIC_KEY=caminho_ou_chave_publica

CLOUDINARY_CLOUD_NAME=seu_cloud_name
CLOUDINARY_API_KEY=sua_api_key
CLOUDINARY_API_SECRET=sua_api_secret

STRIPE_API_KEY=sua_stripe_key
STRIPE_PUBLIC_KEY=sua_stripe_public_key
STRIPE_WEBHOOK_KEY=sua_webhook_key
```

### Rodando Localmente
```bash
# Clone o repositório
git clone https://github.com/GabrielNunes5/Amigo-de-Pata-Api.git

# Entre na pasta
cd Amigo-de-Pata-Api

# Compile e instale as dependências
mvn clean install

# Execute a aplicação
mvn spring-boot:run
```

---

## 🐳 Docker

Para rodar o projeto via Docker:

```bash
docker build -t amigo-de-pata-api .
docker run -p 8080:8080 amigo-de-pata-api
```

---

## 🧪 Testes

A aplicação conta com uma suíte de testes unitários e de integração:

```bash
mvn test
```

---

## 📝 Licença

Este projeto está sob a licença [MIT](LICENSE).

---
Desenvolvido com ❤️ para ajudar nossos amigos de quatro patas.

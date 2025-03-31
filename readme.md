# API de Adoção de Animais 🐾

API REST para gestão de animais resgatados em sistema de adoção de ONG.

## Visão Geral do Projeto
- **Stack**: Java 17 + Spring Boot 3.x
- **Banco de Dados**: Relacional (PostgreSQL)
- **Arquitetura**: Camadas (Controller-Service-Repository)
- **Principais Funcionalidades**:
    - Operações CRUD para animais
    - Atualizações parciais
    - Validação de dados
    - Rastreamento de status de adoção

## Esquema do Banco de Dados

### Tabela `animals`
| Coluna                     | Tipo         | Restrições               |
|----------------------------|--------------|---------------------------|
| animal_id                  | INTEGER      | PK, Auto-incremento       |
| animal_name                | VARCHAR(100) | Não nulo                  |
| animal_age                 | VARCHAR(50)  | Não nulo                  |
| animal_weight              | DOUBLE       | Não nulo                  |
| animal_sex                 | VARCHAR(10)  | Não nulo                  |
| animal_color               | VARCHAR(50)  |                           |
| animal_species             | VARCHAR(50)  |                           |
| animal_vaccines            | VARCHAR(255) |                           |
| animal_sized               | VARCHAR(50)  |                           |
| animal_neutered            | BOOLEAN      |                           |
| animal_special_conditions  | VARCHAR(255) |                           |
| animal_category            | VARCHAR(100) | Não nulo                  |
| animal_image_url           | VARCHAR(255) | Não nulo                  |
| animal_adopted             | BOOLEAN      | Padrão: false             |
| adopter_id                 | INTEGER      | FK (adopter.adopter_id)   |

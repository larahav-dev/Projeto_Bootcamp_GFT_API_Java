# 📦 API de Clientes - Projeto Bootcamp GFT

Esta API RESTful gerencia informações de clientes e realiza integração com o serviço [ViaCEP](https://viacep.com.br/) para consulta automática de dados de endereço com base no CEP informado.

## 🚀 Funcionalidades

- Listar todos os clientes
- Listar clientes com paginação e ordenação
- Buscar cliente por ID
- Buscar clientes por nome, cidade, estado ou faixa etária
- Criar novo cliente (com preenchimento automático de endereço via CEP)
- Atualizar cliente existente
- Remover cliente

## 🔗 Endpoints

| Método   | Endpoint                                     | Descrição                                                    |
| -------- | -------------------------------------------- | ------------------------------------------------------------ |
| `GET`    | `/clientes`                                  | Lista todos os clientes                                      |
| `GET`    | `/clientes/paginado`                         | Lista clientes com paginação e ordenação por `id`, `nome` ou `idade` |
| `GET`    | `/clientes/{id}`                             | Busca cliente pelo ID                                        |
| `GET`    | `/clientes/buscar?nome={nome}`               | Busca clientes pelo nome                                     |
| `GET`    | `/clientes/cidade?cidade={cidade}`           | Busca clientes pela cidade                                   |
| `GET`    | `/clientes/estado?estado={estado}`           | Busca clientes pelo estado                                   |
| `GET`    | `/clientes/faixa-etaria?min={min}&max={max}` | Busca clientes por faixa etária                              |
| `POST`   | `/clientes`                                  | Cria novo cliente (sem informar o campo `id`)                |
| `PUT`    | `/clientes/{id}`                             | Atualiza cliente existente (evitar alterar o campo `cep` diretamente) |
| `DELETE` | `/clientes/{id}`                             | Remove cliente pelo ID                                       |

## 📌 Observações

- O campo `cep` não deve ser atualizado diretamente via PUT, pois pode causar inconsistências. Essa funcionalidade será ajustada futuramente.
- Ao cadastrar um cliente, o campo `id` deve ser omitido.
- A ordenação no endpoint `/paginado` só é permitida pelos campos: `id`, `nome` e `idade`.

## 🧠 Integração com ViaCEP

Ao criar ou atualizar um cliente, o serviço `ClienteService` consome a API do ViaCEP para preencher automaticamente os dados de endereço (rua, bairro, cidade, estado) com base no CEP informado.

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate Validator
- Swagger/OpenAPI
- ViaCEP (API externa)
- Maven

## 📚 Documentação Swagger

A API está documentada com Swagger e pode ser acessada via:

```
http://localhost:8080/swagger-ui.html
```

# TerraSense API

A API TerraSense atua como camada de comunicação entre a plataforma de monitoramento agrícola inteligente e o banco de dados Oracle, permitindo o gerenciamento centralizado das informações relacionadas a usuários, propriedades rurais, plantações, dados climáticos coletados por sensores IoT, dados de satélite da NASA e alertas agrícolas.

Por meio da API, é possível cadastrar, consultar, atualizar e remover informações do sistema, permitindo que aplicações web, mobile ou outros serviços consumam os dados de forma padronizada.

A utilização da API centraliza as regras de negócio da solução, garante a integridade dos dados, facilita a manutenção do sistema e possibilita a integração entre diferentes componentes da plataforma TerraSense.

---

# Links do Projeto

* **Deploy:** https://terrasense-csjw.onrender.com
* **Swagger:** https://terrasense-csjw.onrender.com/swagger-ui.html
* **Vídeo de Apresentação:** placeholder
* **Repositório GitHub:** placeholder

---

# Integrantes

| Nome                          | RM       | Turma |
| ----------------------------- | -------- | ----- |
| Agatha Yie Won Yun            | RM561507 | 2TDSA |
| Ana Claudia Fernandes Martins | RM561190 | 2TDSR |
| Andre Rosa Colombo            | RM563112 | 2TDSA |
| Samantha Faruolo Galdi        | RM554794 | 2TDSA |
| Vitor Fria Dalmagro           | RM566052 | 2TDSA |

---

# Tecnologias Utilizadas

* Java 21
* Spring Boot 4.0.6
* Spring Data JPA
* Hibernate
* Oracle Database
* Maven
* Swagger
* Spring HATEOAS
* Lombok
* Bean Validation
* Render

---

# Arquitetura do Projeto

## Explicação das Camadas

### Controller

Responsável por disponibilizar os endpoints da aplicação, recebendo requisições HTTP e retornando respostas adequadas aos clientes da API.

### Service

Responsável pela implementação das regras de negócio, validações e processamento dos dados.

### Repository

Responsável pela comunicação com o banco de dados utilizando Spring Data JPA.

### DTO

Responsável pela transferência de dados entre cliente e servidor, evitando exposição direta das entidades.

### Model

Responsável pelo mapeamento das entidades do banco de dados através do JPA.

### Exception

Responsável pelo tratamento global e padronizado de exceções da aplicação.

### Config

Responsável pelas configurações gerais da aplicação, incluindo recursos do Spring HATEOAS.

---

# Estrutura do Projeto

```txt
src/main/java/br/com/terrasense
│
├── config
├── controller
├── dto
├── exception
├── model
├── repository
└── service
```

---

# Banco de Dados

O projeto utiliza o Oracle Database como banco de dados relacional principal.

## Principais Entidades

* Usuario
* Propriedade
* Plantacao
* DadosNasa
* DadosIotClima
* Alerta

## Relacionamentos

* Um usuário pode possuir várias propriedades.
* Uma propriedade pode possuir várias plantações.
* Uma plantação pode possuir vários registros de dados da NASA.
* Uma plantação pode possuir vários registros de dados climáticos provenientes de sensores IoT.
* Uma plantação pode possuir vários alertas agrícolas.

## Modelagem Avançada

* Relacionamentos JPA (`@OneToMany` e `@ManyToOne`)
* Objetos utilizando `@Embedded`
* Classes reutilizáveis utilizando `@Embeddable`
* Estrutura composta por múltiplas tabelas relacionadas

---

# Como Executar o Projeto

## Clonar o Repositório

```bash
git clone placeholder
```

## Acessar a Pasta do Projeto

```bash
cd placeholder
```

## Executar a Aplicação

```bash
./mvnw spring-boot:run
```

ou

```bash
mvn spring-boot:run
```

---

# Deploy

## URL Base

```txt
https://terrasense-csjw.onrender.com
```

---

# Swagger

## Swagger UI

```txt
https://terrasense-csjw.onrender.com/swagger-ui.html
```

---

# Endpoints da API

## Usuários

| Método | Endpoint         |
| ------ | ---------------- |
| POST   | `/usuarios`      |
| GET    | `/usuarios`      |
| GET    | `/usuarios/{id}` |
| PUT    | `/usuarios/{id}` |
| DELETE | `/usuarios/{id}` |

---

## Propriedades

| Método | Endpoint                            |
| ------ | ----------------------------------- |
| POST   | `/propriedades`                     |
| GET    | `/propriedades`                     |
| GET    | `/propriedades/{id}`                |
| GET    | `/propriedades/usuario/{idUsuario}` |
| GET    | `/propriedades/tipo`                |
| PUT    | `/propriedades/{id}`                |
| DELETE | `/propriedades/{id}`                |

---

## Plantações

| Método | Endpoint                                  |
| ------ | ----------------------------------------- |
| POST   | `/plantacoes`                             |
| GET    | `/plantacoes`                             |
| GET    | `/plantacoes/{id}`                        |
| GET    | `/plantacoes/propriedade/{idPropriedade}` |
| GET    | `/plantacoes/status`                      |
| GET    | `/plantacoes/tipo`                        |
| PUT    | `/plantacoes/{id}`                        |
| DELETE | `/plantacoes/{id}`                        |

---

## Dados NASA

| Método | Endpoint                              |
| ------ | ------------------------------------- |
| POST   | `/dados-nasa`                         |
| GET    | `/dados-nasa`                         |
| GET    | `/dados-nasa/{id}`                    |
| GET    | `/dados-nasa/plantacao/{idPlantacao}` |
| PUT    | `/dados-nasa/{id}`                    |
| DELETE | `/dados-nasa/{id}`                    |

---

## Dados IoT Clima

| Método | Endpoint                                   |
| ------ | ------------------------------------------ |
| POST   | `/dados-iot-clima`                         |
| GET    | `/dados-iot-clima`                         |
| GET    | `/dados-iot-clima/{id}`                    |
| GET    | `/dados-iot-clima/plantacao/{idPlantacao}` |
| PUT    | `/dados-iot-clima/{id}`                    |
| DELETE | `/dados-iot-clima/{id}`                    |

---

## Alertas

| Método | Endpoint                           |
| ------ | ---------------------------------- |
| POST   | `/alertas`                         |
| GET    | `/alertas`                         |
| GET    | `/alertas/{id}`                    |
| GET    | `/alertas/plantacao/{idPlantacao}` |
| GET    | `/alertas/status`                  |
| GET    | `/alertas/nivel`                   |
| PUT    | `/alertas/{id}`                    |
| DELETE | `/alertas/{id}`                    |

---

# Recursos Implementados

* Spring HATEOAS
* Paginação com `Pageable`
* Swagger/OpenAPI
* Bean Validation
* Tratamento global de exceções
* Oracle Database
* Deploy no Render

---

# Documentação da API

A documentação completa da API pode ser acessada através do Swagger UI disponibilizado pela aplicação.

---

# Disciplina

**Java Advanced — Global Solution**

**FIAP**

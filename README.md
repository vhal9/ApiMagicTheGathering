# ApiMagicTheGathering

# Repositório para projeto ApiMagicTheGathering.
![GitHub repo size](https://img.shields.io/github/repo-size/vhal9/rasmoo-ms-grade-curricular)
![GitHub top language](https://img.shields.io/github/languages/top/vhal9/rasmoo-ms-grade-curricular)


## Resumo

Este repositorio contém uma API para o controle de Cartas do jogo Magic - The Gathering 

A aplicação foi desenvolvida utilizando Java em conjunto do Framework Spring. 


## Dependências

- JDK 11
- Maven 3

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Security
- Lombok
- JUnit
- Mockito
- H2 Database

## Execução

Clone o projeto:

```
git clone https://github.com/vhal9/ApiMagicTheGathering.git
```

## Importação IntelliJ

```
File -> Open -> Selecione a raiz do projeto
```

- Após importar o projeto no Intellij, selecione a opção Run do mesmo.

## Execução pelo terminal utilizando o java 11

```shell script
mvn spring-boot:run 
```

## Para executar a suíte de testes basta executar o seguinte comando:

```shell script
mvn clean test
```

Acesso a aplicação pelo link:
```
http://localhost:8081/magic
```

## Features

- Disponibilizar API no heroku
- Migrar para o banco PostgreSQL
- Adicionar Documentação com Swagger
- Adicionar validação personalizadas para atributos Enum.
- Adicionar testes unitários para a controller da entidade Pack
- Adicionar testes unitários para a controller e para a service da entidade Usuário


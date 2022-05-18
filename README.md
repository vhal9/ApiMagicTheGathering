# ApiMagicTheGathering

# Repositório para projeto ApiMagicTheGathering.
![GitHub repo size](https://img.shields.io/github/repo-size/vhal9/rasmoo-ms-grade-curricular)
![GitHub top language](https://img.shields.io/github/languages/top/vhal9/rasmoo-ms-grade-curricular)


## Resumo

Este repositorio contém uma API para o controle de Cartas do jogo Magic - The Gathering 

A aplicação foi desenvolvida utilizando Java em conjunto do Framework Spring. 


## Dependências

- JDK 11

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Security
- Maven
- Lombok
- JUnit
- Mockito
- PostgreSQL 10.20

## Execução

Clone o projeto:

```
git clone https://github.com/vhal9/ApiMagicTheGathering.git
```

## Configuração do banco de dados
É necessário criar um banco de dados no postgres e alterar algumas informações no seguinte arquivo:
```
src/main/resources/application.yml
```
- Ao criar o banco, o nome dado ao banco deve ser inserido no item url, substituindo a palavra banco
- Também é necessário alterar o username e o password, substituindo respectivamente as palavras user e password pelas suas credenciais do postgres
```
# Banco Postgresql
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/banco
    username: user
    password: password
    driver-class-name: org.postgresql.Driver
```
A partir disso a aplicação será capaz de se conectar ao banco postgres

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

- Disponibilizar API na nuvem
- Migrar para o banco PostgreSQL ✔️
- Adicionar Documentação com Swagger
- Adicionar validação personalizadas para atributos Enum.
- Adicionar testes unitários para a controller da entidade Pack
- Adicionar testes unitários para a controller e para a service da entidade Usuário


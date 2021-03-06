# ApiMagicTheGathering

# Repositório para projeto ApiMagicTheGathering.
![GitHub repo size](https://img.shields.io/github/repo-size/vhal9/rasmoo-ms-grade-curricular)
![GitHub top language](https://img.shields.io/github/languages/top/vhal9/rasmoo-ms-grade-curricular)


## Resumo

Essa documentação irá apresentar quais são as entidades envolvidas na solução, os endpoints da api, as dependências e tecnologias utilizadas, como importar e executar a aplicação localmente, a release disponibilizada no Heroku e uma seção de features sobre algumas possíveis evoluções para esta aplicação.

## Descrição

Este repositorio contém uma API para o controle de Cartas do jogo Magic - The Gathering 

A aplicação foi desenvolvida utilizando Java em conjunto do Framework Spring e armazenando os dados no banco PostgreSQL. 
 

### Entidades

Nesse projeto temos basicamente 3 entidades, são elas:

- **User:** Responsável por manter os dados do usuário e fornecer os dados para login.
- **Card:** Responsável por manter os dados das cartas.
- **Pack:** Responsável por manter os dados das listas de cartas dos usuários.

### Endpoints

Para cada entidade temos alguns endpoints disponibilizados para o fluxo dos seus dados. Como método de autenticação foi utilizado o **HttpBasic**, com isso é necessário informar o usuário e senha em cada endpoint para acessar os dados, com exceção ao endpoint **create user**, responsável pela criação do usuário.

- Para a entidade User têm-se 3 endpoints:
  - find by id: Responsável por encontrar um usuário pelo id.
  - list all: Lista todos os usuários
  - create user: Cria um usuário

- Para a entidade Card têm-se 4 endpoints:
  - create card: Responsável pela criação de uma carta.
  - list all: Lista todas as cartas.
  - change price: Altera o preço de uma carta. É necessário que a carta esteja vinculada ao usuário logado.
  - change number of cards of the same type: Altera o atributo número de cartas do mesmo tipo. É necessário que a carta esteja vinculada ao usuário logado.

- Para a entidade Pack têm-se 6 endpoints:
  - list all: Lista todas os packs e suas cartas. Permite a ordenação das cartas utilizando o paramêtro **order_field**, aceitando a ordenação pelos campos **name** e **price**.
  - find by id: Responsável por encontrar um pack pelo campo id.
  - create pack: Cria um novo pack, aceitando enviar uma lista de id's de cartas existentes e vinculadas ao usuário logado.
  - add card to pack: Responsável por criar uma nova carta e adiciona-la a um pack existente, referenciado pelo id na url.
  - add cards by id: Responsável por adicionar diversas cartas existentes a um pack, referenciando o pack pelo id na url e os id's das cartas referenciados no corpo da requisição.
  - remove card: Responsável por remover uma carta de um pack, a partir de seus id's, ambos referenciados na requisição.
 

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
- HttpBasic
- Heroku

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
## Heroku

A aplicação foi disponibilizada no heroku. Para isso, foi necessário alterar a versão do Java para a 8 e realizar alguns ajustes. Com isso a versão da aplicação que se encontra no heroku está na branch **development**. Foi adicionado uma feature ao fim dessa documentação para trazer essa alteração para a master, além de configurar os arquivos **application.yml** para executar ambientes diferentes.

A aplicação se encontra no link: 
```
https://zappts-magic-the-gathering.herokuapp.com/magic/api/{recurso}
```

Já se tem um usuário cadastrado para realizar login:
  - username: User2
  - password: 12345312

Utilize o link de exemplo para uma consulta aos usuários cadastrados:
```
https://zappts-magic-the-gathering.herokuapp.com/magic/api/users
```


### Postman

É possível importar duas coleções com as requisições configuradas para cada endpoint. Uma coleção para utilizar executando o projeto localmente e a outra para executar utilizando o recurso da aplicação no heroku. 
- Encontre os arquivos **Magic Api.postman_collection.json** e **Magic Api - heroku.postman_collection.json** na raiz do projeto.
- Com o postman aberto, seleciona um Workspace ou crie um novo.
- Selecione as opções importar -> File -> Uploads File e selecione os arquivos.
- Dessa forma, o postman deve ficar como mostrado na figura abaixo.
- Todas as requisições estão com a autenticação http basic configurada para o usuário indicado na requisição do método **create user**. É necessário executar essa requisição antes de todas as outras. É possivel criar mais de um usuário, basta alterar o username no corpo dessa requisição e alterá-lo também na aba **Authentication** de cada requisição para utilizá-lo.

![image](https://user-images.githubusercontent.com/11220622/169059816-85ca29ea-e622-473e-913e-4ac7588fba12.png)


## Features

- Disponibilizar API na nuvem ✔️
- Migrar para o banco PostgreSQL ✔️
- Adicionar Documentação com Swagger
- Adicionar validação personalizadas para atributos Enum
- Adicionar testes unitários para a controller da entidade Pack
- Adicionar testes unitários para a controller e para a service da entidade Usuário
- Aumentar a cobertura de testes na service da entidade Pack
- Evoluir autenticação para OAuth 2.0 em conjunto com JWT
- Trazer o codigo da branch development para a master com os ajustes para o heroku e adicionar arquivos de configuração para cada ambiente (local e heroku).


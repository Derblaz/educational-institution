# educational-institution

Projeto usado em fase de recrutamento em uma instituição de ensino

## Ferramentas necessárias

- Java 17
- Node v16.16.0
- Docker

## Como executar?

1. Clonar o repositório:

```sh
git clone https://github.com/Derblaz/educational-institution.git
```

2. Acessar a pasta educational-institution-api:

```shell
cd educational-institution-api
```

3. Executar o build do projeto

```shell
./gradlew clean bootjar
```

4. Subir banco e aplicação com Docker:

```shell
docker-compose up -d
```

5. Acessar a pasta educational-institution-web:

```shell
cd ..\educational-institution-web
```

6. Instalar os pacotes da aplicação:

```shell
npm install
```

7. Rodar a aplicação:

```shell
npm start
```

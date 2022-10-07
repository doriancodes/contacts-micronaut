## Contacts API
Simple reactive CRUD application based on micronaut, project reactor, r2dbc

### Run locally

- on a terminal run `docker-compose up`
- run the application: `./gradlew run`

### Usage

#### Create a new contact:
```shell
curl --location --request POST 'http://localhost:8080/contacts/add' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Doriano",
    "lastName": "Milano",
    "email": "dorian@codes.com",
    "phone": "+1234345345"
}'
```

#### Get the contact list

```shell
curl --location --request GET 'http://localhost:8080/contacts'
```

#### Get single contact
```shell
curl --location --request GET 'http://localhost:8080/contacts/{id}'
```

#### Update contact
```shell
curl --location --request PUT 'http://localhost:8080/contacts/{id}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Doriano",
    "lastName": "Codici",
    "email": "dorian@codes.com",
    "phone": "+1234345345"
}'
```

#### Delete contact
```shell
curl --location --request DELETE 'http://localhost:8080/contacts/{id}'
```

### TODO

- Add tests
- Add swagger docs

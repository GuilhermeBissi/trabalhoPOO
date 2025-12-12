### 1. Configurar o Banco de Dados
CREATE DATABASE aula;

### 2. Configurar Credenciais

spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
server.port=8081


## ▶️ Executar o Projeto

```bash
# Dar permissão de execução (Linux/Mac)
chmod +x mvnw

# Executar
./mvnw spring-boot:run
```

A API estará disponível em: **http://localhost:8081**

---

## Endpoints da API

### 1. Passageiros (`/api/passengers`)

####  Criar Passageiro
```http
POST http://localhost:8081/api/passengers
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@email.com"
}
```

#### Listar Todos os Passageiros
```http
GET http://localhost:8081/api/passengers
```

#### Buscar Passageiro por ID
```http
GET http://localhost:8081/api/passengers/1
```

#### ✏️ Atualizar Passageiro
```http
PUT http://localhost:8081/api/passengers/1
Content-Type: application/json

{
  "name": "João Silva Atualizado",
  "email": "joao.novo@email.com"
}
```

#### 🗑️ Deletar Passageiro
```http
DELETE http://localhost:8081/api/passengers/1
```

---

### 2. Motoristas (`/api/drivers`)

#### 📝 Criar Motorista
```http
POST http://localhost:8081/api/drivers
Content-Type: application/json

{
  "name": "Carlos Souza",
  "birthDate": "1985-03-20",
  "numero": 7
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "name": "Carlos Souza",
  "birthDate": "1985-03-20",
  "numero": 7
}
```

**Validações:**
- `name`: obrigatório, entre 2 e 50 caracteres
- `numero`: deve ser ímpar

#### 📋 Listar Todos os Motoristas
```http
GET http://localhost:8081/api/drivers
```

#### 🔍 Buscar Motorista por ID
```http
GET http://localhost:8081/api/drivers/1
```

#### ✏️ Atualizar Motorista (Completo)
```http
PUT http://localhost:8081/api/drivers/1
Content-Type: application/json

{
  "name": "Carlos Souza Atualizado",
  "birthDate": "1985-03-20",
  "numero": 9
}
```

#### ✏️ Atualizar Motorista (Parcial)
```http
PATCH http://localhost:8081/api/drivers/1
Content-Type: application/json

{
  "name": "Novo Nome"
}
```

#### 🗑️ Deletar Motorista
```http
DELETE http://localhost:8081/api/drivers/1
```

---

### 3. Viagens (`/api/travels`)

#### 📝 Criar Solicitação de Viagem
```http
POST http://localhost:8081/api/travels
Content-Type: application/json

{
  "passengerId": 1,
  "origin": "Centro",
  "destination": "Aeroporto"
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "passenger": {
    "id": 1,
    "name": "João Silva",
    "email": "joao@email.com"
  },
  "driver": null,
  "origin": "Centro",
  "destination": "Aeroporto",
  "status": "CREATED",
  "creationDate": "2025-12-12T10:30:00",
  "acceptanceDate": null
}
```

**Status possíveis:**
- `CREATED` - Viagem criada, aguardando motorista
- `ACCEPTED` - Viagem aceita por motorista
- `REFUSED` - Viagem recusada por motorista
- `FINISHED` - Viagem finalizada

#### ✅ Motorista Aceita Viagem
```http
PATCH http://localhost:8081/api/travels/1/accept?driverId=1
```

**Regras:**
- Só pode aceitar viagens com status `CREATED`
- Retorna erro 400 se já estiver `ACCEPTED` ou `FINISHED`
- Retorna erro 404 se viagem ou motorista não existir

**Resposta (200 OK):**
```json
{
  "id": 1,
  "passenger": {
    "id": 1,
    "name": "João Silva",
    "email": "joao@email.com"
  },
  "driver": {
    "id": 1,
    "name": "Carlos Souza",
    "birthDate": "1985-03-20",
    "numero": 7
  },
  "origin": "Centro",
  "destination": "Aeroporto",
  "status": "ACCEPTED",
  "creationDate": "2025-12-12T10:30:00",
  "acceptanceDate": "2025-12-12T10:35:00"
}
```

#### ❌ Motorista Recusa Viagem
```http
PATCH http://localhost:8081/api/travels/1/refuse?driverId=1
```

**Regras:**
- Só pode recusar viagens com status `CREATED`

#### 🏁 Finalizar Viagem
```http
PATCH http://localhost:8081/api/travels/1/finish
```

**Regras:**
- Só pode finalizar viagens com status `ACCEPTED`

#### 📋 Listar Todas as Viagens
```http
GET http://localhost:8081/api/travels
```

#### 🔍 Buscar Viagem por ID
```http
GET http://localhost:8081/api/travels/1
```

#### 👤 Listar Viagens por Passageiro
```http
GET http://localhost:8081/api/travels/passenger/1
```

#### 🚗 Listar Viagens por Motorista
```http
GET http://localhost:8081/api/travels/driver/1
```

#### 📊 Listar Viagens por Status
```http
GET http://localhost:8081/api/travels/status/CREATED
```

Valores válidos: `CREATED`, `ACCEPTED`, `REFUSED`, `FINISHED`

---

## 🎯 Fluxo Completo de Teste

### 1️⃣ Criar Passageiro
```http
POST http://localhost:8081/api/passengers
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@email.com"
}
```
✅ Resposta: ID do passageiro = 1

### 2️⃣ Criar Motorista
```http
POST http://localhost:8081/api/drivers
Content-Type: application/json

{
  "name": "Carlos Souza",
  "birthDate": "1985-03-20",
  "numero": 7
}
```
✅ Resposta: ID do motorista = 1

### 3️⃣ Passageiro Solicita Viagem
```http
POST http://localhost:8081/api/travels
Content-Type: application/json

{
  "passengerId": 1,
  "origin": "Centro",
  "destination": "Aeroporto"
}
```
✅ Resposta: Viagem criada com status `CREATED`

### 4️⃣ Motorista Aceita a Viagem
```http
PATCH http://localhost:8081/api/travels/1/accept?driverId=1
```
✅ Resposta: Status muda para `ACCEPTED`

### 5️⃣ Finalizar Viagem
```http
PATCH http://localhost:8081/api/travels/1/finish
```
✅ Resposta: Status muda para `FINISHED`

### 6️⃣ Verificar Todas as Viagens
```http
GET http://localhost:8081/api/travels
```

## 📝 Validações

### Passageiro
- `name`: obrigatório, entre 3 e 50 caracteres
- `email`: obrigatório, formato válido de e-mail

### Motorista
- `name`: obrigatório, entre 2 e 50 caracteres
- `numero`: deve ser número ímpar

### Viagem
- `passengerId`: obrigatório, passageiro deve existir
- `origin`: obrigatório
- `destination`: obrigatório

# Kaiburr Backend

## Run locally (docker)
docker compose up --build

Backend: http://localhost:8080
Mongo: mongodb://localhost:27017

## Endpoints
GET /tasks
GET /tasks/{id}
PUT /tasks  (body: Task JSON)
PUT /tasks/{id}/execute
DELETE /tasks/{id}

## Notes
- Allowed commands defined in application.yml under app.exec.allowedCommands
- CORS allowed for http://localhost:5173

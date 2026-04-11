# Smart Campus Sensor & Room Management API

## Overview
This project is a RESTful API built using JAX-RS for the Smart Campus coursework.

The API manages:
- Rooms
- Sensors
- Sensor Readings

This system uses in-memory storage with Java collections such as `LinkedHashMap` and `ArrayList`.
Because of that, data resets whenever the server restarts.

---

## Technology Used
- Java
- Maven
- JAX-RS (Jersey)
- Grizzly HTTP Server
- JSON (Jackson)

---

## Features Implemented

### Discovery
- `GET /api/v1`

### Rooms
- `GET /api/v1/rooms`
- `GET /api/v1/rooms/{roomId}`
- `POST /api/v1/rooms`
- `DELETE /api/v1/rooms/{roomId}`

### Sensors
- `GET /api/v1/sensors`
- `GET /api/v1/sensors?type=Temperature`
- `POST /api/v1/sensors`

### Sensor Readings
- `GET /api/v1/sensors/{sensorId}/readings`
- `POST /api/v1/sensors/{sensorId}/readings`

### Error Handling
- `409 Conflict` when deleting a room that still has sensors
- `422 Unprocessable Entity` when creating a sensor with an invalid room ID
- `403 Forbidden` when posting a reading to a sensor in maintenance mode
- `500 Internal Server Error` for unexpected runtime errors

### Logging
- Request logging
- Response status logging

---

## Project Structure
- `model` -> POJO classes
- `resource` -> API resource classes
- `store` -> in-memory data storage
- `exception` -> custom exception classes
- `mapper` -> exception mapper classes
- `filter` -> logging filter

---

## How to Run the Project

### Using NetBeans
1. Open the project in NetBeans
2. Wait for Maven dependencies to load
3. Right-click the project
4. Click **Run**
5. The server will start on:
   `http://localhost:8080`

### Base API URL
`http://localhost:8080/api/v1`

---

## Sample curl Commands

### 1. Discovery endpoint
```bash
curl http://localhost:8080/api/v1
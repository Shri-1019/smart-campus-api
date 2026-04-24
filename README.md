# Smart Campus Sensor & Room Management API

**GitHub Repository:** `https://github.com/Shri-1019/smart-campus-api.git`

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
- Postman
- GitHub
- NetBeans

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
- `config` -> application configuration
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

## Main Endpoints

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

---

## Sample curl Commands

### 1. Discovery endpoint
```bash
curl http://localhost:8080/api/v1
```

### 2. Get all rooms
```bash
curl http://localhost:8080/api/v1/rooms
```

### 3. Get one room by ID
```bash
curl http://localhost:8080/api/v1/rooms/LIB-301
```

### 4. Create a room
```bash
curl -X POST http://localhost:8080/api/v1/rooms -H "Content-Type: application/json" -d "{\"id\":\"ENG-101\",\"name\":\"Engineering Lab\",\"capacity\":25}"
```

### 5. Get all sensors
```bash
curl http://localhost:8080/api/v1/sensors
```

### 6. Create a sensor
```bash
curl -X POST http://localhost:8080/api/v1/sensors -H "Content-Type: application/json" -d "{\"id\":\"TEMP-001\",\"type\":\"Temperature\",\"status\":\"ACTIVE\",\"currentValue\":26.5,\"roomId\":\"LIB-301\"}"
```

### 7. Filter sensors by type
```bash
curl http://localhost:8080/api/v1/sensors?type=Temperature
```

### 8. Get sensor readings
```bash
curl http://localhost:8080/api/v1/sensors/TEMP-001/readings
```

### 9. Add a sensor reading
```bash
curl -X POST http://localhost:8080/api/v1/sensors/TEMP-001/readings -H "Content-Type: application/json" -d "{\"id\":\"R-001\",\"timestamp\":1712050200000,\"value\":28.4}"
```

### 10. Test invalid room error
```bash
curl -X POST http://localhost:8080/api/v1/sensors -H "Content-Type: application/json" -d "{\"id\":\"TEMP-002\",\"type\":\"Temperature\",\"status\":\"ACTIVE\",\"currentValue\":24.0,\"roomId\":\"FAKE-999\"}"
```

### 11. Test maintenance sensor reading error
```bash
curl -X POST http://localhost:8080/api/v1/sensors/TEMP-999/readings -H "Content-Type: application/json" -d "{\"id\":\"R-999\",\"timestamp\":1712050200000,\"value\":30.5}"
```

---

## Notes
- This project follows the coursework requirement to use JAX-RS only
- No database is used
- Data is stored in memory only
- The API is tested using Postman
- Data resets whenever the server restarts

---

## Coursework Question Answers

### Part 1 – Service Architecture & Setup

#### 1. Resource lifecycle in JAX-RS
By default, resource classes in JAX-RS tend to have a request scope, meaning a new instance of a resource class gets created every time there’s an incoming HTTP request. This reduces the risk of accidental sharing of data in the form of instance variables between users. In my program, the majority of the data will not be held within resource classes themselves, but rather in the collections held by shared store classes such as RoomStore, SensorStore, and SensorReadingStore. As these collections may be modified by different requests at once, the major problem arises in the form of shared data that can be easily altered by any request. Consequently, the program should carefully handle this problem so that no updates to shared data result in data loss or corruption in the case of concurrent requests. The coursework requires storing data within in-memory collections.

#### 2. Why hypermedia is valuable in REST
Hypermedia is seen as a sophisticated use case of REST since the response of the API itself provides instructions regarding possible actions and linked resources. My discovery end point will provide links or paths for the primary collection entities like rooms and sensors. This helps in the sense that the developer of the client does not need to just refer to static documentation to get information about the next step. This allows for greater flexibility in terms of the development of the API.

### Part 2 – Room Management

#### 1. Returning IDs vs full room objects
The API would return only the room IDs, which would lead to a smaller response and lower bandwidth use. This could be helpful if all that the client needed were the room IDs. On the other hand, it would require the client to make more calls for the details of the rooms. The API would return complete room objects in a larger response but with the advantage that the client would immediately receive useful data like the name of the room, its capacity, and its sensors' IDs.

#### 2. Is DELETE idempotent in this implementation?
Yes, the DELETE action is idempotent regarding the end-state of the system. In case where the deletion is performed successfully once, any subsequent invocation of the same DELETE will not bring about any change within the system, since the room was already deleted. While the responses of the system may differ, such as `room not found`, the end-state of the system does not change after the room was initially deleted. Therefore, the fact that further invocations of the same request do not modify the system anymore makes the action idempotent.

### Part 3 – Sensor Operations & Linking

#### 1. What happens if a client sends the wrong content type?
The `@Consumes(MediaType.APPLICATION_JSON)` annotation in the sensor creation endpoint indicates that the request payload must be in JSON format. If the client submits a request payload in any other format like `text/plain` or `application/xml`, JAX-RS will try to find an appropriate reader for that format. If the appropriate reader is not available, this scenario will generally lead to an HTTP status code `415 Unsupported Media Type` being returned.

#### 2. Why query parameters are better for filtering
It is more appropriate to use a query parameter, such as `/sensors?type=CO2`, since the client is still requesting the same collection of sensors, but with an added condition that determines which sensors get returned. Keeping it as a query parameter ensures the resource semantics remain the same while also allowing more flexibility when adding additional filters in the future. If the filter is placed in the path, such as `/sensors/type/CO2`, then the URI starts to look like a hierarchy of resources rather than a search condition.

### Part 4 – Deep Nesting with Sub-Resources

#### 1. Benefits of the sub-resource locator pattern
The sub-resource locator pattern improves the API design because the nested operations are handled by dedicated classes. In my implementation, `SensorResource` handles the sensor resources, while `SensorReadingResource` handles the reading resources for a particular sensor. This makes it easier to avoid putting everything into one very large resource class.

The main advantage is that the code becomes easier to read, maintain, and separate by responsibility. In more complex systems, this design would make development easier than having all operations in one very large controller class.

#### 2. Historical data and consistency
`SensorReadingResource` is used for both reading retrieval from history and appending new values for a certain sensor. When a reading is successfully posted, the current value of the parent sensor is also updated. This is important because it keeps the sensor endpoint and the reading history synchronized.

### Part 5 – Advanced Error Handling, Exception Mapping & Logging

#### 1. Why HTTP 422 is often more accurate than 404
The status code HTTP `422 Unprocessable Entity` is more appropriate than HTTP `404 Not Found` if the request body is structured properly but refers to invalid values. In this case, if an attempt is made to create a sensor with a non-existent `roomId`, the request body is well-formed, yet unprocessable because there is no such room. The status code `404` refers to a requested resource not being found, while `422` shows that a semantic problem exists in the submitted data.

#### 2. Cybersecurity risks of exposing stack traces
There are many security issues when stack traces are shown directly in the application. They reveal information about the implementation of the system, including class names, package names, method names, and file structure. This can help an attacker perform a more targeted attack. The exception mappers and the global exception mapper were implemented to return clean JSON responses instead of exposing internal details.

#### 3. Why filters are better for cross-cutting concerns like logging
Logging is considered a cross-cutting concern because it affects many endpoints, not just one. Using a JAX-RS filter is better than writing logging code inside each endpoint method because it keeps the logic in one place and avoids duplication. In my solution, the logging filter records the HTTP method, the URI used to access the endpoint, and the final response status.
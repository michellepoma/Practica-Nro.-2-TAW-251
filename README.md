
# 📚 Sistema de Gestión Universitaria

Este proyecto es una API REST desarrollada con **Spring Boot 3.x** que permite gestionar estudiantes, docentes, materias e inscripciones en una universidad. Además, cuenta con autenticación basada en JWT y almacenamiento en caché utilizando Redis.

## 🚀 Tecnologías Utilizadas

- Java 21
- Spring Boot 3.2.5
- Spring Security + JWT
- Spring Data JPA + Hibernate
- PostgreSQL
- Redis (Caché)
- Swagger OpenAPI (Documentación de API)
- Lombok
- Maven

## 📂 Estructura del Proyecto

```
mi-proyecto-spring-boot/
├── src/
│   └── main/
│       └── java/com/universidad/
│           ├── controller/        # Controladores REST (API Endpoints)
│           ├── dto/               # Clases DTO (Data Transfer Objects)
│           ├── model/             # Entidades JPA (Mapeo a la BD)
│           ├── registro/          # Lógica de registro de usuarios y autenticación
│           ├── repository/        # Interfaces de acceso a datos (JPA Repositories)
│           ├── service/           # Lógica de negocio (Servicios)
│           ├── validation/        # Validaciones personalizadas y utilidades
│           └── UniversidadApplication.java  # Clase principal (Entry Point de Spring Boot)
│
├── src/main/resources/            # Configuraciones y recursos estáticos
│   └── application.properties     # Configuración de la aplicación
│
├── backup_universidad.sql         # Script de respaldo de la base de datos
├── manualTecnico.pdf                          # Documentación del proyecto
│
└── pom.xml                        # Configuración de dependencias Maven
```

## ⚙️ Configuración de la Aplicación

Configura el archivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/universidad
spring.datasource.username=postgres
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update

springdoc.swagger-ui.path=/swagger-ui.html

spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379

app.jwtSecret=K8jd73ms82xhsOq9zP1lMf7Ks92xLQ8sdjf93KslWpa9BvnsPQ7Ks82lZlxMf9Lp
app.jwtExpirationMs=86400000
```

## 🛡️ Seguridad

- Roles definidos: `ADMIN`, `DOCENTE`, `ESTUDIANTE`
- Autenticación vía JWT
- Acceso a Swagger UI: `http://localhost:8080/swagger-ui.html`

## 📖 Endpoints Principales

Consulta la documentación completa en Swagger.

### 🔹 Materias
- `GET /api/materias` – Consultar materias
- `POST /api/materias` – Crear materia
- `PUT /api/materias/{id}` – Actualizar materia
- `DELETE /api/materias/{id}` – Eliminar materia

### 🔹 Estudiantes
- `GET /api/estudiantes` – Consultar estudiantes
- `POST /api/estudiantes` – Crear estudiante
- `PUT /api/estudiantes/{id}` – Actualizar estudiante

### 🔹 Inscripciones
- `GET /api/inscripciones` – Consultar inscripciones
- `POST /api/inscripciones` – Crear inscripción
- `PUT /api/inscripciones/{id}/abandonar` – Abandonar inscripción

### 🔹 Autenticación
- `POST /api/auth/signup` – Crear usuario
- `POST /api/auth/login` – Iniciar sesión
- `POST /api/auth/logout` – Cerrar sesión

## 📦 Instalación y Ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/michellepoma/Practica-Nro.-2-TAW-251.git
   ```
2. Configura la base de datos en `application.properties`.
3. Ejecuta la aplicación:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
4. Accede a Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## ✍️ Autor

- Michelle Poma - Sistema de Gestión Universitaria - Version Practica Nro. 2

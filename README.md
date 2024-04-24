## Microservicio de Gestión de Empleados y Centros

Este microservicio proporciona una API REST para la gestión de empleados y centros. Permite realizar operaciones como la creación, lectura, actualización y eliminación de empleados, así como la recuperación de información sobre centros.

### Funcionamiento

El microservicio consta de dos controladores principales: `EmpleadoController` y `CentroController`. Cada uno proporciona endpoints para manipular recursos específicos: empleados y centros, respectivamente.

#### Controlador de Empleados (`EmpleadoController`)

El controlador de empleados maneja las operaciones relacionadas con los empleados. Las principales operaciones que ofrece son:

- **Obtener todos los empleados**: `GET /empleados`
- **Obtener un empleado por su ID**: `GET /empleados/{id}`
- **Obtener empleados por nombre**: `GET /empleados/nombre/{nombre}`
- **Obtener empleados por ID del centro al que pertenecen**: `GET /empleados/centro/{idCentro}`
- **Crear un nuevo empleado**: `POST /empleados`
- **Actualizar un empleado existente por su ID**: `PUT /empleados/{id}`
- **Eliminar un empleado por su ID**: `DELETE /empleados/{id}`

#### Controlador de Centros (`CentroController`)

El controlador de centros maneja las operaciones relacionadas con los centros. La única operación que ofrece es:

- **Obtener un centro por su ID**: `GET /centros/{idCentro}`

### Códigos de Estado

El microservicio utiliza los siguientes códigos de estado HTTP:

- `200 OK`: La solicitud se ha completado satisfactoriamente.
- `201 Created`: El recurso se ha creado correctamente.
- `204 No Content`: La solicitud se ha completado correctamente, pero no hay contenido para devolver.
- `400 Bad Request`: La solicitud no se pudo procesar debido a un error de cliente (por ejemplo, datos de empleado inválidos).
- `404 Not Found`: El recurso solicitado no se encontró (por ejemplo, empleado o centro no existente).

### Base de Datos H2 de Ejemplo

El microservicio utiliza una base de datos H2 embebida para almacenar la información sobre los empleados y centros. A continuación se muestra la estructura de la base de datos y los registros de ejemplo:

#### Estructura de la Base de Datos

La base de datos consta de dos tablas: `CENTROS` y `EMPLEADOS`.

- **Tabla CENTROS**:
  - `NUM_CENTRO`: Identificador único del centro.
  - `NOMBRE_CENTRO`: Nombre del centro.

- **Tabla EMPLEADOS**:
  - `ID_EMPLEADO`: Identificador único del empleado.
  - `NOMBRE`: Nombre del empleado.
  - `ID_CENTRO`: Identificador del centro al que pertenece el empleado (clave externa que referencia a la tabla `CENTROS`).

#### Script de Creación de la Base de Datos

El script `schema.sql` define la estructura de las tablas en la base de datos H2:

```sql
DROP TABLE IF EXISTS EMPLEADOS;
DROP TABLE IF EXISTS  CENTROS;

CREATE TABLE CENTROS (
    NUM_CENTRO INT PRIMARY KEY,
    NOMBRE_CENTRO VARCHAR(100)
);

CREATE TABLE EMPLEADOS (
    ID_EMPLEADO INT PRIMARY KEY,
    NOMBRE VARCHAR(100),
    ID_CENTRO INT,
    FOREIGN KEY (ID_CENTRO) REFERENCES CENTROS(NUM_CENTRO)
);
```
El script `data.sql` contiene los registros de ejemplo que se insertarán en las tablas CENTROS y EMPLEADOS:

```sql
INSERT INTO CENTROS (NUM_CENTRO, NOMBRE_CENTRO)
VALUES
(101, 'ViewNext'),
(102, 'Indra'),
(103, 'Microsoft'),
(104, 'Accenture'),
(105, 'Capgemini'),
(106, 'Tata Consultancy Services'),
(107, 'Wipro'),
(108, 'Infosys'),
(109, 'Cognizant'),
(110, 'IBM');

INSERT INTO EMPLEADOS (ID_EMPLEADO, NOMBRE, ID_CENTRO)
VALUES
(1, 'Juan', 101),
(2, 'Ahmed Mohammed', 106),
(3, 'Yuki Tanaka', 104),
(4, 'Juan García', 103),
(5, 'María López', 108),
(6, 'Mohammed Ahmed', 104),
(7, 'David Rodríguez', 102),
(8, 'Laura Martínez', 101),
(9, 'Fatima Ahmed', 108),
(10, 'Takashi Suzuki', 106),
(11, 'Sara García', 106),
(12, 'Hiroshi Tanaka', 103),
(13, 'Rosa Pérez', 108),
(14, 'Yong Chen', 105);

```

### Tecnologías Utilizadas

El microservicio está desarrollado utilizando Java y Spring Boot. Se utiliza validación de datos con anotaciones de Bean Validation (`@Valid`) y se manejan errores de validación y otras excepciones mediante el lanzamiento de `ResponseStatusException` para devolver códigos de estado HTTP adecuados.

### Ejemplo de Uso

A continuación se muestran algunos ejemplos de uso de la API:

- Obtener todos los empleados: `GET /empleados`
- Crear un nuevo empleado:

```json
POST /empleados
        {
          "idEmpleado": 1,
          "nombre": "Juan",
          "idCentro": 101,
          "centro": {
              "nombreCentro": "Centro A",
              "numCentro": 101
                    }
        }
```
- Actualizar un empleado existente:
```json  
PUT /empleados/1
       {
          "idEmpleado": 1,
          "nombre": "Juan",
          "idCentro": 101,
          "centro": {
              "nombreCentro": "Centro A",
              "numCentro": 101
                    }
        }
```
- Eliminar un empleado existente: `DELETE /empleados/1`
- Obtener un centro por su ID: `GET /centros/101`

### Autor

Este microservicio fue desarrollado por Jorge Campos Rodríguez.

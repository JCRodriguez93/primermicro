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

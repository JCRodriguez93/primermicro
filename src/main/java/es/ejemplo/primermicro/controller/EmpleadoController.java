package es.ejemplo.primermicro.controller;

import es.ejemplo.primermicro.entity.EmpleadoEntity;
import es.ejemplo.primermicro.service.CentroService;
import es.ejemplo.primermicro.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private CentroService centroService;

    @GetMapping
    public ResponseEntity<List<EmpleadoEntity>> getAllEmployees() {

        List<EmpleadoEntity> listaEmpleados = empleadoService.listAllEmpleados();

        if (listaEmpleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaEmpleados);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmpleadoEntity> getEmployeesById(@PathVariable("id") Integer id) {

        EmpleadoEntity empleadoEntity = empleadoService.getEmpleadoById(id);

        if (empleadoEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empleadoEntity);
    }

    @GetMapping(value = "/nombre/{nombre}")
    public ResponseEntity<List<EmpleadoEntity>> getEmployeesByName(@PathVariable("nombre") String nombre) {


        List<EmpleadoEntity> listaEmpleados = empleadoService.getEmpleadoByNombre(nombre);

        if (listaEmpleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaEmpleados);
    }
    @GetMapping(value = "/centro/{idCentro}")
    public ResponseEntity<List<EmpleadoEntity>> getEmployeesByCenterId(@PathVariable("idCentro") Integer idCentro) {

        List<EmpleadoEntity> listaEmpleados = empleadoService.getEmpleadoByIdCentro(idCentro);

        if (listaEmpleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaEmpleados);
    }

    @PostMapping
    public ResponseEntity<EmpleadoEntity> createEmployee(@Valid @RequestBody EmpleadoEntity empleadoEntity, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid employee data");
        }
        EmpleadoEntity empleadoCreated = empleadoService.createEmpleado(empleadoEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoCreated);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EmpleadoEntity> updateEmployee(@Valid @PathVariable("id") Integer id, @RequestBody EmpleadoEntity empleadoEntity, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        empleadoEntity.setIdEmpleado(id);
        EmpleadoEntity empleadoDB = empleadoService.updateEmpleado(id, empleadoEntity);
        if (empleadoDB == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(empleadoDB);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<EmpleadoEntity> deleteEmployee(@PathVariable("id") Integer id) {


        EmpleadoEntity empleadoDeleted = empleadoService.deleteEmpleado(id);
        if (empleadoDeleted == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(empleadoDeleted);
    }

}

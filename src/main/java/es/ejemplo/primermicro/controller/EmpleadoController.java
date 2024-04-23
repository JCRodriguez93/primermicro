package es.ejemplo.primermicro.controller;

import es.ejemplo.primermicro.entity.EmpleadoEntity;
import es.ejemplo.primermicro.service.CentroService;
import es.ejemplo.primermicro.service.EmpleadoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/empleados")
public class EmpleadoController {


    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private CentroService centroService;

    @GetMapping
    public ResponseEntity<List<EmpleadoEntity>> getAllEmployees() {
        log.info("Fetching all employees...");
        List<EmpleadoEntity> listaEmpleados = empleadoService.listAllEmployees();

        if (listaEmpleados.isEmpty()) {
            log.warn("No employees found.");
            return ResponseEntity.noContent().build();
        }
        log.info("Returning list of employees.");
        return ResponseEntity.ok(listaEmpleados);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmpleadoEntity> getEmployeesById(@PathVariable("id") Integer id) {
        log.info("Fetching employee with ID: {}", id);
        EmpleadoEntity empleadoEntity = empleadoService.getEmployeeById(id);

        if (empleadoEntity == null) {
            log.warn("Employee with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Employee found: {}", empleadoEntity);
        return ResponseEntity.ok(empleadoEntity);
    }

    @GetMapping(value = "/nombre/{nombre}")
    public ResponseEntity<List<EmpleadoEntity>> getEmployeesByName(@PathVariable("nombre") String nombre) {
        log.info("Fetching employees with name: {}", nombre);
        List<EmpleadoEntity> listaEmpleados = empleadoService.getEmployeeByName(nombre);

        if (listaEmpleados.isEmpty()) {
            log.warn("No employees found with name: {}", nombre);
            return ResponseEntity.noContent().build();
        }
        log.info("Returning list of employees with name: {}", nombre);
        return ResponseEntity.ok(listaEmpleados);
    }


    @GetMapping(value = "/centro/{idCentro}")
    public ResponseEntity<List<EmpleadoEntity>> getEmployeesByCenterId(@PathVariable("idCentro") Integer idCentro) {
        log.info("Fetching employees by center ID: {}", idCentro);
        List<EmpleadoEntity> listaEmpleados = empleadoService.getEmployeeByCenterId(idCentro);

        if (listaEmpleados.isEmpty()) {
            log.warn("No employees found for center ID: {}", idCentro);
            return ResponseEntity.noContent().build();
        }
        log.info("Returning list of employees for center ID: {}", idCentro);
        return ResponseEntity.ok(listaEmpleados);
    }


    @PostMapping
    public ResponseEntity<EmpleadoEntity> createEmployee(@Valid @RequestBody EmpleadoEntity empleadoEntity, BindingResult result) {
        log.info("Creating new employee: {}", empleadoEntity);
        if (result.hasErrors()) {
            log.error("Invalid employee data: {}", result.getAllErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid employee data");
        }
        EmpleadoEntity empleadoCreated = empleadoService.createEmployee(empleadoEntity);
        log.info("Employee created: {}", empleadoCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoCreated);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EmpleadoEntity> updateEmployee(@Valid @PathVariable("id") Integer id, @RequestBody EmpleadoEntity empleadoEntity, BindingResult result) {
        log.info("Updating employee with ID: {}", id);
        if (result.hasErrors()) {
            log.error("Invalid data for updating employee with ID {}: {}", id, result.getAllErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        empleadoEntity.setIdEmpleado(id);
        EmpleadoEntity empleadoDB = empleadoService.updateEmployee(id, empleadoEntity);
        if (empleadoDB == null) {
            log.warn("Employee with ID {} not found for updating", id);
            return ResponseEntity.notFound().build();

        }
        log.info("Employee with ID {} updated: {}", id, empleadoDB);
        return ResponseEntity.ok(empleadoDB);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<EmpleadoEntity> deleteEmployee(@PathVariable("id") Integer id) {
        log.info("Deleting employee with ID: {}", id);
        EmpleadoEntity empleadoDeleted = empleadoService.deleteEmployee(id);
        if (empleadoDeleted == null) {
            log.warn("Employee with ID {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Employee with ID {} deleted", id);
        return ResponseEntity.ok(empleadoDeleted);
    }

}

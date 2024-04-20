package es.ejemplo.primermicro.controller;

import es.ejemplo.primermicro.entity.EmpleadoEntity;
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

    @GetMapping //funciona
    public ResponseEntity<List<EmpleadoEntity>> getAllEmpleados() {
        List<EmpleadoEntity> listaEmpleados = empleadoService.listAllEmpleados();

        if (listaEmpleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaEmpleados);
    }

    @GetMapping(value = "/{id}") //funciona
    public ResponseEntity<EmpleadoEntity> getEmpleadosById(@PathVariable("id") int id) {
        EmpleadoEntity empleadoEntity = empleadoService.getEmpleadoById(id);

        if (empleadoEntity == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(empleadoEntity);
    }


    @PostMapping
    public ResponseEntity<EmpleadoEntity> createEmpleado(@Valid @RequestBody EmpleadoEntity empleadoEntity, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid employee data");
        }
        EmpleadoEntity empleadoCreated = empleadoService.createEmpleado(empleadoEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoCreated);
    }

    @PutMapping(value = "/{id}") //FUNCIONA
    public ResponseEntity<EmpleadoEntity> updateEmpleado(@Valid @PathVariable("id") int id, @RequestBody EmpleadoEntity empleadoEntity, BindingResult result) {
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


    @DeleteMapping(value = "/{id}") //FUNCIONA
    public ResponseEntity<EmpleadoEntity> deleteEmpleado(@PathVariable("id") int id) {
        EmpleadoEntity empleadoDeleted = empleadoService.deleteEmpleado(id);
        if (empleadoDeleted == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(empleadoDeleted);
    }


}

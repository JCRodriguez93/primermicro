package es.ejemplo.primermicro.controller;

import es.ejemplo.primermicro.entity.EmpleadoEntity;
import es.ejemplo.primermicro.service.EmpleadoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
public class EmpleadoControllerMockTest {


    @Mock
    private EmpleadoService empleadoService;

    @InjectMocks
    private EmpleadoController empleadoController;

    @Test
    public void getAllEmpleados_When_EmpleadosExist_Returns_Empleados() {
        // Arrange
        List<EmpleadoEntity> empleados = new ArrayList<>();
        empleados.add(new EmpleadoEntity());
        empleados.add(new EmpleadoEntity());

        when(empleadoService.listAllEmpleados()).thenReturn(empleados);

        ResponseEntity<List<EmpleadoEntity>> response = empleadoController.getAllEmpleados();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void getEmpleadosById_When_EmpleadoExists_Returns_Empleado() {
        EmpleadoEntity empleado = new EmpleadoEntity();
        when(empleadoService.getEmpleadoById(1)).thenReturn(empleado);

        ResponseEntity<EmpleadoEntity> response = empleadoController.getEmpleadosById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(empleado, response.getBody());
    }

    @Test
    public void getEmpleadosById_When_EmpleadoDoesNotExist_Returns_NoContent() {
        when(empleadoService.getEmpleadoById(1)).thenReturn(null);

        ResponseEntity<EmpleadoEntity> response = empleadoController.getEmpleadosById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void createEmpleado_When_EmpleadoIsValid_Returns_CreatedEmpleado() {
        // Arrange
        EmpleadoEntity empleado = new EmpleadoEntity();
        BindingResult bindingResult = new BeanPropertyBindingResult(empleado, "empleado");

        when(empleadoService.createEmpleado(empleado)).thenReturn(empleado);

        // Act
        ResponseEntity<EmpleadoEntity> response = empleadoController.createEmpleado(empleado, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(empleado, response.getBody());
    }
    @Test
    public void createEmpleado_When_EmpleadoIsInvalid_Throws_BadRequest() {
        // Arrange
        EmpleadoEntity empleado = new EmpleadoEntity();
        BindingResult bindingResult = new BeanPropertyBindingResult(empleado, "empleado");

        // Act
        ResponseEntity<EmpleadoEntity> response = empleadoController.createEmpleado(empleado, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
           }


    @Test
    public void updateEmpleado_When_EmpleadoExists_Returns_UpdatedEmpleado() {
        // Arrange
        EmpleadoEntity empleado = new EmpleadoEntity();
        empleado.setIdEmpleado(1);
        BindingResult bindingResult = new BeanPropertyBindingResult(empleado, "empleado");
        when(empleadoService.updateEmpleado(1, empleado)).thenReturn(empleado);

        // Act
        ResponseEntity<EmpleadoEntity> response = empleadoController.updateEmpleado(1, empleado, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(empleado, response.getBody());
    }

    @Test
    public void updateEmpleado_When_EmpleadoDoesNotExist_Returns_NotFound() {
        // Arrange
        BindingResult bindingResult = new BeanPropertyBindingResult(new EmpleadoEntity(), "empleado");
        when(empleadoService.updateEmpleado(1, new EmpleadoEntity())).thenReturn(null);

        // Act
        ResponseEntity<EmpleadoEntity> response = empleadoController.updateEmpleado(1, new EmpleadoEntity(), bindingResult);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void deleteEmpleado_When_EmpleadoExists_Returns_DeletedEmpleado() {
        // Arrange
        EmpleadoEntity empleado = new EmpleadoEntity();
        when(empleadoService.deleteEmpleado(1)).thenReturn(empleado);

        // Act
        ResponseEntity<EmpleadoEntity> response = empleadoController.deleteEmpleado(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(empleado, response.getBody());
    }

    @Test
    public void deleteEmpleado_When_EmpleadoDoesNotExist_Returns_NotFound() {
        // Arrange
        when(empleadoService.deleteEmpleado(1)).thenReturn(null);

        // Act
        ResponseEntity<EmpleadoEntity> response = empleadoController.deleteEmpleado(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

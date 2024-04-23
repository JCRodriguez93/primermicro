package es.ejemplo.primermicro.controller;

import es.ejemplo.primermicro.entity.CentroEntity;
import es.ejemplo.primermicro.entity.EmpleadoEntity;
import es.ejemplo.primermicro.service.CentroService;
import es.ejemplo.primermicro.service.EmpleadoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class EmpleadoControllerMockTest {


    @Mock
    private EmpleadoService empleadoService;

    @Mock
    private CentroService centroService;

    @InjectMocks
    private EmpleadoController empleadoController;

    @Test
    public void getAllEmployeesTest() {

        List<EmpleadoEntity> empleados = new ArrayList<>();
        empleados.add(new EmpleadoEntity());
        empleados.add(new EmpleadoEntity());

        when(empleadoService.listAllEmployees()).thenReturn(empleados);

        ResponseEntity<List<EmpleadoEntity>> response = empleadoController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void getEmployeeByIdWhenEmployeeExistsThenReturnsEmployeeTest() {
        EmpleadoEntity empleado = new EmpleadoEntity();
        when(empleadoService.getEmployeeById(1)).thenReturn(empleado);

        ResponseEntity<EmpleadoEntity> response = empleadoController.getEmployeesById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(empleado, response.getBody());
    }

    @Test
    public void getEmployeeByIdWhenEmployeeDoesNotExistThenReturnsNotFoundTest() {
        when(empleadoService.getEmployeeById(1)).thenReturn(null);

        ResponseEntity<EmpleadoEntity> response = empleadoController.getEmployeesById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }



    @Test
    public void getEmployeesByNameWhenEmployeesExistThenReturnsEmployeeTest() {
        CentroEntity centro = new CentroEntity(101,"IBM");
        EmpleadoEntity empleado1 = new EmpleadoEntity(1,"Juan",101, centro);
        EmpleadoEntity empleado2 = new EmpleadoEntity(2,"Jorge Juan",101, centro);
        EmpleadoEntity empleado3 = new EmpleadoEntity(3,"Juan José",101, centro);

        List<EmpleadoEntity> empleados = new ArrayList<>();
        empleados.add(empleado1);
        empleados.add(empleado2);
        empleados.add(empleado3);

        when(empleadoService.getEmployeeByName("Juan")).thenReturn(empleados);

        ResponseEntity<List<EmpleadoEntity>> response = empleadoController.getEmployeesByName("Juan");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }

    @Test
    public void getEmployeesByCenterIdWhenEmployeeAreAssociatedThenReturnsEmployeesTest() {
        CentroEntity centro = CentroEntity.builder().nombreCentro("Accenture").numCentro(101).build();

        List<EmpleadoEntity> empleados = new ArrayList<>();
        EmpleadoEntity empleado1 = new EmpleadoEntity(1,"Juan",centro.getNumCentro(),centro);
        EmpleadoEntity empleado2 = new EmpleadoEntity(2,"Jose",centro.getNumCentro(),centro);

        empleados.add(empleado1);
        empleados.add(empleado2);

        when(empleadoService.getEmployeeByCenterId(101)).thenReturn(empleados);
        when(centroService.getCenterByIdCenter(101)).thenReturn(centro);

        ResponseEntity<List<EmpleadoEntity>> response = empleadoController.getEmployeesByCenterId(101);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(empleados, response.getBody());
    }

    @Test
    public void getEmployeesByCenterIdWhenEmployeeCenterDoesNotExistThenReturnsNotFoundTest() {

        when(empleadoService.getEmployeeByCenterId(999)).thenReturn(null);
        ResponseEntity<List<EmpleadoEntity>> response = empleadoController.getEmployeesByCenterId(null);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }




    @Test
    public void createEmployeeWhenEmployeeIsValidThenReturnsCreatedEmployeeTest() {

        EmpleadoEntity empleado = EmpleadoEntity.builder()
                .idEmpleado(99)
                .nombre("Juan")
                .idCentro(101)
                .centro(CentroEntity.builder().build()).build();

        BindingResult bindingResult = new BeanPropertyBindingResult(empleado, "empleado");

        when(empleadoService.createEmployee(empleado)).thenReturn(empleado);

        ResponseEntity<EmpleadoEntity> response = empleadoController.createEmployee(empleado, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(empleado, response.getBody());
    }

    /*

    @Test
    public void createEmployeeWhenEmployeeIsInvalidThenReturnsBadRequestTest() {
        EmpleadoEntity empleadoInvalido = EmpleadoEntity.builder()
                .idEmpleado(99)
                .idCentro(101)
                .centro(CentroEntity.builder().build()).build();

        BindingResult bindingResult = new BeanPropertyBindingResult(empleadoInvalido, "empleado");
        bindingResult.reject("nombre", "El nombre del empleado es obligatorio");

        when(empleadoService.createEmployee(empleadoInvalido)).thenThrow(new MockitoException("Empleado inválido"));

        ResponseEntity<EmpleadoEntity> response = empleadoController.createEmployee(empleadoInvalido, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

*/
    @Test
    public void updateEmployeeWhenEmployeeExistsThenReturnsUpdatedEmployeeTest() {
       EmpleadoEntity empleadoInicial = EmpleadoEntity.builder()
               .idEmpleado(99)
               .nombre("nombreInicial").build();

        EmpleadoEntity empleadoActualizado = EmpleadoEntity.builder()
                .idEmpleado(99)
                .nombre("NombreActualizado").build();

        BindingResult bindingResult = new BeanPropertyBindingResult(empleadoInicial, "empleado");

        when(empleadoService.updateEmployee(99, empleadoInicial)).thenReturn(empleadoActualizado);

        ResponseEntity<EmpleadoEntity> response = empleadoController.updateEmployee(99, empleadoInicial, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(empleadoActualizado, response.getBody());
        verify(empleadoService).updateEmployee(99, empleadoInicial);
    }

    @Test
    public void updateEmployeeWhenEmployeeDoesNotExistThenReturnsNotFoundTest() {
        EmpleadoEntity empleadoInexistente = EmpleadoEntity.builder()
                .idEmpleado(99)
                .nombre("Empleado Inexistente").build();

        when(empleadoService.updateEmployee(99, empleadoInexistente)).thenReturn(null);

        BindingResult bindingResult = new BeanPropertyBindingResult(empleadoInexistente, "empleado");

        ResponseEntity<EmpleadoEntity> response = empleadoController.updateEmployee(99, empleadoInexistente, bindingResult);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(empleadoService).updateEmployee(99, empleadoInexistente);
    }

    @Test
    public void deleteEmployeeWhenEmployeeExistsThenReturnsDeletedEmployeeTest() {
        EmpleadoEntity empleado = new EmpleadoEntity();

        when(empleadoService.deleteEmployee(1)).thenReturn(empleado);

        ResponseEntity<EmpleadoEntity> response = empleadoController.deleteEmployee(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(empleado, response.getBody());
        verify(empleadoService).deleteEmployee(1);

        assertNull(empleadoService.getEmployeeById(1));
    }


    @Test
    public void deleteEmployeeWhenEmployeeDoesNotExistThenReturnsNotFoundTest() {
        when(empleadoService.deleteEmployee(1)).thenReturn(null);

        ResponseEntity<EmpleadoEntity> response = empleadoController.deleteEmployee(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(empleadoService).deleteEmployee(1);
        assertNull(empleadoService.getEmployeeById(1));
    }

}

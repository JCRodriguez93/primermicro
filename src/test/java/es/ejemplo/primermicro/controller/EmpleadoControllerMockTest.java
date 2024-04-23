package es.ejemplo.primermicro.controller;

import es.ejemplo.primermicro.entity.CentroEntity;
import es.ejemplo.primermicro.entity.EmpleadoEntity;
import es.ejemplo.primermicro.service.CentroService;
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
import static org.junit.jupiter.api.Assertions.assertNull;
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
    public void getAllEmpleadosTest() {
        // Arrange
        List<EmpleadoEntity> empleados = new ArrayList<>();
        empleados.add(new EmpleadoEntity());
        empleados.add(new EmpleadoEntity());

        when(empleadoService.listAllEmployees()).thenReturn(empleados);

        ResponseEntity<List<EmpleadoEntity>> response = empleadoController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void getEmpleadosById_When_EmpleadoExists_Returns_Empleado() {
        EmpleadoEntity empleado = new EmpleadoEntity();
        when(empleadoService.getEmployeeById(1)).thenReturn(empleado);

        ResponseEntity<EmpleadoEntity> response = empleadoController.getEmployeesById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(empleado, response.getBody());
    }


    @Test
    public void getEmpleadosByName_When_EmpleadoExist_Returns_Empleados() {
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
        assertEquals(3, response.getBody().size()); // Verifica que se devuelva una lista con al menos un empleado
    }

    @Test
    public void getEmpleadosByCenter_When_EmpleadoExist_Returns_Empleados() {
        // Arrange
        CentroEntity centro = new CentroEntity();
        centro.setNumCentro(101); // Configuramos el ID del centro

        List<EmpleadoEntity> empleados = new ArrayList<>();
        EmpleadoEntity empleado1 = new EmpleadoEntity(1,"Juan",centro.getNumCentro(),centro);
        EmpleadoEntity empleado2 = new EmpleadoEntity(2,"Jose",centro.getNumCentro(),centro);

        empleados.add(empleado1); // Puedes agregar más empleados si es necesario
        empleados.add(empleado2);
        // Configuramos Mockito para devolver los empleados cuando se llama al método con el ID del centro
        when(empleadoService.getEmployeeByCenterId(101)).thenReturn(empleados);
        // Configuramos Mockito para devolver el centro cuando se llama al método con el ID del centro
        when(centroService.getCenterByIdCenter(101)).thenReturn(centro);

        // Act
        ResponseEntity<List<EmpleadoEntity>> response = empleadoController.getEmployeesByCenterId(101);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size()); // Verifica que se devuelva una lista con al menos un empleado
        assertEquals(empleados, response.getBody());
    }





    @Test
    public void createEmpleado_When_EmpleadoIsValid_Returns_CreatedEmpleado() {

        EmpleadoEntity empleado = EmpleadoEntity.builder()
                .idEmpleado(99)
                .nombre("Juan")
                .idCentro(101)
                .centro(CentroEntity.builder().build()).build();

        BindingResult bindingResult = new BeanPropertyBindingResult(empleado, "empleado");

        when(empleadoService.createEmployee(empleado)).thenReturn(empleado);

        // Act
        ResponseEntity<EmpleadoEntity> response = empleadoController.createEmployee(empleado, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(empleado, response.getBody());
    }

    @Test
    public void updateEmpleado_When_EmpleadoExists_Returns_UpdatedEmpleado() {
        // Arrange
        // Creamos un empleado con un ID y otros datos
        EmpleadoEntity empleadoInicial = new EmpleadoEntity();
        empleadoInicial.setIdEmpleado(99);
        empleadoInicial.setNombre("NombreInicial");

        // Creamos un nuevo objeto empleado que representará el estado actualizado después de la llamada al método de servicio
        EmpleadoEntity empleadoActualizado = new EmpleadoEntity();
        empleadoActualizado.setIdEmpleado(99);
        empleadoActualizado.setNombre("NombreActualizado");

        BindingResult bindingResult = new BeanPropertyBindingResult(empleadoInicial, "empleado");

        // Configuramos Mockito para que devuelva el empleado actualizado cuando se llama al método de actualización con los parámetros correctos
        when(empleadoService.updateEmployee(99, empleadoInicial)).thenReturn(empleadoActualizado);

        // Act
        ResponseEntity<EmpleadoEntity> response = empleadoController.updateEmployee(99, empleadoInicial, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(empleadoActualizado, response.getBody()); // Verificamos que el objeto devuelto sea el mismo que esperamos después de la actualización
        verify(empleadoService).updateEmployee(99, empleadoInicial); // Verificamos que se llamó al método de actualización con los parámetros correctos
    }


    @Test
    public void deleteEmpleado_When_EmpleadoExists_Returns_DeletedEmpleado() {
        // Arrange
        EmpleadoEntity empleado = new EmpleadoEntity();

        // Configuramos Mockito para que devuelva el empleado que se eliminará cuando se llame al método deleteEmpleado
        when(empleadoService.deleteEmployee(1)).thenReturn(empleado);


        // Act
        ResponseEntity<EmpleadoEntity> response = empleadoController.deleteEmployee(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(empleado, response.getBody()); // Verificamos que se devuelva el empleado eliminado

        // Verificamos que el método deleteEmpleado fue llamado con el ID correcto
        verify(empleadoService).deleteEmployee(1);

        // Verificamos que el empleado haya sido eliminado correctamente
        assertNull(empleadoService.getEmployeeById(1)); // Suponiendo que getEmpleadoById devuelve null cuando el empleado no existe
    }


    @Test
    public void deleteEmpleado_When_EmpleadoDoesNotExist_Returns_NotFound() {
        // Arrange
        when(empleadoService.deleteEmployee(1)).thenReturn(null);

        // Act
        ResponseEntity<EmpleadoEntity> response = empleadoController.deleteEmployee(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

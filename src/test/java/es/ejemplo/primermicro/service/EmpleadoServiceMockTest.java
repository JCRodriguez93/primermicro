package es.ejemplo.primermicro.service;

import es.ejemplo.primermicro.entity.CentroEntity;
import es.ejemplo.primermicro.entity.EmpleadoEntity;
import es.ejemplo.primermicro.repository.EmpleadoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmpleadoServiceMockTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    private EmpleadoService empleadoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        empleadoService = new EmpleadoServiceImp(empleadoRepository);
        EmpleadoEntity margarita = EmpleadoEntity.builder()
                .idEmpleado(1)
                .nombre("Margarita")
                .centro(CentroEntity.builder()
                        .numCentro(101)
                        .nombreCentro("Centro A")
                        .build())
                .build();

        Mockito.when(empleadoRepository.findById(1)).thenReturn(Optional.of(margarita));
    }

    @Test
    public void returnAllEmployeesTest() {
        List<EmpleadoEntity> encontrado = empleadoRepository.findAll();
        Assertions.assertThat(!encontrado.isEmpty());
    }
    @Test
    public void whenValidGetIdThenReturnEmployeeTest() {
        EmpleadoEntity encontrado = empleadoService.getEmployeeById(1);
        Assertions.assertThat(encontrado.getNombre()).isEqualTo("Margarita");
    }
    @Test
    public void whenCreateEmployeeThenReturnNotNullTest() {
        EmpleadoEntity empleadoEntity = EmpleadoEntity.builder()
                .idEmpleado(1)
                .nombre("Margarita")
                .centro(CentroEntity.builder()
                        .numCentro(101)
                        .nombreCentro("Centro A")
                        .build())
                .build();


        Mockito.when(empleadoService.createEmployee(empleadoEntity)).thenReturn(empleadoEntity);

        assertNotNull(empleadoEntity, "El empleado creado no debería ser nulo");
        assertEquals(1, empleadoEntity.getIdEmpleado(), "El ID del empleado creado no coincide con el esperado");
        assertEquals("Margarita", empleadoEntity.getNombre(), "El nombre del empleado creado no coincide con el esperado");
        assertEquals(101, empleadoEntity.getCentro().getNumCentro(), "El número del centro del empleado creado no coincide con el esperado");
        assertEquals("Centro A", empleadoEntity.getCentro().getNombreCentro(), "El nombre del centro del empleado creado no coincide con el esperado");
    }



    @Test
    public void whenDeleteEmployeeThenReturnNullTest() {
        EmpleadoEntity empleadoEntity = EmpleadoEntity.builder()
                .idEmpleado(1)
                .nombre("Margarita")
                .centro(CentroEntity.builder()
                        .numCentro(101)
                        .nombreCentro("Centro A")
                        .build())
                .build();

        empleadoRepository.save(empleadoEntity);
        empleadoService.deleteEmployee(empleadoEntity.getIdEmpleado());
        assertFalse(empleadoRepository.existsById(1), "El empleado debería haber sido eliminado correctamente de la base de datos");
    }


    @Test
    public void updateNameAndIdCenterOfEmployeeWhenIdProvidedTest() {

        EmpleadoEntity actual = empleadoService.getEmployeeById(1);
        EmpleadoEntity empleadoActualizado = empleadoService.getEmployeeById(1);

        empleadoActualizado.setIdEmpleado(actual.getIdEmpleado());
        empleadoActualizado.setNombre("Juan");
        empleadoActualizado.setIdCentro(102);

        empleadoService.updateEmployee(empleadoActualizado.getIdEmpleado(), empleadoActualizado);

        assertNotNull(empleadoActualizado);
        assertEquals("Juan", empleadoActualizado.getNombre(), "El nombre del empleado no se actualizó correctamente");
        assertEquals(102, empleadoActualizado.getIdCentro(), "El ID del centro del empleado no se actualizó correctamente");
    }



}

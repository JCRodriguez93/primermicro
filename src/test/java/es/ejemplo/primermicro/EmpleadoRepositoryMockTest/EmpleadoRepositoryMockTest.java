package es.ejemplo.primermicro.EmpleadoRepositoryMockTest;

import es.ejemplo.primermicro.entity.CentroEntity;
import es.ejemplo.primermicro.entity.EmpleadoEntity;
import es.ejemplo.primermicro.repository.EmpleadoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmpleadoRepositoryMockTest {

    @MockBean
    private EmpleadoRepository empleadoRepository;

    @Test
    public void findEmpleadoById() {
        EmpleadoEntity buscado = EmpleadoEntity.builder()
                .idEmpleado(1)
                .nombre("Margarita Gonzalez")
                .centro(CentroEntity.builder()
                        .numCentro(101)
                        .nombreCentro("Centro A")
                        .build())
                .build();

        // Configuración del comportamiento simulado del repositorio
        Mockito.when(empleadoRepository.findById(buscado.getIdEmpleado())).thenReturn(Optional.of(buscado));
        // Lógica de la prueba
        Optional<EmpleadoEntity> encontrado = empleadoRepository.findById(buscado.getIdEmpleado());
        // Verificación de la prueba
        Assertions.assertTrue(encontrado.isPresent(), "Se esperaba encontrar un empleado con el ID proporcionado");
        EmpleadoEntity empleadoEncontrado = encontrado.get();
        Assertions.assertEquals(buscado.getIdEmpleado(), empleadoEncontrado.getIdEmpleado(),
                "El ID del empleado encontrado no coincide con el ID del empleado buscado");
    }

    @Test
    public void findEmpleadoByName() {
        // Configuración del objeto empleado simulado
        String nombreBuscado = "Margarita Gonzalez";
        EmpleadoEntity buscado = EmpleadoEntity.builder()
                .idEmpleado(1)
                .nombre(nombreBuscado)
                .centro(CentroEntity.builder()
                        .numCentro(101)
                        .nombreCentro("Centro A")
                        .build())
                .build();

        // Configuración del comportamiento simulado del repositorio
        List<EmpleadoEntity> listaSimulada = new ArrayList<>();
        listaSimulada.add(buscado);
        Mockito.when(empleadoRepository.findByNombre(nombreBuscado)).thenReturn(listaSimulada);

        // Lógica de la prueba
        List<EmpleadoEntity> encontrados = empleadoRepository.findByNombre(nombreBuscado);

        // Verificación de la prueba
        Assertions.assertFalse(encontrados.isEmpty(), "Se esperaba encontrar al menos un empleado con el nombre proporcionado");
        EmpleadoEntity empleadoEncontrado = encontrados.get(0);
        Assertions.assertEquals(nombreBuscado, empleadoEncontrado.getNombre(),
                "El nombre del empleado encontrado no coincide con el nombre del empleado buscado");
    }

    @Test
    public void findEmpleadoByCenter() {
        // Configuración del objeto empleado simulado
        int numCentroBuscado = 101;
        EmpleadoEntity buscado = EmpleadoEntity.builder()
                .idEmpleado(1)
                .nombre("Margarita Gonzalez")
                .centro(CentroEntity.builder()
                        .numCentro(numCentroBuscado)
                        .nombreCentro("Centro A")
                        .build())
                .build();

        // Configuración del comportamiento simulado del repositorio
        List<EmpleadoEntity> listaSimulada = new ArrayList<>();
        listaSimulada.add(buscado);
        Mockito.when(empleadoRepository.findByIdCentro(numCentroBuscado)).thenReturn(listaSimulada);

        // Lógica de la prueba
        List<EmpleadoEntity> encontrados = empleadoRepository.findByIdCentro(numCentroBuscado);

        // Verificación de la prueba
        Assertions.assertFalse(encontrados.isEmpty(), "Se esperaba encontrar al menos un empleado con el centro proporcionado");
        EmpleadoEntity empleadoEncontrado = encontrados.get(0);
        Assertions.assertEquals(numCentroBuscado, empleadoEncontrado.getCentro().getNumCentro(),
                "El número del centro del empleado encontrado no coincide con el número del centro del empleado buscado");
    }
}

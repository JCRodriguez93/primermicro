package es.ejemplo.primermicro.controller;

import es.ejemplo.primermicro.entity.CentroEntity;
import es.ejemplo.primermicro.service.CentroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class CentroControllerMockTest {


    @Mock
    private CentroService centroService;

    @InjectMocks
    private CentroController centroController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCentroById_Exist() {
        // Arrange

        CentroEntity centroMock =  CentroEntity.builder()
                .nombreCentro("ViewNext").numCentro(101).build();
        when(centroService.getCenterByIdCenter(centroMock.getNumCentro())).thenReturn(centroMock);

        // Act
        ResponseEntity<CentroEntity> response = centroController.getCenterById(centroMock.getNumCentro());

        // Assert
        assertEquals(centroMock, response.getBody());
        assertEquals(ResponseEntity.ok(centroMock), response);
    }

    @Test
    public void testGetCentroById_NotExist() {
        // Arrange
        Integer idCentro = 1;
        when(centroService.getCenterByIdCenter(idCentro)).thenReturn(null);

        // Act
        ResponseEntity<CentroEntity> response = centroController.getCenterById(idCentro);

        // Assert
        assertNull(response.getBody());
        assertEquals(ResponseEntity.notFound().build(), response);
    }


}

package es.ejemplo.primermicro.controller;

import es.ejemplo.primermicro.entity.CentroEntity;
import es.ejemplo.primermicro.service.CentroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    public void getAllCentersIfExistTest() {
        List<CentroEntity> centrosMock = new ArrayList<>();
        centrosMock.add(new CentroEntity(1, "Centro A"));
        centrosMock.add(new CentroEntity(2, "Centro B"));

        when(centroService.getAllCenters()).thenReturn(centrosMock);
        ResponseEntity<List<CentroEntity>> response = centroController.getAllCenters();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(centrosMock, response.getBody());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());

    }

    @Test
    public void getAllCentersIfNotExistTest() {
        List<CentroEntity> centrosMock = new ArrayList<>();
        when(centroService.getAllCenters()).thenReturn(centrosMock);

        ResponseEntity<List<CentroEntity>> response = centroController.getAllCenters();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }



    @Test
    public void getCenterByIdWhenCenterExistsTest() {

        CentroEntity centroMock =  CentroEntity.builder()
                .nombreCentro("ViewNext").
                numCentro(101)
                .build();

        when(centroService.getCenterByIdCenter(centroMock.getNumCentro())).thenReturn(centroMock);


        ResponseEntity<CentroEntity> response = centroController.getCenterById(centroMock.getNumCentro());

        assertEquals(centroMock, response.getBody());
        assertEquals(ResponseEntity.ok(centroMock), response);
    }

    @Test
    public void getCenterByIdWhenCenterDoesNotExistTest() {
        Integer idCentro = 1;
        when(centroService.getCenterByIdCenter(idCentro)).thenReturn(null);

        ResponseEntity<CentroEntity> response = centroController.getCenterById(idCentro);

        assertNull(response.getBody());
        assertEquals(ResponseEntity.notFound().build(), response);
    }


}

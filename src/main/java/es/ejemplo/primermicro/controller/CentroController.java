package es.ejemplo.primermicro.controller;
import lombok.extern.slf4j.Slf4j;
import es.ejemplo.primermicro.entity.CentroEntity;
import es.ejemplo.primermicro.service.CentroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/centros")
public class CentroController {

    @Autowired
    private CentroService centroService;

    @GetMapping()
    public ResponseEntity<List<CentroEntity>>getAllCenters(){
        log.info("Fetching all centers...");
        List<CentroEntity> listaEmpleados = centroService.getAllCenters();
        if (listaEmpleados.isEmpty()) {
            log.warn("No centers found.");
            return ResponseEntity.noContent().build();
        }
        log.info("Returning list of centers.");
        return ResponseEntity.ok(listaEmpleados);
    }


    @GetMapping(value = "/{idCentro}")
    public ResponseEntity<CentroEntity> getCenterById(@PathVariable("idCentro") Integer idCentro){
        log.info("Fetching center with ID: {}", idCentro);
        CentroEntity centro = centroService.getCenterByIdCenter(idCentro);
        if(centro == null){
            log.warn("Center with ID {} not found", idCentro);
            return ResponseEntity.notFound().build();
        }
        log.info("Center with ID {} found: {}", idCentro, centro);
        return ResponseEntity.ok(centro);
    }

}

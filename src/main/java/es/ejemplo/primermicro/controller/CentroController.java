package es.ejemplo.primermicro.controller;

import es.ejemplo.primermicro.entity.CentroEntity;
import es.ejemplo.primermicro.service.CentroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/centros")
public class CentroController {

    @Autowired
    private CentroService centroService;

    @GetMapping(value = "/{idCentro}") 
    public ResponseEntity<CentroEntity> getCenterById(@PathVariable("idCentro") Integer idCentro){
        CentroEntity centro = centroService.getCentroById(idCentro);
        if(centro == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(centro);
    }

}

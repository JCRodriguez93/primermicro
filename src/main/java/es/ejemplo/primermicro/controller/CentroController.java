package es.ejemplo.primermicro.controller;

import es.ejemplo.primermicro.entity.CentroEntity;
import es.ejemplo.primermicro.entity.EmpleadoEntity;
import es.ejemplo.primermicro.service.CentroService;
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
@RequestMapping(value = "/centros")
public class CentroController {

    @Autowired
    private CentroService centroService;

    @GetMapping(value = "/{idCentro}") //probar
    public ResponseEntity<CentroEntity> getCentroById(@PathVariable("idCentro") Integer idCentro){
        CentroEntity centro = centroService.getCentroById(idCentro);
        if(centro == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(centro);
    }

}

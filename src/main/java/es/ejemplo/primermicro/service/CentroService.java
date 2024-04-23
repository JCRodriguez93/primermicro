package es.ejemplo.primermicro.service;

import es.ejemplo.primermicro.entity.CentroEntity;

import java.util.List;


public interface CentroService {

    CentroEntity getCenterByIdCenter(Integer id);
    List<CentroEntity> getAllCenters();
}

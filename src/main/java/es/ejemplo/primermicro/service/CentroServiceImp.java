package es.ejemplo.primermicro.service;

import es.ejemplo.primermicro.entity.CentroEntity;
import es.ejemplo.primermicro.repository.CentroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CentroServiceImp implements CentroService{

    private final CentroRepository centroRepository;
    @Override
    public CentroEntity getCentroById(Integer id) {
        return centroRepository.findByNumCentro(id);
    }
}

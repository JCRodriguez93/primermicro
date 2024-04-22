package es.ejemplo.primermicro.repository;

import es.ejemplo.primermicro.entity.CentroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroRepository extends JpaRepository<CentroEntity, Integer> {

    CentroEntity findByNumCentro(Integer numCentro);
}

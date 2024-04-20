package es.ejemplo.primermicro.repository;

import es.ejemplo.primermicro.entity.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Integer> {

    List<EmpleadoEntity> findByNombre(String nombre);

    List<EmpleadoEntity> findByIdCentro(int idCentro);
}


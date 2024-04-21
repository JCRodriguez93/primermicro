package es.ejemplo.primermicro.service;

import es.ejemplo.primermicro.entity.EmpleadoEntity;
import es.ejemplo.primermicro.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImp implements EmpleadoService {


    private final EmpleadoRepository empleadoRepository;


    @Override
    public List<EmpleadoEntity> listAllEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public EmpleadoEntity getEmpleadoById(Integer id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public List<EmpleadoEntity> getEmpleadoByNombre(String nombre) {
        return empleadoRepository.findByNombre(nombre).stream().toList();

    }

    @Override
    public List<EmpleadoEntity> getEmpleadoByIdCentro(Integer id) {
        return empleadoRepository.findByIdCentro(id);
    }

    @Override
    public EmpleadoEntity createEmpleado(EmpleadoEntity empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    @Transactional
    public EmpleadoEntity updateEmpleado(Integer id, EmpleadoEntity empleado) {
        Optional<EmpleadoEntity> empleadoDB = empleadoRepository.findById(id);
        if (empleadoDB == null) {
            return null;
        }
        empleado.setIdEmpleado(id);

        return empleadoRepository.save(empleado);
    }

    @Override
    public EmpleadoEntity deleteEmpleado(Integer id) {
        EmpleadoEntity empleadoDB = getEmpleadoById(id);
        if (empleadoDB == null) {
            return null;
        }
        empleadoRepository.delete(empleadoDB);
        return empleadoDB;

    }

}
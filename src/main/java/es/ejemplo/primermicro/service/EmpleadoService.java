package es.ejemplo.primermicro.service;

import es.ejemplo.primermicro.entity.EmpleadoEntity;

import java.util.List;

public interface EmpleadoService {

    public List<EmpleadoEntity> listAllEmpleados();

    public EmpleadoEntity getEmpleadoById(Integer id);

    public EmpleadoEntity createEmpleado(EmpleadoEntity empleado);

    public EmpleadoEntity updateEmpleado(Integer id, EmpleadoEntity empleado);

    public EmpleadoEntity deleteEmpleado(Integer id);


}

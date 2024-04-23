package es.ejemplo.primermicro.service;

import es.ejemplo.primermicro.entity.EmpleadoEntity;

import java.util.List;

public interface EmpleadoService {

    public List<EmpleadoEntity> listAllEmployees();

    public EmpleadoEntity getEmployeeById(Integer id);

    public List<EmpleadoEntity> getEmployeeByName(String nombre);

    public List<EmpleadoEntity> getEmployeeByCenterId(Integer id);

    public EmpleadoEntity createEmployee(EmpleadoEntity empleado);

    public EmpleadoEntity updateEmployee(Integer id, EmpleadoEntity empleado);

    public EmpleadoEntity deleteEmployee(Integer id);


}

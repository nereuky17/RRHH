package com.rrhh.empresa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rrhh.empresa.model.Empleado;

import com.rrhh.empresa.repository.EmpleadoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public Empleado save(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Optional<Empleado> findById(Long id) {
        return empleadoRepository.findById(id);
    }

       public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    public void delete(Empleado empleado) {
        empleadoRepository.delete(empleado);
    }
}

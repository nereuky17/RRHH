package com.rrhh.empresa.service;

import com.rrhh.empresa.dto.EmpleadoDTO;
import com.rrhh.empresa.dto.EmpresaDTO;
import com.rrhh.empresa.model.Empleado;
import com.rrhh.empresa.model.Empresa;
import com.rrhh.empresa.repository.EmpleadoRepository;
import com.rrhh.empresa.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;


    public Empresa guardarEmpresa(Empresa empresa) {
        
        return empresaRepository.save(empresa);
    }

   
    public List<Empresa> findAllEmpresas() {
        return empresaRepository.findAll();
    }

    public List<Empresa> findAllEmpresasWithEmpleados() {
        List<Empresa> empresas = empresaRepository.findAll();
        for (Empresa empresa : empresas) {
            List<Empleado> empleados = empleadoRepository.findByEmpresaId(empresa.getId());
            empresa.setEmpleados(empleados);
        }
        return empresas;
    }

    // Obtener todos los empleados de todas las empresas
    public List<Empleado> findAllEmpleados() {
        return empleadoRepository.findAll();
    }

    // Buscar empresa por ID
    public Empresa findById(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
    }

    // Obtener empleados por ID de empresa
    public List<Empleado> findEmpleadosByEmpresaId(Long empresaId) {
        Empresa empresa = findById(empresaId);
        return empresa.getEmpleados(); 
    }
    
    public void eliminarEmpresa(Long id) {
        Empresa empresa = findById(id);
        if (!empresa.getEmpleados().isEmpty()) {
            throw new RuntimeException("No se puede eliminar la empresa porque tiene empleados asociados. Elimine primero los empleados.");
        }
        empresaRepository.deleteById(id);
    }
    

    // Método para convertir de Empresa a EmpresaDTO
    public EmpresaDTO convertToDTO(Empresa empresa) {
        return new EmpresaDTO(
            empresa.getId(),
            empresa.getNombre(),
            empresa.getCif(),
            empresa.getDireccion(),
            empresa.getTelefono(),
            empresa.getEmail(),
            empresa.getPaginaWeb(),
            empresa.getSector(),
            empresa.getPais(),
            empresa.getEmpleados().stream()
                .map(empleado -> new EmpleadoDTO(
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getDni(),
                    empleado.getEmail(),
                    empleado.getTelefono(),
                    empleado.getPosicion()
                ))
                .collect(Collectors.toList())
        );
    }
}

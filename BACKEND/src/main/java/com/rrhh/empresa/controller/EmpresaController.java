package com.rrhh.empresa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.rrhh.empresa.dto.EmpleadoDTO;
import com.rrhh.empresa.dto.EmpresaDTO;
import com.rrhh.empresa.model.Empresa;
import com.rrhh.empresa.service.EmpresaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/api/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    // Listar todas las empresas junto con sus empleados
    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> listarTodasLasEmpresas() {
        List<EmpresaDTO> empresas = empresaService.findAllEmpresasWithEmpleados().stream()
                .map(empresa -> new EmpresaDTO(
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
                                        empleado.getPosicion(),
                                        empleado.getFechaContratacion().toString()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(empresas);
    }

    // Listar todos los empleados de todas las empresas
    @GetMapping("/empleados")
    public ResponseEntity<List<EmpleadoDTO>> listarTodosLosEmpleados() {
        List<EmpleadoDTO> empleados = empresaService.findAllEmpleados().stream()
                .map(empleado -> new EmpleadoDTO(
                        empleado.getId(),
                        empleado.getNombre(),
                        empleado.getApellido(),
                        empleado.getDni(),
                        empleado.getEmail(),
                        empleado.getTelefono(),
                        empleado.getPosicion(),
                        empleado.getFechaContratacion().toString()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(empleados);
    }

    // Obtener los detalles de una empresa por ID
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> obtenerEmpresaPorId(@PathVariable Long id) {
        Empresa empresa = empresaService.findById(id);
        EmpresaDTO empresaDTO = new EmpresaDTO(
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
                                empleado.getPosicion(),
                                empleado.getFechaContratacion().toString()
                        ))
                        .collect(Collectors.toList())
        );
        return ResponseEntity.ok(empresaDTO);
    }

    // Obtener los empleados de una empresa específica por ID
    @GetMapping("/{id}/empleados")
    public ResponseEntity<List<EmpleadoDTO>> listarEmpleadosPorEmpresaId(@PathVariable Long id) {
        List<EmpleadoDTO> empleados = empresaService.findEmpleadosByEmpresaId(id).stream()
                .map(empleado -> new EmpleadoDTO(
                        empleado.getId(),
                        empleado.getNombre(),
                        empleado.getApellido(),
                        empleado.getDni(),
                        empleado.getEmail(),
                        empleado.getTelefono(),
                        empleado.getPosicion(),
                        empleado.getFechaContratacion().toString()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(empleados);
    }
}
package com.rrhh.empresa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rrhh.empresa.dto.EmpleadoDTO;
import com.rrhh.empresa.model.Empleado;
import com.rrhh.empresa.model.Empresa;
import com.rrhh.empresa.service.EmpleadoService;
import com.rrhh.empresa.service.EmpresaService;

import java.util.Optional;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private EmpresaService empresaService;

    // Crear un nuevo empleado
    @PostMapping("/empresa/{idEmpresa}")
    public ResponseEntity<EmpleadoDTO> crearEmpleado(@PathVariable Long idEmpresa, @RequestBody EmpleadoDTO empleadoDTO) {
        Empresa empresa = empresaService.findById(idEmpresa);
        Empleado empleado = new Empleado(
                empresa,
                empleadoDTO.getNombre(),
                empleadoDTO.getApellido(),
                empleadoDTO.getDni(),
                empleadoDTO.getEmail(),
                empleadoDTO.getTelefono(),
                empleadoDTO.getPosicion(),
                java.sql.Date.valueOf(empleadoDTO.getFechaContratacion())
        );

        empleado = empleadoService.save(empleado);
        EmpleadoDTO responseDTO = new EmpleadoDTO(
                empleado.getId(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getDni(),
                empleado.getEmail(),
                empleado.getTelefono(),
                empleado.getPosicion(),
                empleado.getFechaContratacion().toString()
        );

        return ResponseEntity.ok(responseDTO);
    }

    // Obtener un empleado por ID
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> obtenerEmpleado(@PathVariable Long id) {
        Optional<Empleado> empleadoOpt = empleadoService.findById(id);
        if (empleadoOpt.isPresent()) {
            Empleado empleado = empleadoOpt.get();
            EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getDni(),
                    empleado.getEmail(),
                    empleado.getTelefono(),
                    empleado.getPosicion(),
                    empleado.getFechaContratacion().toString()
            );
            return ResponseEntity.ok(empleadoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar empleado por ID
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> actualizarEmpleado(@PathVariable Long id, @RequestBody EmpleadoDTO empleadoDTO) {
        Optional<Empleado> empleadoOpt = empleadoService.findById(id);
        if (empleadoOpt.isPresent()) {
            Empleado empleado = empleadoOpt.get();
            empleado.setNombre(empleadoDTO.getNombre());
            empleado.setApellido(empleadoDTO.getApellido());
            empleado.setDni(empleadoDTO.getDni());
            empleado.setEmail(empleadoDTO.getEmail());
            empleado.setTelefono(empleadoDTO.getTelefono());
            empleado.setPosicion(empleadoDTO.getPosicion());
            empleado.setFechaContratacion(java.sql.Date.valueOf(empleadoDTO.getFechaContratacion()));

            empleado = empleadoService.save(empleado);

            EmpleadoDTO responseDTO = new EmpleadoDTO(
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getDni(),
                    empleado.getEmail(),
                    empleado.getTelefono(),
                    empleado.getPosicion(),
                    empleado.getFechaContratacion().toString()
            );

            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar empleado por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        Optional<Empleado> empleadoOpt = empleadoService.findById(id);
        if (empleadoOpt.isPresent()) {
            empleadoService.delete(empleadoOpt.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

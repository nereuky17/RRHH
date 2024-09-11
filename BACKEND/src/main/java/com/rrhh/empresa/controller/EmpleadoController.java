package com.rrhh.empresa.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.rrhh.empresa.dto.EmpleadoDTO;
import com.rrhh.empresa.model.Empleado;
import com.rrhh.empresa.model.Empresa;
import com.rrhh.empresa.service.EmpleadoService;
import com.rrhh.empresa.service.EmpresaService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private EmpresaService empresaService;

    // Crear empleado para una empresa
    @PostMapping("/empresa/{idEmpresa}")
    public ResponseEntity<EmpleadoDTO> crearEmpleado(@PathVariable Long idEmpresa, @RequestBody EmpleadoDTO empleadoDTO) {
        Empresa empresa = empresaService.findById(idEmpresa);
        if (empresa == null) {
            return ResponseEntity.badRequest().body(null);  
        }
    
        Empleado empleado = new Empleado();
        empleado.setEmpresa(empresa);
        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setApellido(empleadoDTO.getApellido());
        empleado.setDni(empleadoDTO.getDni());
        empleado.setEmail(empleadoDTO.getEmail());
        empleado.setTelefono(empleadoDTO.getTelefono());
        empleado.setPosicion(empleadoDTO.getPosicion());
        empleado.setFechaContratacion(empleadoDTO.getFechaContratacion());
    
        empleado = empleadoService.save(empleado);
    
        EmpleadoDTO responseDTO = new EmpleadoDTO(
            empleado.getId(),
            empleado.getNombre(),
            empleado.getApellido(),
            empleado.getDni(),
            empleado.getEmail(),
            empleado.getTelefono(),
            empleado.getPosicion(),
            empleado.getFechaContratacion()
        );
    
        return ResponseEntity.ok(responseDTO);
    }

    // Obtener todos los empleados
    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> listarTodosLosEmpleados() {
        List<EmpleadoDTO> empleados = empleadoService.findAll().stream()
                .map(empleado -> new EmpleadoDTO(
                        empleado.getId(),
                        empleado.getNombre(),
                        empleado.getApellido(),
                        empleado.getDni(),
                        empleado.getEmail(),
                        empleado.getTelefono(),
                        empleado.getPosicion(),
                        empleado.getFechaContratacion()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(empleados);
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
                    empleado.getFechaContratacion()
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

            if (empleadoDTO.getNombre() != null && !empleadoDTO.getNombre().isBlank()) {
                empleado.setNombre(empleadoDTO.getNombre());
            }

            if (empleadoDTO.getApellido() != null && !empleadoDTO.getApellido().isBlank()) {
                empleado.setApellido(empleadoDTO.getApellido());
            }

            if (empleadoDTO.getDni() != null && !empleadoDTO.getDni().isBlank()) {
                empleado.setDni(empleadoDTO.getDni());
            }

            if (empleadoDTO.getEmail() != null && !empleadoDTO.getEmail().isBlank()) {
                empleado.setEmail(empleadoDTO.getEmail());
            }

            if (empleadoDTO.getTelefono() != null && !empleadoDTO.getTelefono().isBlank()) {
                empleado.setTelefono(empleadoDTO.getTelefono());
            }

            if (empleadoDTO.getPosicion() != null && !empleadoDTO.getPosicion().isBlank()) {
                empleado.setPosicion(empleadoDTO.getPosicion());
            }

            if (empleadoDTO.getFechaContratacion() != null) {
                empleado.setFechaContratacion(empleadoDTO.getFechaContratacion());
            }

            empleado = empleadoService.save(empleado);

            EmpleadoDTO responseDTO = new EmpleadoDTO(
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getDni(),
                    empleado.getEmail(),
                    empleado.getTelefono(),
                    empleado.getPosicion(),
                    empleado.getFechaContratacion()
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

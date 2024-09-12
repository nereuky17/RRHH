package com.rrhh.empresa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.rrhh.empresa.dto.EmpleadoDTO;
import com.rrhh.empresa.dto.EmpresaDTO;
import com.rrhh.empresa.model.Empleado;
import com.rrhh.empresa.model.Empresa;
import com.rrhh.empresa.service.EmpresaService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/api/empresas")
public class EmpresaController {

        @Autowired
        private EmpresaService empresaService;

        @PutMapping("/{id}")
        public ResponseEntity<EmpresaDTO> actualizarEmpresa(@PathVariable Long id,
                        @Valid @RequestBody EmpresaDTO empresaDTO) {
                Empresa empresaExistente = empresaService.findById(id);

                if (empresaExistente == null) {
                        return ResponseEntity.notFound().build();
                }

                empresaExistente.setNombre(empresaDTO.getNombre());
                empresaExistente.setCif(empresaDTO.getCif());
                empresaExistente.setDireccion(empresaDTO.getDireccion());
                empresaExistente.setTelefono(empresaDTO.getTelefono());
                empresaExistente.setEmail(empresaDTO.getEmail());
                empresaExistente.setPaginaWeb(empresaDTO.getPaginaWeb());
                empresaExistente.setSector(empresaDTO.getSector());
                empresaExistente.setPais(empresaDTO.getPais());

                Empresa empresaActualizada = empresaService.guardarEmpresa(empresaExistente);

                EmpresaDTO empresaActualizadaDTO = new EmpresaDTO(
                                empresaActualizada.getId(),
                                empresaActualizada.getNombre(),
                                empresaActualizada.getCif(),
                                empresaActualizada.getDireccion(),
                                empresaActualizada.getTelefono(),
                                empresaActualizada.getEmail(),
                                empresaActualizada.getPaginaWeb(),
                                empresaActualizada.getSector(),
                                empresaActualizada.getPais(),
                                null // No cargamos empleados aquí
                );

                return ResponseEntity.ok(empresaActualizadaDTO);
        }

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
                                                                                empleado.getPosicion()))
                                                                .collect(Collectors.toList())))
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
                                                empleado.getPosicion()))
                                .collect(Collectors.toList());

                return ResponseEntity.ok(empleados);
        }

        // Eliminar empresa por ID
        @DeleteMapping("/{id}")
        public ResponseEntity<String> eliminarEmpresa(@PathVariable Long id) {
                try {
                        empresaService.eliminarEmpresa(id);
                        return ResponseEntity.noContent().build();
                } catch (RuntimeException e) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
                }
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
                                                                empleado.getPosicion()))
                                                .collect(Collectors.toList()));
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
                                                empleado.getPosicion()))
                                .collect(Collectors.toList());

                return ResponseEntity.ok(empleados);
        }

        @PostMapping
        public ResponseEntity<EmpresaDTO> crearEmpresa(@Valid @RequestBody EmpresaDTO empresaDTO) {
                Empresa nuevaEmpresa = new Empresa(
                                empresaDTO.getNombre(),
                                empresaDTO.getCif(),
                                empresaDTO.getDireccion(),
                                empresaDTO.getTelefono(),
                                empresaDTO.getEmail(),
                                empresaDTO.getPaginaWeb(),
                                empresaDTO.getSector(),
                                empresaDTO.getPais());

                Empresa empresaGuardada = empresaService.guardarEmpresa(nuevaEmpresa);

                EmpresaDTO empresaGuardadaDTO = new EmpresaDTO(
                                empresaGuardada.getId(),
                                empresaGuardada.getNombre(),
                                empresaGuardada.getCif(),
                                empresaGuardada.getDireccion(),
                                empresaGuardada.getTelefono(),
                                empresaGuardada.getEmail(),
                                empresaGuardada.getPaginaWeb(),
                                empresaGuardada.getSector(),
                                empresaGuardada.getPais(),
                                null // No cargamos empleados aquí, si es necesario cargar, agregar lógica
                );

                return ResponseEntity.status(HttpStatus.CREATED).body(empresaGuardadaDTO);
        }

}

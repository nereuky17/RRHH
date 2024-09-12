package com.rrhh.empresa;

import com.rrhh.empresa.model.Empleado;
import com.rrhh.empresa.model.Empresa;
import com.rrhh.empresa.repository.EmpleadoRepository;
import com.rrhh.empresa.repository.EmpresaRepository;
import com.rrhh.empresa.service.EmpleadoService;
import com.rrhh.empresa.service.EmpresaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

public class TestBack {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    @InjectMocks
    private EmpresaService empresaService;

    private Empleado empleado;
    private Empresa empresa;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        empleado = new Empleado();
        empleado.setId(1L);

        empresa = new Empresa();
        empresa.setId(1L);
    }

    // Test para listar todas las empresas
    @Test
    public void testFindAllEmpresas() {
        when(empresaRepository.findAll()).thenReturn(List.of(empresa));

        List<Empresa> result = empresaService.findAllEmpresas();

        assertEquals(1, result.size());
        verify(empresaRepository, times(1)).findAll();
    }

    // Test para buscar empresa por ID
    @Test
    public void testFindByIdEmpresa() {
        // Simulamos que el repositorio devuelve un Optional con una Empresa
        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));

        // Llamamos al servicio para obtener la empresa
        Empresa result = empresaService.findById(1L);

        // Verificamos que el ID de la empresa es el esperado
        assertEquals(empresa.getId(), result.getId());
        verify(empresaRepository, times(1)).findById(1L);
    }

    @Test
    public void testEliminarEmpresa() {
        // Simulamos una empresa con una lista de empleados vacía
        empresa.setEmpleados(new ArrayList<>());

        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));
        doNothing().when(empresaRepository).deleteById(1L);

        empresaService.eliminarEmpresa(1L);

        verify(empresaRepository, times(1)).deleteById(1L);
    }

    // Test para listar todos los empleados
    @Test
    public void testFindAllEmpleados() {
        when(empleadoRepository.findAll()).thenReturn(List.of(empleado));

        List<Empleado> result = empleadoService.findAll();

        assertEquals(1, result.size());
        verify(empleadoRepository, times(1)).findAll();
    }

    // Test para buscar empleado por ID
    @Test
    public void testFindByIdEmpleado() {
        // Simulamos que el repositorio devuelve un Optional con un Empleado
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));

        // Llamamos al servicio para obtener el empleado
        Optional<Empleado> result = empleadoService.findById(1L);

        // Verificamos que el empleado existe y tiene el ID correcto
        assertEquals(true, result.isPresent());
        assertEquals(empleado.getId(), result.get().getId());
        verify(empleadoRepository, times(1)).findById(1L);
    }

    // Test para eliminar empleado
    @Test
    public void testEliminarEmpleado() {
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));
        doNothing().when(empleadoRepository).delete(empleado);

        empleadoService.delete(empleado);

        verify(empleadoRepository, times(1)).delete(empleado);
    }
}

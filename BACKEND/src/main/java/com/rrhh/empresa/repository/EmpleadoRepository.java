package com.rrhh.empresa.repository;

import com.rrhh.empresa.model.Empleado;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    List<Empleado> findByEmpresaId(Long empresaId);
    
}

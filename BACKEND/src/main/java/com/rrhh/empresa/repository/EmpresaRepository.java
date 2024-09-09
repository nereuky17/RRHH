package com.rrhh.empresa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rrhh.empresa.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}

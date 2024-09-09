package com.rrhh.empresa.dto;

import java.util.List;

public class EmpresaDTO {

    private Long id;
    private String nombre;
    private String cif;
    private String direccion;
    private String telefono;
    private String email;
    private String pagina_web;
    private String sector;
    private String pais;
    private List<EmpleadoDTO> empleados;

    // Constructor vacío
    public EmpresaDTO() {
    }

    // Constructor con parámetros
    public EmpresaDTO(Long id, String nombre, String cif, String direccion, String telefono, String email, String pagina_web, String sector, String pais, List<EmpleadoDTO> empleados) {
        this.id = id;
        this.nombre = nombre;
        this.cif = cif;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.pagina_web = pagina_web;
        this.sector = sector;
        this.pais = pais;
        this.empleados = empleados;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaginaWeb() {
        return pagina_web;
    }

    public void setPaginaWeb(String pagina_web) {
        this.pagina_web = pagina_web;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public List<EmpleadoDTO> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<EmpleadoDTO> empleados) {
        this.empleados = empleados;
    }
}
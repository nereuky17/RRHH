package com.rrhh.empresa.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "EMPRESA")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre de la empresa debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Column(nullable = false, unique = true) // Agregamos la restricción de unicidad para el CIF
    @NotBlank(message = "El CIF de la empresa es obligatorio")
    @Pattern(regexp = "[A-Z0-9]{8,10}", message = "El CIF debe tener entre 8 y 10 caracteres alfanuméricos")
    private String cif;

    @Column
    @Size(min = 5, max = 255, message = "La dirección debe tener entre 5 y 255 caracteres")
    private String direccion;

    @Column
    @Pattern(regexp = "^[0-9]{8,15}$", message = "El teléfono debe ser un número válido con un máximo de 15 dígitos")
    private String telefono;

    @Column
    @Email(message = "El email debe tener un formato válido")
    @Size(max = 255, message = "El email no puede exceder los 255 caracteres")
    private String email;

    @Column
    @Size(max = 100, message = "La página web no puede exceder los 100 caracteres")
    private String pagina_web;

    @Column
    @Size(min = 2, max = 50, message = "El sector debe tener entre 2 y 50 caracteres")
    private String sector;

    @Column
    @Size(min = 2, max = 50, message = "El país debe tener entre 2 y 50 caracteres")
    private String pais;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Empleado> empleados=new ArrayList<>();

    // Constructores
    public Empresa() {
    }

    public Empresa(String nombre, String cif, String direccion, String telefono, String email, String pagina_web, String sector, String pais) {
        this.nombre = nombre;
        this.cif = cif;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.pagina_web = pagina_web;
        this.sector = sector;
        this.pais = pais;
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

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
}

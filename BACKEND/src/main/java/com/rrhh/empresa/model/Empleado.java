package com.rrhh.empresa.model;

import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "EMPLEADO")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    @NotNull(message = "La empresa es obligatoria")
    private Empresa empresa;

    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @Column
    @Size(max = 50, message = "El apellido no puede exceder los 50 caracteres")
    private String apellido;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "[0-9]{8}[A-Z]", message = "El DNI debe tener 8 números seguidos de una letra")
    private String dni;

    @Column
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @Column
    @Pattern(regexp = "^[0-9]{8,15}$", message = "El teléfono debe ser un número válido con un máximo de 15 dígitos")
    private String telefono;

    @Column
    @Size(max = 50, message = "La posición no puede exceder los 50 caracteres")
    private String posicion;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "La fecha de contratación es obligatoria")
    private Date fechaContratacion;

    // Constructores
    public Empleado() {
    }

    public Empleado(Empresa empresa, String nombre, String apellido, String dni, String email, String telefono, String posicion, Date fechaContratacion) {
        this.empresa = empresa;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.posicion = posicion;
        this.fechaContratacion = fechaContratacion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }
}
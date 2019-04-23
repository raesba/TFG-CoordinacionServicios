package com.raesba.tfg_coordinacionservicios;

import java.io.Serializable;

public class Proveedor implements Serializable {

    public Proveedor(int idProveedor) {

    }

    private int idProveedor;
    private String email;
    private String password;
    private String nombre;
    private String dni;
    private String direccion;
    private String poblacion;
    private String provincia;
    private String telefonoFijo;
    private String movil;
    private String profesion;
    private float precioHora;
    private String descripcion;

    public Proveedor() {
    }

    public Proveedor(int idProveedor, String email, String password, String nombre, String dni,
                     String direccion, String poblacion, String provincia, String telefonoFijo,
                     String movil, String profesion, float precioHora, String descripcion) {

        this.idProveedor = idProveedor;
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.dni = dni;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.telefonoFijo = telefonoFijo;
        this.movil = movil;
        this.profesion = profesion;
        this.precioHora = precioHora;
        this.descripcion = descripcion;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public float getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(float precioHora) {
        this.precioHora = precioHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

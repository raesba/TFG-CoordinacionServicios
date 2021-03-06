package com.raesba.tfg_coordinacionservicios.data.modelo.user;

import java.io.Serializable;
import java.util.HashMap;

public class Proveedor extends Usuario implements Serializable  {

    private String nombre;
    private String dni;
    private String profesion;
    private float precioHora;
    private String descripcion;
    private HashMap<String, Boolean> disposiciones;

    public Proveedor() {
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

    public HashMap<String, Boolean> getDisposiciones() {
        return disposiciones;
    }

    public void setDisposiciones(HashMap<String, Boolean> disposiciones) {
        this.disposiciones = disposiciones;
    }
}

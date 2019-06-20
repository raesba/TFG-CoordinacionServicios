package com.raesba.tfg_coordinacionservicios.data.modelo.negocio;

public class Disposicion {

    private String idDisposicion;
    private String fechaDisponible;
    private String idProveedor;
    private String profesion;
    private long horaInicio;
    private long horaFin;

    public Disposicion() {
    }

    public String getIdDisposicion() {
        return idDisposicion;
    }

    public void setIdDisposicion(String idDisposicion) {
        this.idDisposicion = idDisposicion;
    }

    public String getFechaDisponible() {
        return fechaDisponible;
    }

    public void setFechaDisponible(String fechaDisponible) {
        this.fechaDisponible = fechaDisponible;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public long getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(long horaInicio) {
        this.horaInicio = horaInicio;
    }

    public long getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(long horaFin) {
        this.horaFin = horaFin;
    }
}

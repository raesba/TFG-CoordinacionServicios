package com.raesba.tfg_coordinacionservicios;

public class Disposicion {

    private int idDisposicion;
    private String fechaDisponible;
    private int idProveedor;
    private int profesion;
    private long horaInicio;
    private long horaFin;

    public Disposicion() {
    }

    public Disposicion(int idDisposicion, String fechaDisponible, int idProveedor, int profesion, long horaInicio, long horaFin) {
        this.idDisposicion = idDisposicion;
        this.fechaDisponible = fechaDisponible;
        this.idProveedor = idProveedor;
        this.profesion = profesion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getIdDisposicion() {
        return idDisposicion;
    }

    public void setIdDisposicion(int idDisposicion) {
        this.idDisposicion = idDisposicion;
    }

    public String getFechaDisponible() {
        return fechaDisponible;
    }

    public void setFechaDisponible(String fechaDisponible) {
        this.fechaDisponible = fechaDisponible;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getProfesion() {
        return profesion;
    }

    public void setProfesion(int profesion) {
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

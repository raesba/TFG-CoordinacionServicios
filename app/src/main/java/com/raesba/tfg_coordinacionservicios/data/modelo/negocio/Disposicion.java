package com.raesba.tfg_coordinacionservicios.data.modelo.negocio;

import java.io.Serializable;

public class Disposicion implements Serializable {

    private String idDisposicion;
    private long fechaDisposicion;
    private String uidProveedor;
    private String uidTransaccion;
    private String profesionProveedor;
    private boolean estadoDisposicion;
    private long updatedAt;

    public Disposicion() {
    }

    public String getIdDisposicion() {
        return idDisposicion;
    }

    public void setIdDisposicion(String idDisposicion) {
        this.idDisposicion = idDisposicion;
    }

    public long getFechaDisposicion() {
        return fechaDisposicion;
    }

    public void setFechaDisposicion(long fechaDisposicion) {
        this.fechaDisposicion = fechaDisposicion;
    }

    public boolean isEstadoDisposicion() {
        return estadoDisposicion;
    }

    public void setEstadoDisposicion(boolean estadoDisposicion) {
        this.estadoDisposicion = estadoDisposicion;
    }

    public String getUidProveedor() {
        return uidProveedor;
    }

    public void setUidProveedor(String uidProveedor) {
        this.uidProveedor = uidProveedor;
    }

    public String getUidTransaccion() {
        return uidTransaccion;
    }

    public void setUidTransaccion(String uidTransaccion) {
        this.uidTransaccion = uidTransaccion;
    }

    public String getProfesionProveedor() {
        return profesionProveedor;
    }

    public void setProfesionProveedor(String profesionProveedor) {
        this.profesionProveedor = profesionProveedor;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}

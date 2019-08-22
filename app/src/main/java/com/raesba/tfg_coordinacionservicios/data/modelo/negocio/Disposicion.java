package com.raesba.tfg_coordinacionservicios.data.modelo.negocio;

import java.io.Serializable;

public class Disposicion implements Serializable {

    private String uid;
    private long fecha;
    private boolean estado;
    private String uidProveedor;
    private String uidTransaccion;
    private String profesionProveedor;
    private long updatedAt;

    public Disposicion() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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

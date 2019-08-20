package com.raesba.tfg_coordinacionservicios.data.modelo.negocio;

public class Disposicion {

    private String idDisposicion;
    private String fechaDisponible;
    private String idProveedor;

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
}

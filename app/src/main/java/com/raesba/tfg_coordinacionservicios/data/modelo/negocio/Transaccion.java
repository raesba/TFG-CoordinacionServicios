package com.raesba.tfg_coordinacionservicios.data.modelo.negocio;

import java.io.Serializable;

public class Transaccion implements Serializable {

    private String idTransaccion;
    private long fechaCreacion;
    private String uidProveedor;
    private String uidEmpresa;
    private String nombreProveedor;
    private String nombreEmpresa;
    private String idDisposicion;
    private long fechaDisposicion;
    private Disposicion disposicion;
    private int estadoTransaccion; // 0 pendiente, 1 aceptada, 2 rechazada, 3 cancelada
    private String direccion;
    private double latitud;
    private double longitud;
    private double precioEstimado;
    private String observaciones;

    public Transaccion() {
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public long getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUidProveedor() {
        return uidProveedor;
    }

    public void setUidProveedor(String uidProveedor) {
        this.uidProveedor = uidProveedor;
    }

    public String getUidEmpresa() {
        return uidEmpresa;
    }

    public void setUidEmpresa(String uidEmpresa) {
        this.uidEmpresa = uidEmpresa;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
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

    public Disposicion getDisposicion() {
        return disposicion;
    }

    public void setDisposicion(Disposicion disposicion) {
        this.disposicion = disposicion;
    }

    public int getEstadoTransaccion() {
        return estadoTransaccion;
    }

    public void setEstadoTransaccion(int estadoTransaccion) {
        this.estadoTransaccion = estadoTransaccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getPrecioEstimado() {
        return precioEstimado;
    }

    public void setPrecioEstimado(double precioEstimado) {
        this.precioEstimado = precioEstimado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}

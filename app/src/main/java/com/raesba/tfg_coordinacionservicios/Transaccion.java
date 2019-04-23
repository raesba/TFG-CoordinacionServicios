package com.raesba.tfg_coordinacionservicios;

public class Transaccion {

    private int idTransaccion;
    private String fecha;
    private int idProveedor;
    private int idEmpresa;
    private int idDisposicion;
    private boolean aceptado;
    private double latitud;
    private double longitud;
    private double precioEstimado;
    private String observaciones;

    public Transaccion() {
    }

    public Transaccion(int idTransaccion, String fecha, int idProveedor, int idEmpresa,
                       int idDisposicion, boolean aceptado, double latitud, double longitud,
                       double precioEstimado, String observaciones) {

        this.idDisposicion = idDisposicion;
        this.fecha = fecha;
        this.idProveedor = idProveedor;
        this.idEmpresa = idEmpresa;
        this.idDisposicion = idDisposicion;
        this.aceptado = aceptado;
        this.latitud = latitud;
        this.longitud = longitud;
        this.precioEstimado = precioEstimado;
        this.observaciones = observaciones;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdDisposicion() {
        return idDisposicion;
    }

    public void setIdDisposicion(int idDisposicion) {
        this.idDisposicion = idDisposicion;
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
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

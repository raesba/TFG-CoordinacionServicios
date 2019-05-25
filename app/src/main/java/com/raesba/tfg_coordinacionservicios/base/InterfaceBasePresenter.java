package com.raesba.tfg_coordinacionservicios.base;

public interface InterfaceBasePresenter<VistaTipo> {
    void vistaActiva(VistaTipo vista);
    void vistaInactiva();
}

package com.raesba.tfg_coordinacionservicios.base;

public class BasePresenter<VistaTipo> implements InterfaceBasePresenter<VistaTipo> {

    protected VistaTipo vista;

    @Override
    public void vistaActiva(VistaTipo vista) {
        this.vista = vista;
    }

    @Override
    public void vistaInactiva() {
        vista = null;
    }
}

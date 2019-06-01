package com.raesba.tfg_coordinacionservicios.ui.proveedordetalle;

import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.base.InterfaceBasePresenter;
import com.raesba.tfg_coordinacionservicios.base.InterfaceBaseVista;

import java.util.ArrayList;

public interface ProveedorDetalleContract {
    interface Vista extends InterfaceBaseVista {

        void mostrarDatosProveedor(Proveedor proveedor, boolean currentUser);
        void mostrarProfesiones(ArrayList<String> profesiones);
    }

    interface Presenter extends InterfaceBasePresenter<ProveedorDetalleContract.Vista> {

        void getDatosProveedor(String uid);
        void getProfesiones();
    }
}

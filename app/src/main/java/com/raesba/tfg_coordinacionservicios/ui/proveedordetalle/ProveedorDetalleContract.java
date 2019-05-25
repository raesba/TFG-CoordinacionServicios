package com.raesba.tfg_coordinacionservicios.ui.proveedordetalle;

import com.raesba.tfg_coordinacionservicios.Proveedor;
import com.raesba.tfg_coordinacionservicios.base.InterfaceBasePresenter;
import com.raesba.tfg_coordinacionservicios.base.InterfaceBaseVista;

public interface ProveedorDetalleContract {
    interface Vista extends InterfaceBaseVista {

       void mostrarDatosProveedor(Proveedor proveedor, boolean currentUser);

    }

    interface Presenter extends InterfaceBasePresenter<ProveedorDetalleContract.Vista> {

        void getDatosProveedor(String uid);
    }
}

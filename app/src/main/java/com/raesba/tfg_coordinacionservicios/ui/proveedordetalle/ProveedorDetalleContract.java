package com.raesba.tfg_coordinacionservicios.ui.proveedordetalle;

import com.raesba.tfg_coordinacionservicios.base.InterfaceBasePresenter;
import com.raesba.tfg_coordinacionservicios.base.InterfaceBaseActivity;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;

import java.util.ArrayList;

public interface ProveedorDetalleContract {
    interface Activity extends InterfaceBaseActivity {

        void mostrarDatosProveedor(Proveedor proveedor, boolean currentUser);
        void mostrarProfesiones(ArrayList<String> profesiones);
    }

    interface Presenter extends InterfaceBasePresenter<Activity> {

        void getDatosProveedor(String uid);
        void getProfesiones();
    }
}

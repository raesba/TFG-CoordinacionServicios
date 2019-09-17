package com.raesba.tfg_coordinacionservicios.ui.proveedorlista;

import com.raesba.tfg_coordinacionservicios.base.InterfaceBaseActivity;
import com.raesba.tfg_coordinacionservicios.base.InterfaceBasePresenter;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;

import java.util.ArrayList;

public interface ProveedorListaContract {
    interface Activity extends InterfaceBaseActivity {
        void mostrarProveedores(ArrayList<Proveedor> proveedores);
        void mostrarProfesiones(ArrayList<String> profesiones);
        void comprobarListaVacia();
    }

    interface Presenter extends InterfaceBasePresenter<ProveedorListaContract.Activity> {
        void getProveedores(String profesion);
        void getProfesiones();
    }
}

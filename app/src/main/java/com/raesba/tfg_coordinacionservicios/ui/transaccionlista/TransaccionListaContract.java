package com.raesba.tfg_coordinacionservicios.ui.transaccionlista;

import com.raesba.tfg_coordinacionservicios.base.InterfaceBasePresenter;
import com.raesba.tfg_coordinacionservicios.base.InterfaceBaseVista;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.ui.transaccionnueva.TransaccionNuevaContract;

import java.util.ArrayList;

public interface TransaccionListaContract {
    interface Vista extends InterfaceBaseVista {
        void mostrarTransacciones(ArrayList<Transaccion> transacciones);
    }

    interface Presenter extends InterfaceBasePresenter<TransaccionListaContract.Vista> {
        void getTransacciones(int userType, String uid);
    }
}


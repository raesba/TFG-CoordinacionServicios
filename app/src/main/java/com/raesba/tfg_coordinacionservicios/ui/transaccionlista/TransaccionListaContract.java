package com.raesba.tfg_coordinacionservicios.ui.transaccionlista;

import com.raesba.tfg_coordinacionservicios.base.InterfaceBaseActivity;
import com.raesba.tfg_coordinacionservicios.base.InterfaceBasePresenter;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;

import java.util.ArrayList;

public interface TransaccionListaContract {
    interface Activity extends InterfaceBaseActivity {
        void mostrarTransacciones(ArrayList<Transaccion> transacciones);
        void mostrarTransaccion(Transaccion transaccion);
        void comprobarListaVacia();
    }

    interface Presenter extends InterfaceBasePresenter<Activity> {
        void getTransacciones(int userType, String uid);
    }
}


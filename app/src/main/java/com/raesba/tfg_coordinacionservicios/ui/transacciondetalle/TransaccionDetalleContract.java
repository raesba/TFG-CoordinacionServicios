package com.raesba.tfg_coordinacionservicios.ui.transacciondetalle;

import com.raesba.tfg_coordinacionservicios.base.InterfaceBaseActivity;
import com.raesba.tfg_coordinacionservicios.base.InterfaceBasePresenter;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;

public interface TransaccionDetalleContract {
    interface Activity extends InterfaceBaseActivity {
        void mostrarTransaccion(Transaccion transaccion);
        void onFinishTransaccion(int estadoTransaccion);
    }

    interface Presenter extends InterfaceBasePresenter<TransaccionDetalleContract.Activity> {
        void getTransaccion(String uid);
        void updateEstado(String uid, long fechaDisposicion, int estadoTransaccion);
    }
}
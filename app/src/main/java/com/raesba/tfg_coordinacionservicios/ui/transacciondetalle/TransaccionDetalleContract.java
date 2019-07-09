package com.raesba.tfg_coordinacionservicios.ui.transacciondetalle;

import com.raesba.tfg_coordinacionservicios.base.InterfaceBaseActivity;
import com.raesba.tfg_coordinacionservicios.base.InterfaceBasePresenter;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;

public interface TransaccionDetalleContract {
    interface Activity extends InterfaceBaseActivity {
        void mostrarTransaccion(Transaccion transaccion);
        void onFinishTransaccion();
    }

    interface Presenter extends InterfaceBasePresenter<TransaccionDetalleContract.Activity> {
        void getTransaccion(String uid);
        void updateEstado(String uid, int estado);
    }
}
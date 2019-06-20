package com.raesba.tfg_coordinacionservicios.ui.nuevatransaccion;

import com.raesba.tfg_coordinacionservicios.base.InterfaceBasePresenter;
import com.raesba.tfg_coordinacionservicios.base.InterfaceBaseVista;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;

public interface NuevaTransaccionContract {
    interface Vista extends InterfaceBaseVista {
        void mostrarDatosProveedor(Proveedor proveedor);
        void onFinishTransaccion();
    }

    interface Presenter extends InterfaceBasePresenter<NuevaTransaccionContract.Vista> {
        void getProveedor(String uid);
        void pushTransaccion(Transaccion transaccion);
    }
}

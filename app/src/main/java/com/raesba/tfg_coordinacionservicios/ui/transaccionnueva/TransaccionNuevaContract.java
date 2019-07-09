package com.raesba.tfg_coordinacionservicios.ui.transaccionnueva;

import com.raesba.tfg_coordinacionservicios.base.InterfaceBasePresenter;
import com.raesba.tfg_coordinacionservicios.base.InterfaceBaseActivity;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;

public interface TransaccionNuevaContract {
    interface Activity extends InterfaceBaseActivity {
        void mostrarDatosProveedor(Proveedor proveedor);
        void mostrarDatosEmpresa(Empresa empresa);
        void onFinishTransaccion();
    }

    interface Presenter extends InterfaceBasePresenter<Activity> {
        void getProveedor(String uid);
        void getEmpresa(String uid);
        void pushTransaccion(Transaccion transaccion);
    }
}

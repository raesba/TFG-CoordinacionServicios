package com.raesba.tfg_coordinacionservicios.ui.transaccionnueva;

import com.raesba.tfg_coordinacionservicios.base.BasePresenter;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProveedorCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.OnCompletadoCallback;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;

public class TransaccionNuevaPresenter extends BasePresenter<TransaccionNuevaContract.Vista>
        implements TransaccionNuevaContract.Presenter {

    private DatabaseManager databaseManager;

    public TransaccionNuevaPresenter(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void getProveedor(String uid) {
        databaseManager.getProveedor(uid, new GetProveedorCallback() {
            @Override
            public void onSuccess(Proveedor proveedor, boolean currentUser) {
                if (vista != null){
                    vista.mostrarDatosProveedor(proveedor);
                }
            }
            @Override
            public void onError(String error) {
                if (vista != null){
                    vista.mostrarToast(error);
                }
            }
        });
    }

    @Override
    public void pushTransaccion(Transaccion transaccion) {
        databaseManager.pushTransaccion(transaccion, new OnCompletadoCallback() {
            @Override
            public void onSuccess(String mensaje) {
                if (vista != null){
                    vista.onFinishTransaccion();
                }
            }

            @Override
            public void onError(String mensaje) {
                if (vista != null){
                    vista.mostrarToast(mensaje);
                }
            }
        });
    }
}

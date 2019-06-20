package com.raesba.tfg_coordinacionservicios.ui.nuevatransaccion;

import com.raesba.tfg_coordinacionservicios.base.BasePresenter;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProveedorCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.OnCompletadoCallback;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;

public class NuevaTransaccionPresenter extends BasePresenter<NuevaTransaccionContract.Vista>
        implements NuevaTransaccionContract.Presenter {

    private DatabaseManager databaseManager;

    public NuevaTransaccionPresenter(DatabaseManager databaseManager) {
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
            public void onFailure(String error) {
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
            public void onFinalizadoCorrectamente(String mensaje) {
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

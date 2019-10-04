package com.raesba.tfg_coordinacionservicios.ui.transaccionlista;

import com.raesba.tfg_coordinacionservicios.base.BasePresenter;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProveedorCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetTransaccionesCallback;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

import java.util.ArrayList;

public class TransaccionListaPresenter extends BasePresenter<TransaccionListaContract.Activity>
        implements TransaccionListaContract.Presenter {

    private DatabaseManager databaseManager;

    public TransaccionListaPresenter(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void getTransacciones(int userType, String uid) {
        if (userType == Constantes.USUARIO_TIPO_PROVEEDOR){
            databaseManager.getProveedor(uid, new GetProveedorCallback() {
                @Override
                public void onSuccess(Proveedor proveedor, boolean currentUser) {

                }

                @Override
                public void onError(String error) {

                }
            });
        }
        
        databaseManager.getTransacciones(userType, uid, new GetTransaccionesCallback() {
            @Override
            public void onSuccess(ArrayList<Transaccion> transacciones) {
                if (vista != null){
                    vista.setProgessBar(false);
                    vista.mostrarTransacciones(transacciones);
                }
            }

            @Override
            public void onError(String error) {
                if (vista != null){
                    vista.setProgessBar(false);
                    vista.mostrarToast(error);
                }
            }
        });
    }
}

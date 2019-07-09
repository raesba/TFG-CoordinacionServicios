package com.raesba.tfg_coordinacionservicios.ui.transacciondetalle;

import com.raesba.tfg_coordinacionservicios.base.BasePresenter;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProveedorCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetTransaccionCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetTransaccionesCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.OnCompletadoCallback;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.ui.transacciondetalle.TransaccionDetalleContract;

public class TransaccionDetallePresenter extends BasePresenter<TransaccionDetalleContract.Activity>
        implements TransaccionDetalleContract.Presenter {

    private DatabaseManager databaseManager;

    public TransaccionDetallePresenter(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void getTransaccion(String uid) {
        databaseManager.getTransaccion(uid, new GetTransaccionCallback() {
            @Override
            public void onSuccess(Transaccion transaccion) {
                if (vista != null){
                    vista.mostrarTransaccion(transaccion);
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
    public void updateEstado(String uid, int estado) {

    }
}
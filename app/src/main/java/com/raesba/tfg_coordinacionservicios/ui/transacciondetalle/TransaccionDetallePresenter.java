package com.raesba.tfg_coordinacionservicios.ui.transacciondetalle;

import com.raesba.tfg_coordinacionservicios.base.BasePresenter;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetTransaccionCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.OnDefaultCallback;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

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
                    vista.setProgessBar(false);
                    vista.mostrarTransaccion(transaccion);
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

    @Override
    public void updateEstado(String uid, int estadoTransaccion) {
        if (vista != null){
            vista.setProgessBar(true);
        }

        databaseManager.updateTransaccion(uid, estadoTransaccion, new OnDefaultCallback<Integer>(){
            @Override
            public void onSuccess(Integer result) {
                if (vista != null){
                    vista.setProgessBar(false);
                    vista.mostrarToast(Constantes.MSG_TRANSACCION_ACTUALIZADA);
                    vista.onFinishTransaccion(result);
                }
            }

            @Override
            public void onError(String error) {
                if (vista != null){
                    vista.mostrarToast(error);
                    vista.setProgessBar(false);
                }
            }
        });
    }
}
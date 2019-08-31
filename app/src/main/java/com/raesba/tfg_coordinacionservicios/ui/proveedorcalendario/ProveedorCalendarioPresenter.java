package com.raesba.tfg_coordinacionservicios.ui.proveedorcalendario;

import com.raesba.tfg_coordinacionservicios.base.BasePresenter;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetDisposicionesCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetTransaccionCallback;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;

import java.util.HashMap;

public class ProveedorCalendarioPresenter extends BasePresenter<ProveedorCalendarioContract.Activity>
        implements ProveedorCalendarioContract.Presenter {

    private DatabaseManager databaseManager;

    public ProveedorCalendarioPresenter(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void getDisposiciones(String uid) {
        databaseManager.getDisposiciones(uid, new GetDisposicionesCallback() {
            @Override
            public void onSuccess(HashMap<String, Boolean> disposiciones) {
                if (vista != null){
                    vista.setProgessBar(false);
                    vista.mostrarDisposiciones(disposiciones, false);
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
    public void pushDisposiciones(String uid, HashMap<String, Boolean> disposiciones) {
        databaseManager.pushDisposiciones(uid, disposiciones, new GetDisposicionesCallback(){

            @Override
            public void onSuccess(HashMap<String, Boolean> disposiciones) {
                if (vista != null){
                    vista.setProgessBar(false);
                    vista.mostrarDisposiciones(disposiciones, true);
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


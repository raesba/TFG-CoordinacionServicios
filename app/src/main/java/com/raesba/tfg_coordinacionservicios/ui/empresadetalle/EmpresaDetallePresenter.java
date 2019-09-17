package com.raesba.tfg_coordinacionservicios.ui.empresadetalle;

import com.raesba.tfg_coordinacionservicios.base.BasePresenter;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetEmpresaCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.OnDefaultCallback;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

public class EmpresaDetallePresenter extends BasePresenter<EmpresaDetalleContract.Activity>
        implements EmpresaDetalleContract.Presenter {

    private DatabaseManager databaseManager;

    public EmpresaDetallePresenter(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void getDatosEmpresa(String uid) {
        databaseManager.getEmpresa(uid, new GetEmpresaCallback() {
            @Override
            public void onSuccess(Empresa empresa) {
                if (vista != null){
                    vista.setProgessBar(false);
                    vista.mostrarDatosEmpresa(empresa);
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
    public void updateEmpresa(Empresa empresa) {
        databaseManager.updateEmpresa(empresa);
    }

    public void darDeBaja(String uid) {
        databaseManager.darDeBaja(uid, Constantes.USUARIO_TIPO_EMPRESA, new OnDefaultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                if (vista != null){
                    vista.resultadoBaja();
                }
            }

            @Override
            public void onError(String mensaje) {

            }
        });
    }

}


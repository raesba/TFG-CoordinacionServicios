package com.raesba.tfg_coordinacionservicios.ui.empresadetalle;

import com.raesba.tfg_coordinacionservicios.base.BasePresenter;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetEmpresaCallback;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;

public class EmpresaDetallePresenter extends BasePresenter<EmpresaDetalleContract.Vista>
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
                    vista.mostrarDatosEmpresa(empresa);
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
}

